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
import com.studyhub.database.entity.ReminderEntity;
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
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReminderEntity> __insertionAdapterOfReminderEntity;

  private final EntityDeletionOrUpdateAdapter<ReminderEntity> __updateAdapterOfReminderEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByEventId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateActiveStatus;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminderEntity = new EntityInsertionAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reminders` (`id`,`userId`,`eventId`,`eventType`,`title`,`triggerTime`,`isRepeating`,`repeatInterval`,`isActive`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ReminderEntity entity) {
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
        if (entity.getEventId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEventId());
        }
        if (entity.getEventType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEventType());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        statement.bindLong(6, entity.getTriggerTime());
        final int _tmp = entity.isRepeating() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getRepeatInterval() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRepeatInterval());
        }
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getSyncStatus());
      }
    };
    this.__updateAdapterOfReminderEntity = new EntityDeletionOrUpdateAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `id` = ?,`userId` = ?,`eventId` = ?,`eventType` = ?,`title` = ?,`triggerTime` = ?,`isRepeating` = ?,`repeatInterval` = ?,`isActive` = ?,`syncStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ReminderEntity entity) {
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
        if (entity.getEventId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEventId());
        }
        if (entity.getEventType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEventType());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        statement.bindLong(6, entity.getTriggerTime());
        final int _tmp = entity.isRepeating() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getRepeatInterval() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRepeatInterval());
        }
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getSyncStatus());
        if (entity.getId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reminders WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByEventId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reminders WHERE eventId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateActiveStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reminders SET isActive = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ReminderEntity reminder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfReminderEntity.insert(reminder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ReminderEntity> reminders) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfReminderEntity.insert(reminders);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ReminderEntity reminder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfReminderEntity.handle(reminder);
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
  public void deleteByEventId(final String eventId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByEventId.acquire();
    int _argIndex = 1;
    if (eventId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, eventId);
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
      __preparedStmtOfDeleteByEventId.release(_stmt);
    }
  }

  @Override
  public void updateActiveStatus(final String id, final boolean isActive) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateActiveStatus.acquire();
    int _argIndex = 1;
    final int _tmp = isActive ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
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
      __preparedStmtOfUpdateActiveStatus.release(_stmt);
    }
  }

  @Override
  public List<ReminderEntity> getActiveReminders(final String userId) {
    final String _sql = "SELECT * FROM reminders WHERE userId = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfTriggerTime = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerTime");
      final int _cursorIndexOfIsRepeating = CursorUtil.getColumnIndexOrThrow(_cursor, "isRepeating");
      final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ReminderEntity _item;
        _item = new ReminderEntity();
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
        final String _tmpEventId;
        if (_cursor.isNull(_cursorIndexOfEventId)) {
          _tmpEventId = null;
        } else {
          _tmpEventId = _cursor.getString(_cursorIndexOfEventId);
        }
        _item.setEventId(_tmpEventId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _item.setEventType(_tmpEventType);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final long _tmpTriggerTime;
        _tmpTriggerTime = _cursor.getLong(_cursorIndexOfTriggerTime);
        _item.setTriggerTime(_tmpTriggerTime);
        final boolean _tmpIsRepeating;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRepeating);
        _tmpIsRepeating = _tmp != 0;
        _item.setRepeating(_tmpIsRepeating);
        final String _tmpRepeatInterval;
        if (_cursor.isNull(_cursorIndexOfRepeatInterval)) {
          _tmpRepeatInterval = null;
        } else {
          _tmpRepeatInterval = _cursor.getString(_cursorIndexOfRepeatInterval);
        }
        _item.setRepeatInterval(_tmpRepeatInterval);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _item.setActive(_tmpIsActive);
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
  public ReminderEntity getByEventId(final String eventId) {
    final String _sql = "SELECT * FROM reminders WHERE eventId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (eventId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfTriggerTime = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerTime");
      final int _cursorIndexOfIsRepeating = CursorUtil.getColumnIndexOrThrow(_cursor, "isRepeating");
      final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final ReminderEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ReminderEntity();
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
        final String _tmpEventId;
        if (_cursor.isNull(_cursorIndexOfEventId)) {
          _tmpEventId = null;
        } else {
          _tmpEventId = _cursor.getString(_cursorIndexOfEventId);
        }
        _result.setEventId(_tmpEventId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _result.setEventType(_tmpEventType);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final long _tmpTriggerTime;
        _tmpTriggerTime = _cursor.getLong(_cursorIndexOfTriggerTime);
        _result.setTriggerTime(_tmpTriggerTime);
        final boolean _tmpIsRepeating;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRepeating);
        _tmpIsRepeating = _tmp != 0;
        _result.setRepeating(_tmpIsRepeating);
        final String _tmpRepeatInterval;
        if (_cursor.isNull(_cursorIndexOfRepeatInterval)) {
          _tmpRepeatInterval = null;
        } else {
          _tmpRepeatInterval = _cursor.getString(_cursorIndexOfRepeatInterval);
        }
        _result.setRepeatInterval(_tmpRepeatInterval);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _result.setActive(_tmpIsActive);
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
  public ReminderEntity getById(final String id) {
    final String _sql = "SELECT * FROM reminders WHERE id = ?";
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
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfTriggerTime = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerTime");
      final int _cursorIndexOfIsRepeating = CursorUtil.getColumnIndexOrThrow(_cursor, "isRepeating");
      final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
      final ReminderEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new ReminderEntity();
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
        final String _tmpEventId;
        if (_cursor.isNull(_cursorIndexOfEventId)) {
          _tmpEventId = null;
        } else {
          _tmpEventId = _cursor.getString(_cursorIndexOfEventId);
        }
        _result.setEventId(_tmpEventId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _result.setEventType(_tmpEventType);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final long _tmpTriggerTime;
        _tmpTriggerTime = _cursor.getLong(_cursorIndexOfTriggerTime);
        _result.setTriggerTime(_tmpTriggerTime);
        final boolean _tmpIsRepeating;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRepeating);
        _tmpIsRepeating = _tmp != 0;
        _result.setRepeating(_tmpIsRepeating);
        final String _tmpRepeatInterval;
        if (_cursor.isNull(_cursorIndexOfRepeatInterval)) {
          _tmpRepeatInterval = null;
        } else {
          _tmpRepeatInterval = _cursor.getString(_cursorIndexOfRepeatInterval);
        }
        _result.setRepeatInterval(_tmpRepeatInterval);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _result.setActive(_tmpIsActive);
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
  public LiveData<List<ReminderEntity>> getAllByUser(final String userId) {
    final String _sql = "SELECT * FROM reminders WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"reminders"}, false, new Callable<List<ReminderEntity>>() {
      @Override
      @Nullable
      public List<ReminderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTriggerTime = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerTime");
          final int _cursorIndexOfIsRepeating = CursorUtil.getColumnIndexOrThrow(_cursor, "isRepeating");
          final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReminderEntity _item;
            _item = new ReminderEntity();
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
            final String _tmpEventId;
            if (_cursor.isNull(_cursorIndexOfEventId)) {
              _tmpEventId = null;
            } else {
              _tmpEventId = _cursor.getString(_cursorIndexOfEventId);
            }
            _item.setEventId(_tmpEventId);
            final String _tmpEventType;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmpEventType = null;
            } else {
              _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
            }
            _item.setEventType(_tmpEventType);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final long _tmpTriggerTime;
            _tmpTriggerTime = _cursor.getLong(_cursorIndexOfTriggerTime);
            _item.setTriggerTime(_tmpTriggerTime);
            final boolean _tmpIsRepeating;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRepeating);
            _tmpIsRepeating = _tmp != 0;
            _item.setRepeating(_tmpIsRepeating);
            final String _tmpRepeatInterval;
            if (_cursor.isNull(_cursorIndexOfRepeatInterval)) {
              _tmpRepeatInterval = null;
            } else {
              _tmpRepeatInterval = _cursor.getString(_cursorIndexOfRepeatInterval);
            }
            _item.setRepeatInterval(_tmpRepeatInterval);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item.setActive(_tmpIsActive);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
