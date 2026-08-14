package com.studyhub.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyhub.constant.FirestoreConstants;
import com.studyhub.constant.SyncStatus;
import com.studyhub.database.StudyHubDatabase;
import com.studyhub.database.dao.SubjectDao;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.model.Subject;

import java.util.List;

public class DataSyncWorker extends Worker {

    private static final String TAG = "DataSyncWorker";
    private final StudyHubDatabase db;
    private final FirebaseFirestore firestore;

    public DataSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = StudyHubDatabase.getInstance(getApplicationContext());
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return Result.success(); // User not logged in, nothing to sync
        }

        try {
            syncSubjects();
            
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Sync failed", e);
            return Result.retry();
        }
    }

    private void syncSubjects() {
        SubjectDao subjectDao = db.subjectDao();
        List<SubjectEntity> pendingSubjects = subjectDao.getPendingSync();
        
        for (SubjectEntity entity : pendingSubjects) {
            if (entity.getSyncStatus() == SyncStatus.PENDING_DELETE) {
                // Delete from Firestore
                firestore.collection(FirestoreConstants.COLLECTION_SUBJECTS)
                        .document(entity.getId())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            // After deleted from cloud, delete locally
                            new Thread(() -> subjectDao.deleteById(entity.getId())).start();
                        });
            } else {
                // Insert or Update to Firestore
                Subject subject = new Subject();
                subject.setId(entity.getId());
                subject.setUserId(entity.getUserId());
                subject.setName(entity.getName());
                subject.setCode(entity.getCode());
                subject.setTeacher(entity.getTeacher());
                subject.setCredits(entity.getCredits());
                subject.setRoom(entity.getRoom());
                subject.setColorHex(entity.getColorHex());
                subject.setNote(entity.getNote());
                subject.setFavorite(entity.isFavorite());
                subject.setCreatedAt(entity.getCreatedAt());
                subject.setUpdatedAt(entity.getUpdatedAt());

                firestore.collection(FirestoreConstants.COLLECTION_SUBJECTS)
                        .document(entity.getId())
                        .set(subject)
                        .addOnSuccessListener(aVoid -> {
                            new Thread(() -> subjectDao.updateSyncStatus(entity.getId(), SyncStatus.SYNCED)).start();
                        });
            }
        }
    }
}
