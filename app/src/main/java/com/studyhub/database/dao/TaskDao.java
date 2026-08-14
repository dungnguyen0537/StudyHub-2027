package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntity> tasks);

    @Update
    void update(TaskEntity task);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM tasks WHERE userId = :userId AND syncStatus != 3 ORDER BY isCompleted ASC, priority DESC, deadline ASC")
    LiveData<List<TaskEntity>> getAllByUser(String userId);

    @Query("SELECT * FROM tasks WHERE userId = :userId AND category = :category AND syncStatus != 3 ORDER BY isCompleted ASC, priority DESC, deadline ASC")
    LiveData<List<TaskEntity>> getByCategory(String userId, String category);

    @Query("UPDATE tasks SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :id")
    void updateStatus(String id, boolean isCompleted, long updatedAt);

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = 0 AND syncStatus != 3")
    LiveData<List<TaskEntity>> getActiveTasks(String userId);

    @Query("SELECT * FROM tasks WHERE syncStatus != 0")
    List<TaskEntity> getPendingSync();

    @Query("UPDATE tasks SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(String id, int status);

    @Query("SELECT * FROM tasks WHERE userId = :userId AND title LIKE '%' || :query || '%' AND syncStatus != 3")
    LiveData<List<TaskEntity>> search(String userId, String query);

    @Query("SELECT * FROM tasks WHERE id = :id AND syncStatus != 3")
    LiveData<TaskEntity> getById(String id);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity getByIdSync(String id);

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = 0 AND syncStatus != 3 ORDER BY priority DESC, deadline ASC")
    LiveData<List<TaskEntity>> getIncompleteTasks(String userId);
}
