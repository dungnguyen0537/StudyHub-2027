package com.studyhub.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHelper {
    
    private final FirebaseAuth firebaseAuth;
    
    public FirebaseAuthHelper() {
        firebaseAuth = FirebaseAuth.getInstance();
    }
    
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
    
    public Task<AuthResult> loginWithEmail(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }
    
    public Task<AuthResult> registerWithEmail(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }
    
    public Task<AuthResult> signInWithCredential(AuthCredential credential) {
        return firebaseAuth.signInWithCredential(credential);
    }
    
    public Task<Void> sendPasswordResetEmail(String email) {
        return firebaseAuth.sendPasswordResetEmail(email);
    }
    
    public Task<Void> sendEmailVerification() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            return user.sendEmailVerification();
        }
        return null;
    }
    
    public void signOut() {
        firebaseAuth.signOut();
    }
    
    public boolean isEmailVerified() {
        FirebaseUser user = getCurrentUser();
        return user != null && user.isEmailVerified();
    }
}
