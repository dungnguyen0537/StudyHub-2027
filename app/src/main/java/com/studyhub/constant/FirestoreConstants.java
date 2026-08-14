package com.studyhub.constant;

public class FirestoreConstants {
    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_SUBJECTS = "subjects";
    public static final String COLLECTION_SCHEDULES = "schedules";
    public static final String COLLECTION_DEADLINES = "deadlines";
    public static final String COLLECTION_TASKS = "tasks";
    public static final String COLLECTION_NOTES = "notes";
    public static final String COLLECTION_REMINDERS = "reminders";

    // User document fields
    public static final String FIELD_FULL_NAME = "fullName";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_STUDENT_ID = "studentId";
    public static final String FIELD_CLASS_NAME = "className";
    public static final String FIELD_DEPARTMENT = "department";
    public static final String FIELD_MAJOR = "major";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_AVATAR_URL = "avatarUrl";
    public static final String FIELD_BIRTH_DATE = "birthDate";
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_UPDATED_AT = "updatedAt";

    // Common fields
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_SUBJECT_ID = "subjectId";
    public static final String FIELD_IS_FAVORITE = "isFavorite";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DUE_DATE = "dueDate";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_IS_COMPLETED = "isCompleted";
    public static final String FIELD_CATEGORY = "category";

    // Storage paths
    public static final String STORAGE_AVATARS = "users/%s/avatar/profile.jpg";
    public static final String STORAGE_ATTACHMENTS = "users/%s/attachments/%s/%s";
    public static final String STORAGE_NOTE_IMAGES = "users/%s/notes/%s/image_%d.jpg";

    private FirestoreConstants() {}
}
