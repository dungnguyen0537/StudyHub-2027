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
import com.studyhub.database.entity.ScheduleEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class ScheduleDao_Impl implements ScheduleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScheduleEntity> __insertionAdapterOfScheduleEntity;

  private final EntityDeletionOrUpdateAdapter<ScheduleEntity> __updateAdapterOfScheduleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBySubjectId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public ScheduleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScheduleEntity = new EntityInsertionAdapter<ScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `schedules` (`id`,`userId`,`subjectId`,`dayOfWeek`,`startTime`,`endTime`,`room`,`reminderEnabled`,`reminderMinutesBefore`,`createdAt`,`updatedAt`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ScheduleEntity entity) {
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
        if (entity.getSubjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectId());
        }
        statement.bindLong(4, entity.getDayOfWeek());
        if (entity.getStartTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStartTime());
        }
        if (entity.getEndTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getEndTime());
        }
        if (entity.getRoom() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRoom());
        }
        final int _tmp = entity.isReminderEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getReminderMinutesBefore());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getSyncStatus());
      }
    };
    this.__updateAdapterOfScheduleEntity = new EntityDeletionOrUpdateAdapter<ScheduleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `schedules` SET `id` = ?,`userId` = ?,`subjectId` = ?,`dayOfWeek` = ?,`startTime` = ?,`endTime` = ?,`room` = ?,`reminderEnabled` = ?,`reminderMinutesBefore` = ?,`createdAt` = ?,`updatedAt` = ?,`syncStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ScheduleEntity entity) {
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
        if (entity.getSubjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectId());
        }
        statement.bindLong(4, entity.getDayOfWeek());
        if (entity.getStartTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStartTime());
        }
        if (entity.getEndTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getEndTime());
        }
        if (entity.getRoom() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRoom());
        }
        final int _tmp = entity.isReminderEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getReminderMinutesBefore());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getSyncStatus());
        if (entity.getId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM schedules WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteBySubjectId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM schedules WHERE subjectId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE schedules SET syncStatus = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ScheduleEntity schedule) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfScheduleEntity.insert(schedule);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ScheduleEntity> schedules) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfScheduleEntity.insert(schedules);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ScheduleEntity schedule) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfScheduleEntity.handle(schedule);
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
  public void deleteBySubjectId(final String subjectId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBySubjectId.acquire();
    int _argIndex = 1;
    if (subjectId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, subjectId);
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
      __preparedStmtOfDeleteBySubjectId.release(_stmt);
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
  public LiveData<List<ScheduleEntity>> getAllByUser(final String userId) {
    final String _sql = "SELECT * FROM schedules WHERE userId = ? AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"schedules"}, false, new Callable<List<ScheduleEntity>>() {
      @Override
      @Nullable
      public List<ScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<ScheduleEntity> _result = new ArrayList<ScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduleEntity _item;
            _item = new ScheduleEntity();
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
            final String _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
            }
            _item.setSubjectId(_tmpSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            _item.setDayOfWeek(_tmpDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            _item.setStartTime(_tmpStartTime);
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            _item.setEndTime(_tmpEndTime);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final boolean _tmpReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp != 0;
            _item.setReminderEnabled(_tmpReminderEnabled);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            _item.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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
  public LiveData<List<ScheduleEntity>> getByDayOfWeek(final int dayOfWeek) {
    final String _sql = "SELECT * FROM schedules WHERE dayOfWeek = ? AND syncStatus != 3 ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dayOfWeek);
    return __db.getInvalidationTracker().createLiveData(new String[] {"schedules"}, false, new Callable<List<ScheduleEntity>>() {
      @Override
      @Nullable
      public List<ScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<ScheduleEntity> _result = new ArrayList<ScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduleEntity _item;
            _item = new ScheduleEntity();
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
            final String _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
            }
            _item.setSubjectId(_tmpSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            _item.setDayOfWeek(_tmpDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            _item.setStartTime(_tmpStartTime);
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            _item.setEndTime(_tmpEndTime);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final boolean _tmpReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp != 0;
            _item.setReminderEnabled(_tmpReminderEnabled);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            _item.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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
  public LiveData<List<ScheduleEntity>> getBySubject(final String subjectId) {
    final String _sql = "SELECT * FROM schedules WHERE subjectId = ? AND syncStatus != 3 ORDER BY dayOfWeek ASC, startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subjectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subjectId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"schedules"}, false, new Callable<List<ScheduleEntity>>() {
      @Override
      @Nullable
      public List<ScheduleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<ScheduleEntity> _result = new ArrayList<ScheduleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScheduleEntity _item;
            _item = new ScheduleEntity();
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
            final String _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
            }
            _item.setSubjectId(_tmpSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            _item.setDayOfWeek(_tmpDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            _item.setStartTime(_tmpStartTime);
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            _item.setEndTime(_tmpEndTime);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _item.setRoom(_tmpRoom);
            final boolean _tmpReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp != 0;
            _item.setReminderEnabled(_tmpReminderEnabled);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            _item.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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
  public List<ScheduleEntity> getPendingSync() {
    final String _sql = "SELECT * FROM schedules WHERE syncStatus != 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
      final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
      final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
      final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final List<ScheduleEntity> _result = new ArrayList<ScheduleEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ScheduleEntity _item;
        _item = new ScheduleEntity();
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
        final String _tmpSubjectId;
        if (_cursor.isNull(_cursorIndexOfSubjectId)) {
          _tmpSubjectId = null;
        } else {
          _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
        }
        _item.setSubjectId(_tmpSubjectId);
        final int _tmpDayOfWeek;
        _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
        _item.setDayOfWeek(_tmpDayOfWeek);
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        _item.setStartTime(_tmpStartTime);
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        _item.setEndTime(_tmpEndTime);
        final String _tmpRoom;
        if (_cursor.isNull(_cursorIndexOfRoom)) {
          _tmpRoom = null;
        } else {
          _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
        }
        _item.setRoom(_tmpRoom);
        final boolean _tmpReminderEnabled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
        _tmpReminderEnabled = _tmp != 0;
        _item.setReminderEnabled(_tmpReminderEnabled);
        final int _tmpReminderMinutesBefore;
        _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
        _item.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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
  public LiveData<ScheduleEntity> getById(final String id) {
    final String _sql = "SELECT * FROM schedules WHERE id = ? AND syncStatus != 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"schedules"}, false, new Callable<ScheduleEntity>() {
      @Override
      @Nullable
      public ScheduleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final ScheduleEntity _result;
          if (_cursor.moveToFirst()) {
            _result = new ScheduleEntity();
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
            final String _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
            }
            _result.setSubjectId(_tmpSubjectId);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            _result.setDayOfWeek(_tmpDayOfWeek);
            final String _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            }
            _result.setStartTime(_tmpStartTime);
            final String _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            }
            _result.setEndTime(_tmpEndTime);
            final String _tmpRoom;
            if (_cursor.isNull(_cursorIndexOfRoom)) {
              _tmpRoom = null;
            } else {
              _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
            }
            _result.setRoom(_tmpRoom);
            final boolean _tmpReminderEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp != 0;
            _result.setReminderEnabled(_tmpReminderEnabled);
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            _result.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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
  public ScheduleEntity getByIdSync(final String id) {
    final String _sql = "SELECT * FROM schedules WHERE id = ?";
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
      final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
      final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "room");
      final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
      final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final ScheduleEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ScheduleEntity();
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
        final String _tmpSubjectId;
        if (_cursor.isNull(_cursorIndexOfSubjectId)) {
          _tmpSubjectId = null;
        } else {
          _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
        }
        _result.setSubjectId(_tmpSubjectId);
        final int _tmpDayOfWeek;
        _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
        _result.setDayOfWeek(_tmpDayOfWeek);
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        _result.setStartTime(_tmpStartTime);
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        _result.setEndTime(_tmpEndTime);
        final String _tmpRoom;
        if (_cursor.isNull(_cursorIndexOfRoom)) {
          _tmpRoom = null;
        } else {
          _tmpRoom = _cursor.getString(_cursorIndexOfRoom);
        }
        _result.setRoom(_tmpRoom);
        final boolean _tmpReminderEnabled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfReminderEnabled);
        _tmpReminderEnabled = _tmp != 0;
        _result.setReminderEnabled(_tmpReminderEnabled);
        final int _tmpReminderMinutesBefore;
        _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
        _result.setReminderMinutesBefore(_tmpReminderMinutesBefore);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
