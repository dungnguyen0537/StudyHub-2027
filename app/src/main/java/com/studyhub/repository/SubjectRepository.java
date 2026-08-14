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
import com.studyhub.database.dao.SubjectDao;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.model.Subject;
import com.studyhub.service.SyncManager;
import com.studyhub.utils.AppExecutors;
import com.studyhub.utils.NetworkUtils;

import java.util.List;
import java.util.UUID;

public class SubjectRepository {

    private final SubjectDao subjectDao;
    private final FirebaseFirestore firestore;
    private final Application application;

    public SubjectRepository(Application application) {
        this.application = application;
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        this.subjectDao = db.subjectDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        refreshSubjectsFromCloud();
        return subjectDao.getAllByUser(getCurrentUserId());
    }

    public LiveData<SubjectEntity> getSubjectById(String id) {
        return subjectDao.getById(id);
    }

    public void insertSubject(SubjectEntity subject) {
        subject.setId(UUID.randomUUID().toString());
        subject.setUserId(getCurrentUserId());
        subject.setCreatedAt(System.currentTimeMillis());
        subject.setUpdatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                subject.setSyncStatus(SyncStatus.SYNCED);
                subjectDao.insert(subject);
                saveSubjectToCloud(subject);
            } else {
                subject.setSyncStatus(SyncStatus.PENDING_INSERT);
                subjectDao.insert(subject);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    public void updateSubject(SubjectEntity subject) {
        subject.setUpdatedAt(System.currentTimeMillis());
        
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                subject.setSyncStatus(SyncStatus.SYNCED);
                subjectDao.update(subject);
                saveSubjectToCloud(subject);
            } else {
                subject.setSyncStatus(SyncStatus.PENDING_UPDATE);
                subjectDao.update(subject);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    public void deleteSubject(SubjectEntity subject) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                subjectDao.deleteById(subject.getId());
                deleteSubjectFromCloud(subject.getId());
            } else {
                subject.setSyncStatus(SyncStatus.PENDING_DELETE);
                subjectDao.update(subject);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    private void saveSubjectToCloud(SubjectEntity entity) {
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
                .document(subject.getId())
                .set(subject)
                .addOnFailureListener(e -> Log.e("SubjectRepo", "Failed to save subject to cloud", e));
    }

    private void deleteSubjectFromCloud(String id) {
        firestore.collection(FirestoreConstants.COLLECTION_SUBJECTS)
                .document(id)
                .delete()
                .addOnFailureListener(e -> Log.e("SubjectRepo", "Failed to delete subject", e));
    }

    private void refreshSubjectsFromCloud() {
        if (!NetworkUtils.isNetworkAvailable(application) || getCurrentUserId().isEmpty()) return;

        firestore.collection(FirestoreConstants.COLLECTION_SUBJECTS)
                .whereEqualTo(FirestoreConstants.FIELD_USER_ID, getCurrentUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (Subject subject : queryDocumentSnapshots.toObjects(Subject.class)) {
                            // Only update local DB if not pending local changes
                            SubjectEntity localSubject = subjectDao.getByIdSync(subject.getId());
                            if (localSubject == null || localSubject.getSyncStatus() == SyncStatus.SYNCED) {
                                SubjectEntity entity = new SubjectEntity();
                                entity.setId(subject.getId());
                                entity.setUserId(subject.getUserId());
                                entity.setName(subject.getName());
                                entity.setCode(subject.getCode());
                                entity.setTeacher(subject.getTeacher());
                                entity.setCredits(subject.getCredits());
                                entity.setRoom(subject.getRoom());
                                entity.setColorHex(subject.getColorHex());
                                entity.setNote(subject.getNote());
                                entity.setFavorite(subject.isFavorite());
                                entity.setCreatedAt(subject.getCreatedAt());
                                entity.setUpdatedAt(subject.getUpdatedAt());
                                entity.setSyncStatus(SyncStatus.SYNCED);
                                
                                if (localSubject == null) {
                                    subjectDao.insert(entity);
                                } else {
                                    subjectDao.update(entity);
                                }
                            }
                        }
                    });
                });
    }
}
