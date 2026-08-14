package com.studyhub.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.studyhub.database.dao.DeadlineDao;
import com.studyhub.database.dao.NoteDao;
import com.studyhub.database.dao.ReminderDao;
import com.studyhub.database.dao.ScheduleDao;
import com.studyhub.database.dao.SubjectDao;
import com.studyhub.database.dao.TaskDao;
import com.studyhub.database.dao.UserDao;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.NoteEntity;
import com.studyhub.database.entity.ReminderEntity;
import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.database.entity.TaskEntity;
import com.studyhub.database.entity.UserEntity;

@Database(entities = {
        UserEntity.class,
        SubjectEntity.class,
        ScheduleEntity.class,
        DeadlineEntity.class,
        TaskEntity.class,
        NoteEntity.class,
        ReminderEntity.class
}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class StudyHubDatabase extends RoomDatabase {

    private static volatile StudyHubDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract SubjectDao subjectDao();
    public abstract ScheduleDao scheduleDao();
    public abstract DeadlineDao deadlineDao();
    public abstract TaskDao taskDao();
    public abstract NoteDao noteDao();
    public abstract ReminderDao reminderDao();

    public static StudyHubDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StudyHubDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StudyHubDatabase.class, "studyhub_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
