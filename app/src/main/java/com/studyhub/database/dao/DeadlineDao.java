package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.DeadlineEntity;

import java.util.List;

@Dao
public interface DeadlineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DeadlineEntity deadline);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DeadlineEntity> deadlines);

    @Update
    void update(DeadlineEntity deadline);

    @Query("DELETE FROM deadlines WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM deadlines WHERE subjectId = :subjectId")
    void deleteBySubjectId(String subjectId);

    @Query("UPDATE deadlines SET syncStatus = 3 WHERE subjectId = :subjectId")
    void markPendingDeleteBySubjectId(String subjectId);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND syncStatus != 3 ORDER BY dueDate ASC")
    LiveData<List<DeadlineEntity>> getAllByUser(String userId);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND dueDate >= :startOfDay AND dueDate <= :endOfDay AND syncStatus != 3 ORDER BY dueDate ASC")
    LiveData<List<DeadlineEntity>> getByDateRange(String userId, long startOfDay, long endOfDay);

    @Query("SELECT * FROM deadlines WHERE subjectId = :subjectId AND syncStatus != 3 ORDER BY dueDate ASC")
    LiveData<List<DeadlineEntity>> getBySubject(String subjectId);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND status = :status AND syncStatus != 3 ORDER BY dueDate ASC")
    LiveData<List<DeadlineEntity>> getByStatus(String userId, String status);

    @Query("UPDATE deadlines SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    void updateStatus(String id, String status, long updatedAt);

    @Query("SELECT COUNT(*) FROM deadlines WHERE userId = :userId AND status = 'COMPLETED' AND syncStatus != 3")
    LiveData<Integer> getCompletedCount(String userId);

    @Query("SELECT COUNT(*) FROM deadlines WHERE userId = :userId AND status != 'COMPLETED' AND dueDate < :now AND syncStatus != 3")
    LiveData<Integer> getOverdueCount(String userId, long now);

    @Query("SELECT * FROM deadlines WHERE syncStatus != 0")
    List<DeadlineEntity> getPendingSync();

    @Query("UPDATE deadlines SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(String id, int status);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND title LIKE '%' || :query || '%' AND syncStatus != 3")
    LiveData<List<DeadlineEntity>> search(String userId, String query);

    @Query("SELECT * FROM deadlines WHERE id = :id AND syncStatus != 3")
    LiveData<DeadlineEntity> getById(String id);

    @Query("SELECT * FROM deadlines WHERE id = :id")
    DeadlineEntity getByIdSync(String id);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND dueDate >= :now AND status != 'COMPLETED' AND syncStatus != 3 ORDER BY dueDate ASC")
    LiveData<List<DeadlineEntity>> getUpcoming(String userId, long now);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND dueDate >= :now AND status != 'COMPLETED' AND syncStatus != 3 ORDER BY dueDate ASC")
    List<DeadlineEntity> getUpcomingSync(String userId, long now);

    @Query("SELECT * FROM deadlines WHERE userId = :userId AND status != 'COMPLETED' AND syncStatus != 3 ORDER BY dueDate ASC")
    List<DeadlineEntity> getUncompletedSync(String userId);
}
