package com.studyhub.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.UserDao;
import com.studyhub.database.entity.UserEntity;
import com.studyhub.firebase.FirebaseAuthHelper;
import com.studyhub.firebase.FirestoreHelper;
import com.studyhub.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthRepository {
    private static final String TAG = "AuthRepository";
    private final FirebaseAuthHelper authHelper;
    private final FirestoreHelper firestoreHelper;
    private final UserDao userDao;
    private final ExecutorService executorService;

    // States
    public enum AuthState { IDLE, LOADING, SUCCESS, ERROR }
    
    private final MutableLiveData<AuthState> authState = new MutableLiveData<>(AuthState.IDLE);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AuthRepository(Application application) {
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<AuthState> getAuthState() { return authState; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public FirebaseUser getCurrentUser() { return authHelper.getCurrentUser(); }
    public boolean isEmailVerified() { return authHelper.isEmailVerified(); }

    public void login(String email, String password) {
        authState.setValue(AuthState.LOADING);
        authHelper.loginWithEmail(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null && user.isEmailVerified()) {
                        syncUserFromFirestore(user.getUid());
                    } else {
                        authState.setValue(AuthState.ERROR);
                        errorMessage.setValue("Vui lòng xác thực email trước khi đăng nhập");
                        authHelper.signOut();
                    }
                })
                .addOnFailureListener(this::handleAuthError);
    }

    public void register(String fullName, String email, String password) {
        authState.setValue(AuthState.LOADING);
        authHelper.registerWithEmail(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        User user = new User(firebaseUser.getUid(), fullName, email);
                        firestoreHelper.saveUser(user).addOnSuccessListener(aVoid -> {
                            com.google.android.gms.tasks.Task<Void> verificationTask = authHelper.sendEmailVerification();
                            if (verificationTask != null) {
                                verificationTask.addOnCompleteListener(task -> {
                                    authState.setValue(AuthState.SUCCESS); // Register successful
                                });
                            } else {
                                authState.setValue(AuthState.SUCCESS);
                            }
                        }).addOnFailureListener(this::handleAuthError);
                    }
                })
                .addOnFailureListener(this::handleAuthError);
    }
    
    public void loginWithGoogle(AuthCredential credential) {
        authState.setValue(AuthState.LOADING);
        authHelper.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    boolean isNewUser = authResult.getAdditionalUserInfo() != null && 
                                        authResult.getAdditionalUserInfo().isNewUser();
                    FirebaseUser firebaseUser = authResult.getUser();
                    
                    if (firebaseUser != null) {
                        if (isNewUser) {
                            String displayName = firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "";
                            String email = firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "";
                            User user = new User(firebaseUser.getUid(), displayName, email);
                            user.setAvatarUrl(firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : "");
                            firestoreHelper.saveUser(user).addOnSuccessListener(aVoid -> {
                                syncUserFromFirestore(user.getUid());
                            }).addOnFailureListener(this::handleAuthError);
                        } else {
                            syncUserFromFirestore(firebaseUser.getUid());
                        }
                    }
                })
                .addOnFailureListener(this::handleAuthError);
    }

    public void resetPassword(String email) {
        authState.setValue(AuthState.LOADING);
        authHelper.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> authState.setValue(AuthState.SUCCESS))
                .addOnFailureListener(this::handleAuthError);
    }

    public void logout() {
        authHelper.signOut();
        executorService.execute(() -> {
            userDao.clearAll();
            // TODO: Clear other DAOs on logout
        });
    }

    private void syncUserFromFirestore(String uid) {
        firestoreHelper.getUser(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            // Convert to Entity and cache
                            UserEntity entity = new UserEntity();
                            entity.setUid(user.getUid());
                            entity.setFullName(user.getFullName());
                            entity.setEmail(user.getEmail());
                            entity.setStudentId(user.getStudentId());
                            entity.setClassName(user.getClassName());
                            entity.setDepartment(user.getDepartment());
                            entity.setMajor(user.getMajor());
                            entity.setPhone(user.getPhone());
                            entity.setAvatarUrl(user.getAvatarUrl());
                            entity.setBirthDate(user.getBirthDate());
                            entity.setCreatedAt(user.getCreatedAt());
                            entity.setUpdatedAt(user.getUpdatedAt());
                            
                            executorService.execute(() -> {
                                userDao.insertOrUpdate(entity);
                                authState.postValue(AuthState.SUCCESS);
                            });
                        }
                    } else {
                        authState.setValue(AuthState.ERROR);
                        errorMessage.setValue("Không tìm thấy thông tin người dùng");
                    }
                })
                .addOnFailureListener(this::handleAuthError);
    }

    private void handleAuthError(Exception e) {
        Log.e(TAG, "Auth error: ", e);
        authState.setValue(AuthState.ERROR);
        
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage.setValue("Email hoặc mật khẩu không chính xác");
        } else if (e instanceof FirebaseAuthInvalidUserException) {
            errorMessage.setValue("Tài khoản không tồn tại hoặc đã bị vô hiệu hóa");
        } else if (e instanceof FirebaseAuthUserCollisionException) {
            errorMessage.setValue("Email này đã được sử dụng");
        } else {
            errorMessage.setValue("Đã xảy ra lỗi. Vui lòng thử lại");
        }
    }
    
    public void resendVerificationEmail() {
        authHelper.sendEmailVerification()
                .addOnSuccessListener(aVoid -> {
                    errorMessage.setValue("Đã gửi lại email xác thực. Vui lòng kiểm tra hộp thư của bạn.");
                })
                .addOnFailureListener(e -> {
                    errorMessage.setValue("Không thể gửi email xác thực: " + e.getMessage());
                });
    }

    public void resetState() {
        authState.setValue(AuthState.IDLE);
    }
}
