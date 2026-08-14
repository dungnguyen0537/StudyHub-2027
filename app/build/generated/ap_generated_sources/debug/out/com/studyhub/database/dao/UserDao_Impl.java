package com.studyhub.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studyhub.database.entity.UserEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`uid`,`fullName`,`email`,`studentId`,`className`,`department`,`major`,`phone`,`avatarUrl`,`address`,`birthDate`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserEntity entity) {
        if (entity.getUid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUid());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        if (entity.getStudentId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentId());
        }
        if (entity.getClassName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getClassName());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDepartment());
        }
        if (entity.getMajor() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMajor());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhone());
        }
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAvatarUrl());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAddress());
        }
        statement.bindLong(11, entity.getBirthDate());
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `uid` = ?,`fullName` = ?,`email` = ?,`studentId` = ?,`className` = ?,`department` = ?,`major` = ?,`phone` = ?,`avatarUrl` = ?,`address` = ?,`birthDate` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `uid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserEntity entity) {
        if (entity.getUid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUid());
        }
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        if (entity.getStudentId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentId());
        }
        if (entity.getClassName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getClassName());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDepartment());
        }
        if (entity.getMajor() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMajor());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhone());
        }
        if (entity.getAvatarUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAvatarUrl());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAddress());
        }
        statement.bindLong(11, entity.getBirthDate());
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
        if (entity.getUid() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getUid());
        }
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users";
        return _query;
      }
    };
  }

  @Override
  public void insertOrUpdate(final UserEntity user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserEntity.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final UserEntity user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserEntity.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClearAll.release(_stmt);
    }
  }

  @Override
  public LiveData<UserEntity> getUserById(final String uid) {
    final String _sql = "SELECT * FROM users WHERE uid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uid);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "className");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfMajor = CursorUtil.getColumnIndexOrThrow(_cursor, "major");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfBirthDate = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new UserEntity();
            final String _tmpUid;
            if (_cursor.isNull(_cursorIndexOfUid)) {
              _tmpUid = null;
            } else {
              _tmpUid = _cursor.getString(_cursorIndexOfUid);
            }
            _result.setUid(_tmpUid);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            _result.setFullName(_tmpFullName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpStudentId;
            if (_cursor.isNull(_cursorIndexOfStudentId)) {
              _tmpStudentId = null;
            } else {
              _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
            }
            _result.setStudentId(_tmpStudentId);
            final String _tmpClassName;
            if (_cursor.isNull(_cursorIndexOfClassName)) {
              _tmpClassName = null;
            } else {
              _tmpClassName = _cursor.getString(_cursorIndexOfClassName);
            }
            _result.setClassName(_tmpClassName);
            final String _tmpDepartment;
            if (_cursor.isNull(_cursorIndexOfDepartment)) {
              _tmpDepartment = null;
            } else {
              _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            }
            _result.setDepartment(_tmpDepartment);
            final String _tmpMajor;
            if (_cursor.isNull(_cursorIndexOfMajor)) {
              _tmpMajor = null;
            } else {
              _tmpMajor = _cursor.getString(_cursorIndexOfMajor);
            }
            _result.setMajor(_tmpMajor);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _result.setPhone(_tmpPhone);
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            _result.setAvatarUrl(_tmpAvatarUrl);
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            _result.setAddress(_tmpAddress);
            final long _tmpBirthDate;
            _tmpBirthDate = _cursor.getLong(_cursorIndexOfBirthDate);
            _result.setBirthDate(_tmpBirthDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result.setUpdatedAt(_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public UserEntity getUserByIdSync(final String uid) {
    final String _sql = "SELECT * FROM users WHERE uid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uid);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "className");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfMajor = CursorUtil.getColumnIndexOrThrow(_cursor, "major");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfBirthDate = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDate");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new UserEntity();
        final String _tmpUid;
        if (_cursor.isNull(_cursorIndexOfUid)) {
          _tmpUid = null;
        } else {
          _tmpUid = _cursor.getString(_cursorIndexOfUid);
        }
        _result.setUid(_tmpUid);
        final String _tmpFullName;
        if (_cursor.isNull(_cursorIndexOfFullName)) {
          _tmpFullName = null;
        } else {
          _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
        }
        _result.setFullName(_tmpFullName);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpStudentId;
        if (_cursor.isNull(_cursorIndexOfStudentId)) {
          _tmpStudentId = null;
        } else {
          _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
        }
        _result.setStudentId(_tmpStudentId);
        final String _tmpClassName;
        if (_cursor.isNull(_cursorIndexOfClassName)) {
          _tmpClassName = null;
        } else {
          _tmpClassName = _cursor.getString(_cursorIndexOfClassName);
        }
        _result.setClassName(_tmpClassName);
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        _result.setDepartment(_tmpDepartment);
        final String _tmpMajor;
        if (_cursor.isNull(_cursorIndexOfMajor)) {
          _tmpMajor = null;
        } else {
          _tmpMajor = _cursor.getString(_cursorIndexOfMajor);
        }
        _result.setMajor(_tmpMajor);
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        _result.setPhone(_tmpPhone);
        final String _tmpAvatarUrl;
        if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
          _tmpAvatarUrl = null;
        } else {
          _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
        }
        _result.setAvatarUrl(_tmpAvatarUrl);
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        _result.setAddress(_tmpAddress);
        final long _tmpBirthDate;
        _tmpBirthDate = _cursor.getLong(_cursorIndexOfBirthDate);
        _result.setBirthDate(_tmpBirthDate);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpUpdatedAt;
        _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _result.setUpdatedAt(_tmpUpdatedAt);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
