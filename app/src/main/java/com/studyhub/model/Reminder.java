package com.studyhub.model;

public class Reminder {
    private String id;
    private String userId;
    private String eventId;
    private String eventType; // SCHEDULE, DEADLINE, TASK, CUSTOM
    private String title;
    private long triggerTime;
    private boolean isRepeating;
    private String repeatInterval; // DAILY, WEEKLY, NONE
    private boolean isActive;

    public Reminder() {}

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getEventId() { return eventId; }
    public String getEventType() { return eventType; }
    public String getTitle() { return title; }
    public long getTriggerTime() { return triggerTime; }
    public boolean isRepeating() { return isRepeating; }
    public String getRepeatInterval() { return repeatInterval; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public void setTitle(String title) { this.title = title; }
    public void setTriggerTime(long triggerTime) { this.triggerTime = triggerTime; }
    public void setRepeating(boolean repeating) { isRepeating = repeating; }
    public void setRepeatInterval(String repeatInterval) { this.repeatInterval = repeatInterval; }
    public void setActive(boolean active) { isActive = active; }
}
