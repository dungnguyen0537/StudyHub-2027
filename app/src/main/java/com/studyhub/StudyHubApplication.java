package com.studyhub;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.studyhub.utils.PreferenceManager;
import com.studyhub.constant.AppConstants;

public class StudyHubApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize PreferenceManager to check Theme
        PreferenceManager preferenceManager = new PreferenceManager(this);
        boolean isDarkMode = preferenceManager.getBoolean(AppConstants.KEY_DARK_MODE, false);
        
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        // Initialize Notification Channels
        com.studyhub.utils.NotificationHelper.createNotificationChannels(this);
    }
}
