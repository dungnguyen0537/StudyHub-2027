package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects",
        indices = {@Index("userId"), @Index("syncStatus")})
public class SubjectEntity {
    @PrimaryKey @NonNull private String id;
    private String userId;
    private String name;
    private String code;
    private String teacher;
    private int credits;
    private String room;
    private String colorHex;
    private String note;
    private boolean isFavorite;
    private long createdAt;
    private long updatedAt;
    private int syncStatus;

    public SubjectEntity() {}

    // Getters
    @NonNull public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getTeacher() { return teacher; }
    public int getCredits() { return credits; }
    public String getRoom() { return room; }
    public String getColorHex() { return colorHex; }
    public String getNote() { return note; }
    public boolean isFavorite() { return isFavorite; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public int getSyncStatus() { return syncStatus; }

    // Setters
    public void setId(@NonNull String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setRoom(String room) { this.room = room; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public void setNote(String note) { this.note = note; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
    public void setSyncStatus(int syncStatus) { this.syncStatus = syncStatus; }
}
