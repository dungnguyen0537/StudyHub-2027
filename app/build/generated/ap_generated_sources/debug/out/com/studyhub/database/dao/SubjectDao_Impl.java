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
import com.studyhub.database.entity.SubjectEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SubjectDao_Impl implements SubjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SubjectEntity> __insertionAdapterOfSubjectEntity;

  private final EntityDeletionOrUpdateAdapter<SubjectEntity> __updateAdapterOfSubjectEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public SubjectDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubjectEntity = new EntityInsertionAdapter<SubjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `subjects` (`id`,`userId`,`name`,`code`,`teacher`,`credits`,`room`,`colorHex`,`note`,`isFavorite`,`createdAt`,`updatedAt`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SubjectEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getCode() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCode());
        }
        if (entity.getTeacher() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTeacher());
        }
        statement.bindLong(6, entity.getCredits());
        if (entity.getRoom() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRoom());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getColorHex());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNote());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
        statement.bindLong(13, entity.getSyncStatus());
      }
    };
    this.__updateAdapterOfSubjectEntity = new EntityDeletionOrUpdateAdapter<SubjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `subjects` SET `id` = ?,`userId` = ?,`name` = ?,`code` = ?,`teacher` = ?,`credits` = ?,`room` = ?,`colorHex` = ?,`note` = ?,`isFavorite` = ?,`createdAt` = ?,`updatedAt` = ?,`syncStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SubjectEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUserId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getCode() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCode());
        }
        if (entity.getTeacher() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTeacher());
        }
        statement.bindLong(6, entity.getCredits());
        if (entity.getRoom() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRoom());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getColorHex());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNote());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
        statement.bindLong(13, entity.getSyncStatus());
        if (entity.getId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM subjects WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE subjects SET syncStatus = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final SubjectEntity subject) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubjectEntity.insert(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<SubjectEntity> subjects) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubjectEntity.insert(subjects);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final SubjectEntity subject) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSubjectEntity.handle(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, id);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public void updateSyncStatus(final String id, final int status) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, status);
    _argIndex = 2;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, id);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateSyncStatus.release(_stmt);
    }
  }

  @Override
  public LiveData<List<SubjectEntity>> getAllByUser(final String userId) {
    final String _sql = "SELECT * FROM subjects WHERE userId = ? AND syncStatus != 3 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"subjects"}, false, new Callable<List<SubjectEntity>>() {
      @Override
      @Nullable
      public List<SubjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<SubjectEntity> _result = new ArrayList<SubjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectEntity _item;
            _item = new SubjectEntity();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item.setUserId(_tmpUserId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _item.setCode(_tmpCode);
            final String _tmpTeacher;
            if (_cursor.isNull(_cursorIndexOfTeacher)) {
              _tmpTeacher = null;
            } else {
              _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            }
            _item.setTeacher(_tmpTeacher);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            _item.setCredits(_tmpCredits);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            _item.setColorHex(_tmpColorHex);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item.setNote(_tmpNote);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item.setFavorite(_tmpIsFavorite);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.setUpdatedAt(_tmpUpdatedAt);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
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
  public LiveData<SubjectEntity> getById(final String id) {
    final String _sql = "SELECT * FROM subjects WHERE id = ? AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"subjects"}, false, new Callable<SubjectEntity>() {
      @Override
      @Nullable
      public SubjectEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final SubjectEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new SubjectEntity();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _result.setId(_tmpId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _result.setUserId(_tmpUserId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _result.setCode(_tmpCode);
            final String _tmpTeacher;
            if (_cursor.isNull(_cursorIndexOfTeacher)) {
              _tmpTeacher = null;
            } else {
              _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            }
            _result.setTeacher(_tmpTeacher);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            _result.setCredits(_tmpCredits);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _result.setRoom(_tmpRoom);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            _result.setColorHex(_tmpColorHex);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _result.setNote(_tmpNote);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result.setFavorite(_tmpIsFavorite);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result.setUpdatedAt(_tmpUpdatedAt);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _result.setSyncStatus(_tmpSyncStatus);
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
  public SubjectEntity getByIdSync(final String id) {
    final String _sql = "SELECT * FROM subjects WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
      final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
      final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
      final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
      final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final SubjectEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new SubjectEntity();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        _result.setUserId(_tmpUserId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpCode;
        if (_cursor.isNull(_cursorIndexOfCode)) {
          _tmpCode = null;
        } else {
          _tmpCode = _cursor.getString(_cursorIndexOfCode);
        }
        _result.setCode(_tmpCode);
        final String _tmpTeacher;
        if (_cursor.isNull(_cursorIndexOfTeacher)) {
          _tmpTeacher = null;
        } else {
          _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
        }
        _result.setTeacher(_tmpTeacher);
        final int _tmpCredits;
        _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
        _result.setCredits(_tmpCredits);
        final String _tmpRoom;
        if (_cursor.isNull(_cursorIndexOfRoom)) {
          _tmpRoom = null;
        } else {
          _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
        }
        _result.setRoom(_tmpRoom);
        final String _tmpColorHex;
        if (_cursor.isNull(_cursorIndexOfColorHex)) {
          _tmpColorHex = null;
        } else {
          _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
        }
        _result.setColorHex(_tmpColorHex);
        final String _tmpNote;
        if (_cursor.isNull(_cursorIndexOfNote)) {
          _tmpNote = null;
        } else {
          _tmpNote = _cursor.getString(_cursorIndexOfNote);
        }
        _result.setNote(_tmpNote);
        final boolean _tmpIsFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp != 0;
        _result.setFavorite(_tmpIsFavorite);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpUpdatedAt;
        _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _result.setUpdatedAt(_tmpUpdatedAt);
        final int _tmpSyncStatus;
        _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
        _result.setSyncStatus(_tmpSyncStatus);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<SubjectEntity>> getFavorites(final String userId) {
    final String _sql = "SELECT * FROM subjects WHERE userId = ? AND isFavorite = 1 AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"subjects"}, false, new Callable<List<SubjectEntity>>() {
      @Override
      @Nullable
      public List<SubjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<SubjectEntity> _result = new ArrayList<SubjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectEntity _item;
            _item = new SubjectEntity();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item.setUserId(_tmpUserId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _item.setCode(_tmpCode);
            final String _tmpTeacher;
            if (_cursor.isNull(_cursorIndexOfTeacher)) {
              _tmpTeacher = null;
            } else {
              _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            }
            _item.setTeacher(_tmpTeacher);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            _item.setCredits(_tmpCredits);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            _item.setColorHex(_tmpColorHex);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item.setNote(_tmpNote);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item.setFavorite(_tmpIsFavorite);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.setUpdatedAt(_tmpUpdatedAt);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
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
  public LiveData<List<SubjectEntity>> search(final String userId, final String query) {
    final String _sql = "SELECT * FROM subjects WHERE userId = ? AND (name LIKE '%' || ? || '%' OR code LIKE '%' || ? || '%') AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 3;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"subjects"}, false, new Callable<List<SubjectEntity>>() {
      @Override
      @Nullable
      public List<SubjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<SubjectEntity> _result = new ArrayList<SubjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectEntity _item;
            _item = new SubjectEntity();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item.setUserId(_tmpUserId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _item.setCode(_tmpCode);
            final String _tmpTeacher;
            if (_cursor.isNull(_cursorIndexOfTeacher)) {
              _tmpTeacher = null;
            } else {
              _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            }
            _item.setTeacher(_tmpTeacher);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            _item.setCredits(_tmpCredits);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            _item.setColorHex(_tmpColorHex);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item.setNote(_tmpNote);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item.setFavorite(_tmpIsFavorite);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.setUpdatedAt(_tmpUpdatedAt);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
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
  public List<SubjectEntity> getPendingSync() {
    final String _sql = "SELECT * FROM subjects WHERE syncStatus != 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
      final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
      final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
      final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
      final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final List<SubjectEntity> _result = new ArrayList<SubjectEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SubjectEntity _item;
        _item = new SubjectEntity();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        _item.setUserId(_tmpUserId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCode;
        if (_cursor.isNull(_cursorIndexOfCode)) {
          _tmpCode = null;
        } else {
          _tmpCode = _cursor.getString(_cursorIndexOfCode);
        }
        _item.setCode(_tmpCode);
        final String _tmpTeacher;
        if (_cursor.isNull(_cursorIndexOfTeacher)) {
          _tmpTeacher = null;
        } else {
          _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
        }
        _item.setTeacher(_tmpTeacher);
        final int _tmpCredits;
        _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
        _item.setCredits(_tmpCredits);
        final String _tmpRoom;
        if (_cursor.isNull(_cursorIndexOfRoom)) {
          _tmpRoom = null;
        } else {
          _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
        }
        _item.setRoom(_tmpRoom);
        final String _tmpColorHex;
        if (_cursor.isNull(_cursorIndexOfColorHex)) {
          _tmpColorHex = null;
        } else {
          _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
        }
        _item.setColorHex(_tmpColorHex);
        final String _tmpNote;
        if (_cursor.isNull(_cursorIndexOfNote)) {
          _tmpNote = null;
        } else {
          _tmpNote = _cursor.getString(_cursorIndexOfNote);
        }
        _item.setNote(_tmpNote);
        final boolean _tmpIsFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp != 0;
        _item.setFavorite(_tmpIsFavorite);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        final long _tmpUpdatedAt;
        _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _item.setUpdatedAt(_tmpUpdatedAt);
        final int _tmpSyncStatus;
        _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
        _item.setSyncStatus(_tmpSyncStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getCount(final String userId) {
    final String _sql = "SELECT COUNT(*) FROM subjects WHERE userId = ? AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"subjects"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
