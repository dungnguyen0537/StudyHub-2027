package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.studyhub.repository.AuthRepository;
import com.studyhub.utils.ValidationUtils;

public class AuthViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;

    // Validation error states
    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();
    private final MutableLiveData<String> nameError = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    // Expose Repository states
    public LiveData<AuthRepository.AuthState> getAuthState() { return authRepository.getAuthState(); }
    public LiveData<String> getErrorMessage() { return authRepository.getErrorMessage(); }
    public FirebaseUser getCurrentUser() { return authRepository.getCurrentUser(); }
    public boolean isEmailVerified() { return authRepository.isEmailVerified(); }

    // Expose Validation states
    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPasswordError() { return passwordError; }
    public LiveData<String> getNameError() { return nameError; }

    public void login(String email, String password) {
        clearErrors();
        boolean isValid = true;

        if (!ValidationUtils.isValidEmail(email)) {
            emailError.setValue("Email không hợp lệ");
            isValid = false;
        }

        if (!ValidationUtils.isNotEmpty(password)) {
            passwordError.setValue("Vui lòng nhập mật khẩu");
            isValid = false;
        }

        if (isValid) {
            authRepository.login(email, password);
        }
    }

    public void register(String fullName, String email, String password, String confirmPassword) {
        clearErrors();
        boolean isValid = true;

        if (!ValidationUtils.isNotEmpty(fullName)) {
            nameError.setValue("Vui lòng nhập họ tên");
            isValid = false;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            emailError.setValue("Email không hợp lệ");
            isValid = false;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            passwordError.setValue("Mật khẩu phải có ít nhất 6 ký tự");
            isValid = false;
        } else if (!ValidationUtils.isPasswordsMatch(password, confirmPassword)) {
            passwordError.setValue("Mật khẩu xác nhận không khớp");
            isValid = false;
        }

        if (isValid) {
            authRepository.register(fullName, email, password);
        }
    }
    
    public void loginWithGoogle(AuthCredential credential) {
        authRepository.loginWithGoogle(credential);
    }

    public void resetPassword(String email) {
        clearErrors();
        if (!ValidationUtils.isValidEmail(email)) {
            emailError.setValue("Email không hợp lệ");
            return;
        }
        authRepository.resetPassword(email);
    }

    public void logout() {
        authRepository.logout();
    }
    
    public void resendVerificationEmail() {
        authRepository.resendVerificationEmail();
    }

    public void resetState() {
        authRepository.resetState();
    }

    private void clearErrors() {
        emailError.setValue(null);
        passwordError.setValue(null);
        nameError.setValue(null);
    }
}
