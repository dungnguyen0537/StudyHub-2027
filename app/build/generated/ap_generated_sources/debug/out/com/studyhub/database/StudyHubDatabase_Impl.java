package com.studyhub.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.studyhub.database.dao.DeadlineDao;
import com.studyhub.database.dao.DeadlineDao_Impl;
import com.studyhub.database.dao.NoteDao;
import com.studyhub.database.dao.NoteDao_Impl;
import com.studyhub.database.dao.ReminderDao;
import com.studyhub.database.dao.ReminderDao_Impl;
import com.studyhub.database.dao.ScheduleDao;
import com.studyhub.database.dao.ScheduleDao_Impl;
import com.studyhub.database.dao.SubjectDao;
import com.studyhub.database.dao.SubjectDao_Impl;
import com.studyhub.database.dao.TaskDao;
import com.studyhub.database.dao.TaskDao_Impl;
import com.studyhub.database.dao.UserDao;
import com.studyhub.database.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudyHubDatabase_Impl extends StudyHubDatabase {
  private volatile UserDao _userDao;

  private volatile SubjectDao _subjectDao;

  private volatile ScheduleDao _scheduleDao;

  private volatile DeadlineDao _deadlineDao;

  private volatile TaskDao _taskDao;

  private volatile NoteDao _noteDao;

  private volatile ReminderDao _reminderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`uid` TEXT NOT NULL, `fullName` TEXT, `email` TEXT, `studentId` TEXT, `className` TEXT, `department` TEXT, `major` TEXT, `phone` TEXT, `avatarUrl` TEXT, `address` TEXT, `birthDate` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`uid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `subjects` (`id` TEXT NOT NULL, `userId` TEXT, `name` TEXT, `code` TEXT, `teacher` TEXT, `credits` INTEGER NOT NULL, `room` TEXT, `colorHex` TEXT, `note` TEXT, `isFavorite` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subjects_userId` ON `subjects` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subjects_syncStatus` ON `subjects` (`syncStatus`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `schedules` (`id` TEXT NOT NULL, `userId` TEXT, `subjectId` TEXT, `dayOfWeek` INTEGER NOT NULL, `startTime` TEXT, `endTime` TEXT, `room` TEXT, `reminderEnabled` INTEGER NOT NULL, `reminderMinutesBefore` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_schedules_userId` ON `schedules` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_schedules_subjectId` ON `schedules` (`subjectId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_schedules_dayOfWeek` ON `schedules` (`dayOfWeek`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `deadlines` (`id` TEXT NOT NULL, `userId` TEXT, `subjectId` TEXT, `title` TEXT, `description` TEXT, `priority` INTEGER NOT NULL, `dueDate` INTEGER NOT NULL, `attachmentUrl` TEXT, `attachmentName` TEXT, `status` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`subjectId`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_deadlines_userId` ON `deadlines` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_deadlines_subjectId` ON `deadlines` (`subjectId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_deadlines_dueDate` ON `deadlines` (`dueDate`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_deadlines_status` ON `deadlines` (`status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_deadlines_syncStatus` ON `deadlines` (`syncStatus`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` TEXT NOT NULL, `userId` TEXT, `title` TEXT, `priority` INTEGER NOT NULL, `category` TEXT, `isCompleted` INTEGER NOT NULL, `deadline` INTEGER NOT NULL, `notificationEnabled` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_userId` ON `tasks` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_category` ON `tasks` (`category`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_isCompleted` ON `tasks` (`isCompleted`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` TEXT NOT NULL, `userId` TEXT, `subjectId` TEXT, `title` TEXT, `content` TEXT, `type` TEXT, `imageUrlsJson` TEXT, `isFavorite` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_userId` ON `notes` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_subjectId` ON `notes` (`subjectId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_isFavorite` ON `notes` (`isFavorite`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` TEXT NOT NULL, `userId` TEXT, `eventId` TEXT, `eventType` TEXT, `title` TEXT, `triggerTime` INTEGER NOT NULL, `isRepeating` INTEGER NOT NULL, `repeatInterval` TEXT, `isActive` INTEGER NOT NULL, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_userId` ON `reminders` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_eventId` ON `reminders` (`eventId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '134aa40a60b6dc4be2bfc6e14ddc805a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `subjects`");
        db.execSQL("DROP TABLE IF EXISTS `schedules`");
        db.execSQL("DROP TABLE IF EXISTS `deadlines`");
        db.execSQL("DROP TABLE IF EXISTS `tasks`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(13);
        _columnsUsers.put("uid", new TableInfo.Column("uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("fullName", new TableInfo.Column("fullName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("studentId", new TableInfo.Column("studentId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("className", new TableInfo.Column("className", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("department", new TableInfo.Column("department", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("major", new TableInfo.Column("major", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("avatarUrl", new TableInfo.Column("avatarUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("birthDate", new TableInfo.Column("birthDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.studyhub.database.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsSubjects = new HashMap<String, TableInfo.Column>(13);
        _columnsSubjects.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("code", new TableInfo.Column("code", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("teacher", new TableInfo.Column("teacher", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("credits", new TableInfo.Column("credits", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("room", new TableInfo.Column("room", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("colorHex", new TableInfo.Column("colorHex", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjects = new HashSet<TableInfo.Index>(2);
        _indicesSubjects.add(new TableInfo.Index("index_subjects_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesSubjects.add(new TableInfo.Index("index_subjects_syncStatus", false, Arrays.asList("syncStatus"), Arrays.asList("ASC")));
        final TableInfo _infoSubjects = new TableInfo("subjects", _columnsSubjects, _foreignKeysSubjects, _indicesSubjects);
        final TableInfo _existingSubjects = TableInfo.read(db, "subjects");
        if (!_infoSubjects.equals(_existingSubjects)) {
          return new RoomOpenHelper.ValidationResult(false, "subjects(com.studyhub.database.entity.SubjectEntity).\n"
                  + " Expected:\n" + _infoSubjects + "\n"
                  + " Found:\n" + _existingSubjects);
        }
        final HashMap<String, TableInfo.Column> _columnsSchedules = new HashMap<String, TableInfo.Column>(12);
        _columnsSchedules.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("subjectId", new TableInfo.Column("subjectId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("startTime", new TableInfo.Column("startTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("endTime", new TableInfo.Column("endTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("room", new TableInfo.Column("room", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("reminderEnabled", new TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("reminderMinutesBefore", new TableInfo.Column("reminderMinutesBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSchedules = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSchedules.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSchedules = new HashSet<TableInfo.Index>(3);
        _indicesSchedules.add(new TableInfo.Index("index_schedules_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesSchedules.add(new TableInfo.Index("index_schedules_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        _indicesSchedules.add(new TableInfo.Index("index_schedules_dayOfWeek", false, Arrays.asList("dayOfWeek"), Arrays.asList("ASC")));
        final TableInfo _infoSchedules = new TableInfo("schedules", _columnsSchedules, _foreignKeysSchedules, _indicesSchedules);
        final TableInfo _existingSchedules = TableInfo.read(db, "schedules");
        if (!_infoSchedules.equals(_existingSchedules)) {
          return new RoomOpenHelper.ValidationResult(false, "schedules(com.studyhub.database.entity.ScheduleEntity).\n"
                  + " Expected:\n" + _infoSchedules + "\n"
                  + " Found:\n" + _existingSchedules);
        }
        final HashMap<String, TableInfo.Column> _columnsDeadlines = new HashMap<String, TableInfo.Column>(13);
        _columnsDeadlines.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("subjectId", new TableInfo.Column("subjectId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("attachmentUrl", new TableInfo.Column("attachmentUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("attachmentName", new TableInfo.Column("attachmentName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeadlines.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDeadlines = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDeadlines.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION", Arrays.asList("subjectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDeadlines = new HashSet<TableInfo.Index>(5);
        _indicesDeadlines.add(new TableInfo.Index("index_deadlines_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesDeadlines.add(new TableInfo.Index("index_deadlines_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        _indicesDeadlines.add(new TableInfo.Index("index_deadlines_dueDate", false, Arrays.asList("dueDate"), Arrays.asList("ASC")));
        _indicesDeadlines.add(new TableInfo.Index("index_deadlines_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        _indicesDeadlines.add(new TableInfo.Index("index_deadlines_syncStatus", false, Arrays.asList("syncStatus"), Arrays.asList("ASC")));
        final TableInfo _infoDeadlines = new TableInfo("deadlines", _columnsDeadlines, _foreignKeysDeadlines, _indicesDeadlines);
        final TableInfo _existingDeadlines = TableInfo.read(db, "deadlines");
        if (!_infoDeadlines.equals(_existingDeadlines)) {
          return new RoomOpenHelper.ValidationResult(false, "deadlines(com.studyhub.database.entity.DeadlineEntity).\n"
                  + " Expected:\n" + _infoDeadlines + "\n"
                  + " Found:\n" + _existingDeadlines);
        }
        final HashMap<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(11);
        _columnsTasks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("deadline", new TableInfo.Column("deadline", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("notificationEnabled", new TableInfo.Column("notificationEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(3);
        _indicesTasks.add(new TableInfo.Index("index_tasks_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_category", false, Arrays.asList("category"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_isCompleted", false, Arrays.asList("isCompleted"), Arrays.asList("ASC")));
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(db, "tasks");
        if (!_infoTasks.equals(_existingTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "tasks(com.studyhub.database.entity.TaskEntity).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(11);
        _columnsNotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("subjectId", new TableInfo.Column("subjectId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("imageUrlsJson", new TableInfo.Column("imageUrlsJson", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(3);
        _indicesNotes.add(new TableInfo.Index("index_notes_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_subjectId", false, Arrays.asList("subjectId"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_isFavorite", false, Arrays.asList("isFavorite"), Arrays.asList("ASC")));
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.studyhub.database.entity.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(10);
        _columnsReminders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("eventId", new TableInfo.Column("eventId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("eventType", new TableInfo.Column("eventType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("triggerTime", new TableInfo.Column("triggerTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("isRepeating", new TableInfo.Column("isRepeating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("repeatInterval", new TableInfo.Column("repeatInterval", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(2);
        _indicesReminders.add(new TableInfo.Index("index_reminders_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesReminders.add(new TableInfo.Index("index_reminders_eventId", false, Arrays.asList("eventId"), Arrays.asList("ASC")));
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.studyhub.database.entity.ReminderEntity).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "134aa40a60b6dc4be2bfc6e14ddc805a", "6f79d60d7a2152938d3f70a736c5bfd4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","subjects","schedules","deadlines","tasks","notes","reminders");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `subjects`");
      _db.execSQL("DELETE FROM `schedules`");
      _db.execSQL("DELETE FROM `deadlines`");
      _db.execSQL("DELETE FROM `tasks`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `reminders`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SubjectDao.class, SubjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScheduleDao.class, ScheduleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DeadlineDao.class, DeadlineDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TaskDao.class, TaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }

  @Override
  public ScheduleDao scheduleDao() {
    if (_scheduleDao != null) {
      return _scheduleDao;
    } else {
      synchronized(this) {
        if(_scheduleDao == null) {
          _scheduleDao = new ScheduleDao_Impl(this);
        }
        return _scheduleDao;
      }
    }
  }

  @Override
  public DeadlineDao deadlineDao() {
    if (_deadlineDao != null) {
      return _deadlineDao;
    } else {
      synchronized(this) {
        if(_deadlineDao == null) {
          _deadlineDao = new DeadlineDao_Impl(this);
        }
        return _deadlineDao;
      }
    }
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }
}
