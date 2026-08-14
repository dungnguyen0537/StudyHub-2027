package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks",
        indices = {@Index("userId"), @Index("category"), @Index("isCompleted")})
public class TaskEntity {
    @PrimaryKey @NonNull private String id;
    private String userId;
    private String title;
    private int priority;
    private String category;
    private boolean isCompleted;
    private long deadline;
    private boolean notificationEnabled;
    private long createdAt;
    private long updatedAt;
    private int syncStatus;

    public TaskEntity() {}

    @NonNull public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public int getPriority() { return priority; }
    public String getCategory() { return category; }
    public boolean isCompleted() { return isCompleted; }
    public long getDeadline() { return deadline; }
    public boolean isNotificationEnabled() { return notificationEnabled; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public int getSyncStatus() { return syncStatus; }

    public void setId(@NonNull String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setCategory(String category) { this.category = category; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public void setDeadline(long deadline) { this.deadline = deadline; }
    public void setNotificationEnabled(boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
