package com.studyhub.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.studyhub.constant.AppConstants;

public class PreferenceManager {

    private final SharedPreferences preferences;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        preferences.edit().putBoolean("IS_FIRST_TIME_LAUNCH", isFirstTime).apply();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
