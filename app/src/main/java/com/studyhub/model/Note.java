package com.studyhub.model;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private String id;
    private String userId;
    private String subjectId;
    private String title;
    private String content;
    private String type; // TEXT, CHECKLIST, IMAGE
    private List<String> imageUrls;
    private boolean isFavorite;
    private long createdAt;
    private long updatedAt;

    public Note() {
        this.imageUrls = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getSubjectId() { return subjectId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getType() { return type; }
    public List<String> getImageUrls() { return imageUrls; }
    public boolean isFavorite() { return isFavorite; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setType(String type) { this.type = type; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
