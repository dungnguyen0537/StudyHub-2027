package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.ScheduleEntity;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduleEntity schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ScheduleEntity> schedules);

    @Update
    void update(ScheduleEntity schedule);

    @Query("DELETE FROM schedules WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM schedules WHERE subjectId = :subjectId")
    void deleteBySubjectId(String subjectId);

    @Query("SELECT * FROM schedules WHERE userId = :userId AND syncStatus != 3")
    LiveData<List<ScheduleEntity>> getAllByUser(String userId);

    @Query("SELECT * FROM schedules WHERE dayOfWeek = :dayOfWeek AND syncStatus != 3 ORDER BY startTime ASC")
    LiveData<List<ScheduleEntity>> getByDayOfWeek(int dayOfWeek);

    @Query("SELECT * FROM schedules WHERE subjectId = :subjectId AND syncStatus != 3 ORDER BY dayOfWeek ASC, startTime ASC")
    LiveData<List<ScheduleEntity>> getBySubject(String subjectId);

    @Query("SELECT * FROM schedules WHERE syncStatus != 0")
    List<ScheduleEntity> getPendingSync();

    @Query("UPDATE schedules SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(String id, int status);

    @Query("SELECT * FROM schedules WHERE id = :id AND syncStatus != 3")
    LiveData<ScheduleEntity> getById(String id);

    @Query("SELECT * FROM schedules WHERE id = :id")
    ScheduleEntity getByIdSync(String id);
}
