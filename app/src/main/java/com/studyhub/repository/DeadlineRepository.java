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
import com.studyhub.database.dao.DeadlineDao;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.model.Deadline;
import com.studyhub.service.SyncManager;
import com.studyhub.utils.AppExecutors;
import com.studyhub.utils.NetworkUtils;

import java.util.List;
import java.util.UUID;

public class DeadlineRepository {

    private final DeadlineDao deadlineDao;
    private final FirebaseFirestore firestore;
    private final Application application;

    public DeadlineRepository(Application application) {
        this.application = application;
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        this.deadlineDao = db.deadlineDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public LiveData<List<DeadlineEntity>> getAllDeadlines() {
        refreshDeadlinesFromCloud();
        return deadlineDao.getAllByUser(getCurrentUserId());
    }
    
    public LiveData<List<DeadlineEntity>> getUpcomingDeadlines(long currentTime) {
        return deadlineDao.getUpcoming(getCurrentUserId(), currentTime);
    }

    public void insertDeadline(DeadlineEntity deadline) {
        deadline.setId(UUID.randomUUID().toString());
        deadline.setUserId(getCurrentUserId());
        deadline.setCreatedAt(System.currentTimeMillis());
        deadline.setUpdatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                deadline.setSyncStatus(SyncStatus.SYNCED);
                deadlineDao.insert(deadline);
                saveDeadlineToCloud(deadline);
            } else {
                deadline.setSyncStatus(SyncStatus.PENDING_INSERT);
                deadlineDao.insert(deadline);
                SyncManager.enqueueSyncWork(application);
            }
            scheduleAlarm(deadline);
        });
    }

    public void updateDeadline(DeadlineEntity deadline) {
        deadline.setUpdatedAt(System.currentTimeMillis());
        
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                deadline.setSyncStatus(SyncStatus.SYNCED);
                deadlineDao.update(deadline);
                saveDeadlineToCloud(deadline);
            } else {
                deadline.setSyncStatus(SyncStatus.PENDING_UPDATE);
                deadlineDao.update(deadline);
                SyncManager.enqueueSyncWork(application);
            }
            scheduleAlarm(deadline);
        });
    }

    public void deleteDeadline(DeadlineEntity deadline) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                deadlineDao.deleteById(deadline.getId());
                deleteDeadlineFromCloud(deadline.getId());
            } else {
                deadline.setSyncStatus(SyncStatus.PENDING_DELETE);
                deadlineDao.update(deadline);
                SyncManager.enqueueSyncWork(application);
            }
            com.studyhub.utils.AlarmHelper.cancelReminder(application, deadline.getId().hashCode());
        });
    }

    private void scheduleAlarm(DeadlineEntity deadline) {
        // Schedule a reminder 1 day before the deadline
        long triggerTime = deadline.getDueDate() - (24 * 60 * 60 * 1000L);
        if (triggerTime > System.currentTimeMillis() && !deadline.getStatus().equals("COMPLETED")) {
            com.studyhub.utils.AlarmHelper.setReminder(
                    application,
                    deadline.getId().hashCode(),
                    triggerTime,
                    "Sắp đến Deadline!",
                    "Hạn chót cho " + deadline.getTitle() + " là vào ngày mai.",
                    "deadline",
                    deadline.getId()
            );
        } else {
            com.studyhub.utils.AlarmHelper.cancelReminder(application, deadline.getId().hashCode());
        }
    }

    private void saveDeadlineToCloud(DeadlineEntity entity) {
        Deadline deadline = new Deadline();
        deadline.setId(entity.getId());
        deadline.setUserId(entity.getUserId());
        deadline.setSubjectId(entity.getSubjectId());
        deadline.setTitle(entity.getTitle());
        deadline.setDescription(entity.getDescription());
        deadline.setDueDate(entity.getDueDate());
        deadline.setPriority(entity.getPriority());
        deadline.setStatus(entity.getStatus());
        deadline.setAttachmentUrl(entity.getAttachmentUrl());
        deadline.setAttachmentName(entity.getAttachmentName());
        deadline.setCreatedAt(entity.getCreatedAt());
        deadline.setUpdatedAt(entity.getUpdatedAt());

        firestore.collection(FirestoreConstants.COLLECTION_DEADLINES)
                .document(deadline.getId())
                .set(deadline)
                .addOnFailureListener(e -> Log.e("DeadlineRepo", "Failed to save deadline to cloud", e));
    }

    private void deleteDeadlineFromCloud(String id) {
        firestore.collection(FirestoreConstants.COLLECTION_DEADLINES)
                .document(id)
                .delete()
                .addOnFailureListener(e -> Log.e("DeadlineRepo", "Failed to delete deadline", e));
    }

    private void refreshDeadlinesFromCloud() {
        if (!NetworkUtils.isNetworkAvailable(application) || getCurrentUserId().isEmpty()) return;

        firestore.collection(FirestoreConstants.COLLECTION_DEADLINES)
                .whereEqualTo(FirestoreConstants.FIELD_USER_ID, getCurrentUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (Deadline deadline : queryDocumentSnapshots.toObjects(Deadline.class)) {
                            DeadlineEntity localEntity = deadlineDao.getByIdSync(deadline.getId());
                            if (localEntity == null || localEntity.getSyncStatus() == SyncStatus.SYNCED) {
                                DeadlineEntity entity = new DeadlineEntity();
                                entity.setId(deadline.getId());
                                entity.setUserId(deadline.getUserId());
                                entity.setSubjectId(deadline.getSubjectId());
                                entity.setTitle(deadline.getTitle());
                                entity.setDescription(deadline.getDescription());
                                entity.setDueDate(deadline.getDueDate());
                                entity.setPriority(deadline.getPriority());
                                entity.setStatus(deadline.getStatus());
                                entity.setAttachmentUrl(deadline.getAttachmentUrl());
                                entity.setAttachmentName(deadline.getAttachmentName());
                                entity.setCreatedAt(deadline.getCreatedAt());
                                entity.setUpdatedAt(deadline.getUpdatedAt());
                                entity.setSyncStatus(SyncStatus.SYNCED);
                                
                                try {
                                    if (localEntity == null) {
                                        deadlineDao.insert(entity);
                                    } else {
                                        deadlineDao.update(entity);
                                    }
                                } catch (android.database.sqlite.SQLiteConstraintException e) {
                                    Log.e("DeadlineRepo", "Foreign key constraint failed. Subject may not exist locally yet: " + entity.getSubjectId());
                                } catch (Exception e) {
                                    Log.e("DeadlineRepo", "Failed to insert/update synced deadline", e);
                                }
                            }
                        }
                    });
                });
    }
}
