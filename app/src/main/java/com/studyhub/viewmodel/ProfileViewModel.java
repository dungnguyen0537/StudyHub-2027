package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.UserDao;
import com.studyhub.database.entity.UserEntity;
import com.studyhub.repository.AuthRepository;
import com.studyhub.utils.AppExecutors;

public class ProfileViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final UserDao userDao;
    private final FirebaseUser currentUser;
    private final MutableLiveData<UserEntity> userProfile = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userDao = StudyHubDatabase.getInstance(application).userDao();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        
        loadUserProfile();
    }

    private void loadUserProfile() {
        if (currentUser != null) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                UserEntity entity = userDao.getUserByIdSync(currentUser.getUid());
                if (entity != null) {
                    userProfile.postValue(entity);
                } else {
                    // Fallback to basic info from FirebaseUser
                    UserEntity basic = new UserEntity();
                    basic.setUid(currentUser.getUid());
                    basic.setFullName(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Sinh viên");
                    basic.setEmail(currentUser.getEmail());
                    basic.setAvatarUrl(currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "");
                    userProfile.postValue(basic);
                }
            });
        }
    }

    public LiveData<UserEntity> getUserProfile() {
        return userProfile;
    }

    public void logout() {
        authRepository.logout();
    }

    public void updateUserProfile(UserEntity updatedEntity) {
        if (currentUser != null) {
            // Update Firestore
            com.studyhub.firebase.FirestoreHelper firestoreHelper = new com.studyhub.firebase.FirestoreHelper();
            firestoreHelper.getUser(currentUser.getUid()).addOnSuccessListener(documentSnapshot -> {
                com.studyhub.model.User user = documentSnapshot.toObject(com.studyhub.model.User.class);
                if (user == null) {
                    user = new com.studyhub.model.User(currentUser.getUid(), updatedEntity.getFullName(), updatedEntity.getEmail());
                }
                
                user.setFullName(updatedEntity.getFullName());
                user.setStudentId(updatedEntity.getStudentId());
                user.setPhone(updatedEntity.getPhone());
                user.setAddress(updatedEntity.getAddress());
                user.setAvatarUrl(updatedEntity.getAvatarUrl());
                
                firestoreHelper.saveUser(user).addOnSuccessListener(aVoid -> {
                    // Update local DB
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        userDao.insertOrUpdate(updatedEntity);
                        userProfile.postValue(updatedEntity);
                    });
                });
            });
            
            // Also update Firebase Auth profile if name or avatar changed
            com.google.firebase.auth.UserProfileChangeRequest profileUpdates = new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(updatedEntity.getFullName())
                .setPhotoUri(updatedEntity.getAvatarUrl() != null ? android.net.Uri.parse(updatedEntity.getAvatarUrl()) : null)
                .build();
            currentUser.updateProfile(profileUpdates);
        }
    }
}
