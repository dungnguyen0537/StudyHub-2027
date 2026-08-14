package com.studyhub.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @NonNull
    private String uid;
    private String fullName;
    private String email;
    private String studentId;
    private String className;
    private String department;
    private String major;
    private String phone;
    private String avatarUrl;
    private String address;
    private long birthDate;
    private long createdAt;
    private long updatedAt;

    public UserEntity() {}

    // Getters
    @NonNull public String getUid() { return uid; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getStudentId() { return studentId; }
    public String getClassName() { return className; }
    public String getDepartment() { return department; }
    public String getMajor() { return major; }
    public String getPhone() { return phone; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getAddress() { return address; }
    public long getBirthDate() { return birthDate; }
    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }

    // Setters
    public void setUid(@NonNull String uid) { this.uid = uid; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setClassName(String className) { this.className = className; }
    public void setDepartment(String department) { this.department = department; }
    public void setMajor(String major) { this.major = major; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setAddress(String address) { this.address = address; }
    public void setBirthDate(long birthDate) { this.birthDate = birthDate; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
