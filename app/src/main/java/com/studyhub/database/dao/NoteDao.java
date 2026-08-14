package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Update
    void update(NoteEntity note);

    @Query("DELETE FROM notes WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM notes WHERE userId = :userId AND syncStatus != 3 ORDER BY updatedAt DESC")
    LiveData<List<NoteEntity>> getAllByUser(String userId);

    @Query("SELECT * FROM notes WHERE userId = :userId AND subjectId = :subjectId AND syncStatus != 3 ORDER BY updatedAt DESC")
    LiveData<List<NoteEntity>> getBySubject(String userId, String subjectId);

    @Query("SELECT * FROM notes WHERE userId = :userId AND isFavorite = 1 AND syncStatus != 3 ORDER BY updatedAt DESC")
    LiveData<List<NoteEntity>> getFavorites(String userId);

    @Query("UPDATE notes SET isFavorite = :isFavorite, updatedAt = :updatedAt WHERE id = :id")
    void updateFavoriteStatus(String id, boolean isFavorite, long updatedAt);

    @Query("SELECT * FROM notes WHERE syncStatus != 0")
    List<NoteEntity> getPendingSync();

    @Query("UPDATE notes SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(String id, int status);

    @Query("SELECT * FROM notes WHERE id = :id AND syncStatus != 3")
    LiveData<NoteEntity> getById(String id);

    @Query("SELECT * FROM notes WHERE id = :id")
    NoteEntity getByIdSync(String id);

    @Query("SELECT * FROM notes WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') AND syncStatus != 3 ORDER BY updatedAt DESC")
    LiveData<List<NoteEntity>> searchNotes(String userId, String query);
}
