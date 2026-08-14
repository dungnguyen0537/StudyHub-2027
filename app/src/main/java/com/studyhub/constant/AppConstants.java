package com.studyhub.constant;

public class AppConstants {
    // SharedPreferences
    public static final String PREF_NAME = "studyhub_prefs";
    public static final String KEY_DARK_MODE = "dark_mode";
    public static final String KEY_LAST_SYNC = "last_sync_timestamp";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_AVATAR = "user_avatar";

    // Reminder defaults
    public static final int REMINDER_DEFAULT_MINUTES = 30;
    public static final int[] REMINDER_OPTIONS = {15, 30, 60, 120, 1440}; // minutes

    // File limits
    public static final int MAX_AVATAR_SIZE_MB = 5;
    public static final int MAX_ATTACHMENT_SIZE_MB = 25;

    // Date/Time format
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

    // Navigation
    public static final String NAV_EXTRA_NAVIGATE_TO = "navigate_to";
    public static final String NAV_EXTRA_REMINDER_ID = "reminder_id";
    public static final String NAV_LOGIN = "login";
    public static final String NAV_DASHBOARD = "dashboard";

    // Bundle keys
    public static final String KEY_SUBJECT_ID = "subject_id";
    public static final String KEY_DEADLINE_ID = "deadline_id";
    public static final String KEY_TASK_ID = "task_id";
    public static final String KEY_NOTE_ID = "note_id";
    public static final String KEY_SCHEDULE_ID = "schedule_id";
    public static final String KEY_IS_EDIT_MODE = "is_edit_mode";

    private AppConstants() {} // prevent instantiation
}
