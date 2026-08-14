package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedules",
        foreignKeys = @ForeignKey(
            entity = SubjectEntity.class,
            parentColumns = "id",
            childColumns = "subjectId",
            onDelete = ForeignKey.CASCADE),
        indices = {@Index("userId"), @Index("subjectId"), @Index("dayOfWeek")})
public class ScheduleEntity {
    @PrimaryKey @NonNull private String id;
    private String userId;
    private String subjectId;
    private int dayOfWeek;
    private String startTime;
    private String endTime;
    private String room;
    private boolean reminderEnabled;
    private int reminderMinutesBefore;
    private long createdAt;
    private long updatedAt;
    private int syncStatus;

    public ScheduleEntity() {}

    @NonNull public String getId() { return id; }
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
    public int getSyncStatus() { return syncStatus; }

    public void setId(@NonNull String id) { this.id = id; }
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
    public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
