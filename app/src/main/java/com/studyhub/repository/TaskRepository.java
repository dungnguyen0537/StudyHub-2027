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
import com.studyhub.database.dao.TaskDao;
import com.studyhub.database.entity.TaskEntity;
import com.studyhub.model.Task;
import com.studyhub.service.SyncManager;
import com.studyhub.utils.AppExecutors;
import com.studyhub.utils.NetworkUtils;

import java.util.List;
import java.util.UUID;

public class TaskRepository {

    private final TaskDao taskDao;
    private final FirebaseFirestore firestore;
    private final Application application;

    public TaskRepository(Application application) {
        this.application = application;
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        this.taskDao = db.taskDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        refreshTasksFromCloud();
        return taskDao.getAllByUser(getCurrentUserId());
    }
    
    public LiveData<List<TaskEntity>> getIncompleteTasks() {
        return taskDao.getActiveTasks(getCurrentUserId());
    }

    public LiveData<TaskEntity> getTaskById(String id) {
        return taskDao.getById(id);
    }

    public void insertTask(TaskEntity task) {
        task.setId(UUID.randomUUID().toString());
        task.setUserId(getCurrentUserId());
        task.setCreatedAt(System.currentTimeMillis());
        task.setUpdatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                task.setSyncStatus(SyncStatus.SYNCED);
                taskDao.insert(task);
                saveTaskToCloud(task);
            } else {
                task.setSyncStatus(SyncStatus.PENDING_INSERT);
                taskDao.insert(task);
                SyncManager.enqueueSyncWork(application);
            }
            scheduleAlarm(task);
        });
    }

    public void updateTask(TaskEntity task) {
        task.setUpdatedAt(System.currentTimeMillis());
        
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                task.setSyncStatus(SyncStatus.SYNCED);
                taskDao.update(task);
                saveTaskToCloud(task);
            } else {
                task.setSyncStatus(SyncStatus.PENDING_UPDATE);
                taskDao.update(task);
                SyncManager.enqueueSyncWork(application);
            }
            scheduleAlarm(task);
        });
    }

    public void deleteTask(TaskEntity task) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                taskDao.deleteById(task.getId());
                deleteTaskFromCloud(task.getId());
            } else {
                task.setSyncStatus(SyncStatus.PENDING_DELETE);
                taskDao.update(task);
                SyncManager.enqueueSyncWork(application);
            }
            com.studyhub.utils.AlarmHelper.cancelReminder(application, task.getId().hashCode());
        });
    }

    private void scheduleAlarm(TaskEntity task) {
        if (task.isNotificationEnabled() && task.getDeadline() > 0) {
            long triggerTime = task.getDeadline();
            if (triggerTime > System.currentTimeMillis() && !task.isCompleted()) {
                com.studyhub.utils.AlarmHelper.setReminder(
                        application,
                        task.getId().hashCode(),
                        triggerTime,
                        "Đến hạn Công việc!",
                        "Đã đến hạn chót cho: " + task.getTitle(),
                        "task",
                        task.getId()
                );
            } else {
                com.studyhub.utils.AlarmHelper.cancelReminder(application, task.getId().hashCode());
            }
        } else {
            com.studyhub.utils.AlarmHelper.cancelReminder(application, task.getId().hashCode());
        }
    }

    private void saveTaskToCloud(TaskEntity entity) {
        Task task = new Task();
        task.setId(entity.getId());
        task.setUserId(entity.getUserId());
        task.setTitle(entity.getTitle());
        task.setCompleted(entity.isCompleted());
        task.setPriority(entity.getPriority());
        task.setCategory(entity.getCategory());
        task.setDeadline(entity.getDeadline());
        task.setNotificationEnabled(entity.isNotificationEnabled());
        task.setCreatedAt(entity.getCreatedAt());
        task.setUpdatedAt(entity.getUpdatedAt());

        firestore.collection(FirestoreConstants.COLLECTION_TASKS)
                .document(task.getId())
                .set(task)
                .addOnFailureListener(e -> Log.e("TaskRepo", "Failed to save task to cloud", e));
    }

    private void deleteTaskFromCloud(String id) {
        firestore.collection(FirestoreConstants.COLLECTION_TASKS)
                .document(id)
                .delete()
                .addOnFailureListener(e -> Log.e("TaskRepo", "Failed to delete task", e));
    }

    private void refreshTasksFromCloud() {
        if (!NetworkUtils.isNetworkAvailable(application) || getCurrentUserId().isEmpty()) return;

        firestore.collection(FirestoreConstants.COLLECTION_TASKS)
                .whereEqualTo(FirestoreConstants.FIELD_USER_ID, getCurrentUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (Task task : queryDocumentSnapshots.toObjects(Task.class)) {
                            TaskEntity localEntity = taskDao.getByIdSync(task.getId());
                            if (localEntity == null || localEntity.getSyncStatus() == SyncStatus.SYNCED) {
                                TaskEntity entity = new TaskEntity();
                                entity.setId(task.getId());
                                entity.setUserId(task.getUserId());
                                entity.setTitle(task.getTitle());
                                entity.setCompleted(task.isCompleted());
                                entity.setPriority(task.getPriority());
                                entity.setCategory(task.getCategory());
                                entity.setDeadline(task.getDeadline());
                                entity.setNotificationEnabled(task.isNotificationEnabled());
                                entity.setCreatedAt(task.getCreatedAt());
                                entity.setUpdatedAt(task.getUpdatedAt());
                                entity.setSyncStatus(SyncStatus.SYNCED);
                                
                                taskDao.insert(entity);
                            }
                        }
                    });
                });
    }
}
