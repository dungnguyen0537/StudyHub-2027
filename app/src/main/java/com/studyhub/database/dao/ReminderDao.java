package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.ReminderEntity;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReminderEntity reminder);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReminderEntity> reminders);

    @Update
    void update(ReminderEntity reminder);

    @Query("DELETE FROM reminders WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM reminders WHERE eventId = :eventId")
    void deleteByEventId(String eventId);

    @Query("SELECT * FROM reminders WHERE userId = :userId AND isActive = 1")
    List<ReminderEntity> getActiveReminders(String userId);

    @Query("SELECT * FROM reminders WHERE eventId = :eventId LIMIT 1")
    ReminderEntity getByEventId(String eventId);

    @Query("UPDATE reminders SET isActive = :isActive WHERE id = :id")
    void updateActiveStatus(String id, boolean isActive);

    @Query("SELECT * FROM reminders WHERE id = :id")
    ReminderEntity getById(String id);

    @Query("SELECT * FROM reminders WHERE userId = :userId")
    LiveData<List<ReminderEntity>> getAllByUser(String userId);
}
