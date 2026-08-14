package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.List;

@Entity(tableName = "notes",
        indices = {@Index("userId"), @Index("subjectId"), @Index("isFavorite")})
public class NoteEntity {
    @PrimaryKey @NonNull private String id;
    private String userId;
    private String subjectId;
    private String title;
    private String content;
    private String type;
    private String imageUrlsJson; // JSON serialized List<String>
    private boolean isFavorite;
    private long createdAt;
    private long updatedAt;
    private int syncStatus;

    public NoteEntity() {}

    @NonNull public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getSubjectId() { return subjectId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getType() { return type; }
    public String getImageUrlsJson() { return imageUrlsJson; }
    
    public List<String> getImageUrls() {
        if (imageUrlsJson == null || imageUrlsJson.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return new com.google.gson.Gson().fromJson(imageUrlsJson, 
            new com.google.gson.reflect.TypeToken<List<String>>(){}.getType());
    }
    
    public boolean isFavorite() { return isFavorite; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public int getSyncStatus() { return syncStatus; }

    public void setId(@NonNull String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setType(String type) { this.type = type; }
    public void setImageUrlsJson(String imageUrlsJson) { this.imageUrlsJson = imageUrlsJson; }

    public void setImageUrls(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            this.imageUrlsJson = null;
        } else {
            this.imageUrlsJson = new com.google.gson.Gson().toJson(imageUrls);
        }
    }

    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
