package com.studyhub.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable navigateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler(Looper.getMainLooper());
        navigateRunnable = this::checkAuthState;

        // Wait 2 seconds, then check auth state
        handler.postDelayed(navigateRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(navigateRunnable);
        }
    }

    private void checkAuthState() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);

        if (currentUser != null && currentUser.isEmailVerified()) {
            // User logged in and verified -> go to dashboard
            intent.putExtra(AppConstants.NAV_EXTRA_NAVIGATE_TO, AppConstants.NAV_DASHBOARD);
        } else {
            // Not logged in or not verified -> go to login
            intent.putExtra(AppConstants.NAV_EXTRA_NAVIGATE_TO, AppConstants.NAV_LOGIN);
        }

        startActivity(intent);
        finish(); // Remove SplashActivity from back stack
    }
}
