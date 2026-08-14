package com.studyhub.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.studyhub.database.entity.UserEntity;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(UserEntity user);

    @Update
    void update(UserEntity user);

    @Query("SELECT * FROM users WHERE uid = :uid")
    LiveData<UserEntity> getUserById(String uid);

    @Query("SELECT * FROM users WHERE uid = :uid")
    UserEntity getUserByIdSync(String uid);

    @Query("DELETE FROM users")
    void clearAll();
}
