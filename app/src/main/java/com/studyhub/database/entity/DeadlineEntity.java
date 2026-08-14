package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "deadlines",
        foreignKeys = @ForeignKey(
            entity = SubjectEntity.class,
            parentColumns = "id",
            childColumns = "subjectId",
            onDelete = ForeignKey.CASCADE),
        indices = {@Index("userId"), @Index("subjectId"), @Index("dueDate"),
                   @Index("status"), @Index("syncStatus")})
public class DeadlineEntity {
    @PrimaryKey @NonNull private String id;
    private String userId;
    private String subjectId;
    private String title;
    private String description;
    private int priority;
    private long dueDate;
    private String attachmentUrl;
    private String attachmentName;
    private String status;
    private long createdAt;
    private long updatedAt;
    private int syncStatus;

    public DeadlineEntity() {}

    @NonNull public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getSubjectId() { return subjectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public long getDueDate() { return dueDate; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public String getAttachmentName() { return attachmentName; }
    public String getStatus() { return status; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public int getSyncStatus() { return syncStatus; }

    public void setId(@NonNull String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setDueDate(long dueDate) { this.dueDate = dueDate; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
    public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
