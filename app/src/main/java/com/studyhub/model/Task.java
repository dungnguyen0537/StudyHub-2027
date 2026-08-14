package com.studyhub.model;

public class Task {
    private String id;
    private String userId;
    private String title;
    private int priority;
    private String category;
    private boolean isCompleted;
    private long deadline;
    private boolean notificationEnabled;
    private long createdAt;
    private long updatedAt;

    public Task() {}

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public int getPriority() { return priority; }
    public String getCategory() { return category; }
    public boolean isCompleted() { return isCompleted; }
    public long getDeadline() { return deadline; }
    public boolean isNotificationEnabled() { return notificationEnabled; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setCategory(String category) { this.category = category; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public void setDeadline(long deadline) { this.deadline = deadline; }
    public void setNotificationEnabled(boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
