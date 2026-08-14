package com.studyhub.model;

public class Schedule {
    private String id;
    private String userId;
    private String subjectId;
    private int dayOfWeek; // 2=Monday ... 7=Saturday, 1=Sunday
    private String startTime; // "HH:mm"
    private String endTime;
    private String room;
    private boolean reminderEnabled;
    private int reminderMinutesBefore;
    private long createdAt;
    private long updatedAt;

    public Schedule() {}

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getSubjectId() { return subjectId; }
    public int getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getRoom() { return room; }
    public boolean isReminderEnabled() { return reminderEnabled; }
    public int getReminderMinutesBefore() { return reminderMinutesBefore; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setRoom(String room) { this.room = room; }
    public void setReminderEnabled(boolean reminderEnabled) { this.reminderEnabled = reminderEnabled; }
    public void setReminderMinutesBefore(int reminderMinutesBefore) { this.reminderMinutesBefore = reminderMinutesBefore; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
