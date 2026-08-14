package com.studyhub.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyhub.constant.FirestoreConstants;
import com.studyhub.constant.SyncStatus;
import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.ScheduleDao;
import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.model.Schedule;
import com.studyhub.service.SyncManager;
import com.studyhub.utils.AppExecutors;
import com.studyhub.utils.NetworkUtils;

import java.util.List;
import java.util.UUID;

public class ScheduleRepository {

    private final ScheduleDao scheduleDao;
    private final FirebaseFirestore firestore;
    private final Application application;

    public ScheduleRepository(Application application) {
        this.application = application;
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        this.scheduleDao = db.scheduleDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public LiveData<List<ScheduleEntity>> getAllSchedules() {
        refreshSchedulesFromCloud();
        return scheduleDao.getAllByUser(getCurrentUserId());
    }
    
    public LiveData<List<ScheduleEntity>> getSchedulesByDay(int dayOfWeek) {
        return scheduleDao.getByDayOfWeek(dayOfWeek);
    }

    public void insertSchedule(ScheduleEntity schedule) {
        schedule.setId(UUID.randomUUID().toString());
        schedule.setUserId(getCurrentUserId());
        schedule.setCreatedAt(System.currentTimeMillis());
        schedule.setUpdatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                if (NetworkUtils.isNetworkAvailable(application)) {
                    schedule.setSyncStatus(SyncStatus.SYNCED);
                    scheduleDao.insert(schedule);
                    saveScheduleToCloud(schedule);
                } else {
                    schedule.setSyncStatus(SyncStatus.PENDING_INSERT);
                    scheduleDao.insert(schedule);
                    SyncManager.enqueueSyncWork(application);
                }
                scheduleAlarm(schedule);
            } catch (Exception e) {
                Log.e("ScheduleRepo", "Failed to insert locally!", e);
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    android.widget.Toast.makeText(application, "Lỗi DB: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    public void updateSchedule(ScheduleEntity schedule) {
        schedule.setUpdatedAt(System.currentTimeMillis());
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                schedule.setSyncStatus(SyncStatus.SYNCED);
                scheduleDao.update(schedule);
                saveScheduleToCloud(schedule);
            } else {
                schedule.setSyncStatus(SyncStatus.PENDING_UPDATE);
                scheduleDao.update(schedule);
                SyncManager.enqueueSyncWork(application);
            }
            scheduleAlarm(schedule);
        });
    }

    public void deleteSchedule(ScheduleEntity schedule) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                scheduleDao.deleteById(schedule.getId());
                deleteScheduleFromCloud(schedule.getId());
            } else {
                schedule.setSyncStatus(SyncStatus.PENDING_DELETE);
                scheduleDao.update(schedule);
                SyncManager.enqueueSyncWork(application);
            }
            com.studyhub.utils.AlarmHelper.cancelReminder(application, schedule.getId().hashCode());
        });
    }

    private void scheduleAlarm(ScheduleEntity schedule) {
        if (schedule.isReminderEnabled()) {
            long triggerTime = com.studyhub.utils.DateUtils.getNextOccurrence(schedule.getDayOfWeek(), schedule.getStartTime());
            if (triggerTime != -1) {
                // Subtract reminderMinutesBefore
                triggerTime -= (schedule.getReminderMinutesBefore() * 60 * 1000L);
                com.studyhub.utils.AlarmHelper.setReminder(
                        application,
                        schedule.getId().hashCode(),
                        triggerTime,
                        "Đến giờ học!",
                        "Môn học sẽ bắt đầu lúc " + schedule.getStartTime() + " tại " + schedule.getRoom(),
                        "schedule",
                        schedule.getId()
                );
            }
        } else {
            com.studyhub.utils.AlarmHelper.cancelReminder(application, schedule.getId().hashCode());
        }
    }

    private void saveScheduleToCloud(ScheduleEntity entity) {
        Schedule schedule = new Schedule();
        schedule.setId(entity.getId());
        schedule.setUserId(entity.getUserId());
        schedule.setSubjectId(entity.getSubjectId());
        schedule.setDayOfWeek(entity.getDayOfWeek());
        schedule.setStartTime(entity.getStartTime());
        schedule.setEndTime(entity.getEndTime());
        schedule.setRoom(entity.getRoom());
        schedule.setReminderEnabled(entity.isReminderEnabled());
        schedule.setReminderMinutesBefore(entity.getReminderMinutesBefore());
        schedule.setCreatedAt(entity.getCreatedAt());
        schedule.setUpdatedAt(entity.getUpdatedAt());

        firestore.collection(FirestoreConstants.COLLECTION_SCHEDULES)
                .document(schedule.getId())
                .set(schedule)
                .addOnFailureListener(e -> Log.e("ScheduleRepo", "Failed to save schedule to cloud", e));
    }

    private void deleteScheduleFromCloud(String id) {
        firestore.collection(FirestoreConstants.COLLECTION_SCHEDULES)
                .document(id)
                .delete()
                .addOnFailureListener(e -> Log.e("ScheduleRepo", "Failed to delete schedule", e));
    }

    private void refreshSchedulesFromCloud() {
        if (!NetworkUtils.isNetworkAvailable(application) || getCurrentUserId().isEmpty()) return;

        firestore.collection(FirestoreConstants.COLLECTION_SCHEDULES)
                .whereEqualTo(FirestoreConstants.FIELD_USER_ID, getCurrentUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (Schedule schedule : queryDocumentSnapshots.toObjects(Schedule.class)) {
                            ScheduleEntity localEntity = scheduleDao.getByIdSync(schedule.getId());
                            if (localEntity == null || localEntity.getSyncStatus() == SyncStatus.SYNCED) {
                                ScheduleEntity entity = new ScheduleEntity();
                                entity.setId(schedule.getId());
                                entity.setUserId(schedule.getUserId());
                                entity.setSubjectId(schedule.getSubjectId());
                                entity.setDayOfWeek(schedule.getDayOfWeek());
                                entity.setStartTime(schedule.getStartTime());
                                entity.setEndTime(schedule.getEndTime());
                                entity.setRoom(schedule.getRoom());
                                entity.setReminderEnabled(schedule.isReminderEnabled());
                                entity.setReminderMinutesBefore(schedule.getReminderMinutesBefore());
                                entity.setCreatedAt(schedule.getCreatedAt());
                                entity.setUpdatedAt(schedule.getUpdatedAt());
                                entity.setSyncStatus(SyncStatus.SYNCED);
                                
                                try {
                                    if (localEntity == null) {
                                        scheduleDao.insert(entity);
                                    } else {
                                        scheduleDao.update(entity);
                                    }
                                } catch (android.database.sqlite.SQLiteConstraintException e) {
                                    Log.e("ScheduleRepo", "Foreign key constraint failed. Subject may not exist locally yet: " + entity.getSubjectId());
                                } catch (Exception e) {
                                    Log.e("ScheduleRepo", "Failed to insert/update synced schedule", e);
                                }
                            }
                        }
                    });
                });
    }
}
