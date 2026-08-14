package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.SubjectEntity;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubjectEntity subject);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SubjectEntity> subjects);

    @Update
    void update(SubjectEntity subject);

    @Query("DELETE FROM subjects WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM subjects WHERE userId = :userId AND syncStatus != 3 ORDER BY createdAt DESC")
    LiveData<List<SubjectEntity>> getAllByUser(String userId);

    @Query("SELECT * FROM subjects WHERE id = :id AND syncStatus != 3")
    LiveData<SubjectEntity> getById(String id);

    @Query("SELECT * FROM subjects WHERE id = :id")
    SubjectEntity getByIdSync(String id);

    @Query("SELECT * FROM subjects WHERE userId = :userId AND isFavorite = 1 AND syncStatus != 3")
    LiveData<List<SubjectEntity>> getFavorites(String userId);

    @Query("SELECT * FROM subjects WHERE userId = :userId AND (name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%') AND syncStatus != 3")
    LiveData<List<SubjectEntity>> search(String userId, String query);

    @Query("SELECT * FROM subjects WHERE syncStatus != 0")
    List<SubjectEntity> getPendingSync();

    @Query("UPDATE subjects SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(String id, int status);

    @Query("SELECT COUNT(*) FROM subjects WHERE userId = :userId AND syncStatus != 3")
    LiveData<Integer> getCount(String userId);
}
