package com.studyhub.model;

public class Deadline {
    private String id;
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

    public Deadline() {}

    // Getters
    public String getId() { return id; }
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

    // Setters
    public void setId(String id) { this.id = id; }
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
}
