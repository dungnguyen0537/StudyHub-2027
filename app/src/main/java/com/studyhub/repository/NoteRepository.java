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
import com.studyhub.database.dao.NoteDao;
import com.studyhub.database.entity.NoteEntity;
import com.studyhub.model.Note;
import com.studyhub.service.SyncManager;
import com.studyhub.utils.AppExecutors;
import com.studyhub.utils.NetworkUtils;

import java.util.List;
import java.util.UUID;

public class NoteRepository {

    private final NoteDao noteDao;
    private final FirebaseFirestore firestore;
    private final Application application;

    public NoteRepository(Application application) {
        this.application = application;
        StudyHubDatabase db = StudyHubDatabase.getInstance(application);
        this.noteDao = db.noteDao();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        refreshNotesFromCloud();
        return noteDao.getAllByUser(getCurrentUserId());
    }
    
    public LiveData<NoteEntity> getNoteById(String id) {
        return noteDao.getById(id);
    }
    
    public LiveData<List<NoteEntity>> searchNotes(String query) {
        return noteDao.searchNotes(getCurrentUserId(), query);
    }

    public void insertNote(NoteEntity note) {
        note.setId(UUID.randomUUID().toString());
        note.setUserId(getCurrentUserId());
        note.setCreatedAt(System.currentTimeMillis());
        note.setUpdatedAt(System.currentTimeMillis());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                note.setSyncStatus(SyncStatus.SYNCED);
                noteDao.insert(note);
                saveNoteToCloud(note);
            } else {
                note.setSyncStatus(SyncStatus.PENDING_INSERT);
                noteDao.insert(note);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    public void updateNote(NoteEntity note) {
        note.setUpdatedAt(System.currentTimeMillis());
        
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                note.setSyncStatus(SyncStatus.SYNCED);
                noteDao.update(note);
                saveNoteToCloud(note);
            } else {
                note.setSyncStatus(SyncStatus.PENDING_UPDATE);
                noteDao.update(note);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    public void deleteNote(NoteEntity note) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (NetworkUtils.isNetworkAvailable(application)) {
                noteDao.deleteById(note.getId());
                deleteNoteFromCloud(note.getId());
            } else {
                note.setSyncStatus(SyncStatus.PENDING_DELETE);
                noteDao.update(note);
                SyncManager.enqueueSyncWork(application);
            }
        });
    }

    private void saveNoteToCloud(NoteEntity entity) {
        Note note = new Note();
        note.setId(entity.getId());
        note.setUserId(entity.getUserId());
        note.setSubjectId(entity.getSubjectId());
        note.setTitle(entity.getTitle());
        note.setContent(entity.getContent());
        note.setImageUrls(entity.getImageUrls());
        note.setType(entity.getType());
        note.setFavorite(entity.isFavorite());
        note.setCreatedAt(entity.getCreatedAt());
        note.setUpdatedAt(entity.getUpdatedAt());

        firestore.collection(FirestoreConstants.COLLECTION_NOTES)
                .document(note.getId())
                .set(note)
                .addOnFailureListener(e -> Log.e("NoteRepo", "Failed to save note to cloud", e));
    }

    private void deleteNoteFromCloud(String id) {
        firestore.collection(FirestoreConstants.COLLECTION_NOTES)
                .document(id)
                .delete()
                .addOnFailureListener(e -> Log.e("NoteRepo", "Failed to delete note", e));
    }

    private void refreshNotesFromCloud() {
        if (!NetworkUtils.isNetworkAvailable(application) || getCurrentUserId().isEmpty()) return;

        firestore.collection(FirestoreConstants.COLLECTION_NOTES)
                .whereEqualTo(FirestoreConstants.FIELD_USER_ID, getCurrentUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        for (Note note : queryDocumentSnapshots.toObjects(Note.class)) {
                            NoteEntity localEntity = noteDao.getByIdSync(note.getId());
                            if (localEntity == null || localEntity.getSyncStatus() == SyncStatus.SYNCED) {
                                NoteEntity entity = new NoteEntity();
                                entity.setId(note.getId());
                                entity.setUserId(note.getUserId());
                                entity.setSubjectId(note.getSubjectId());
                                entity.setTitle(note.getTitle());
                                entity.setContent(note.getContent());
                                entity.setImageUrls(note.getImageUrls());
                                entity.setType(note.getType());
                                entity.setFavorite(note.isFavorite());
                                entity.setCreatedAt(note.getCreatedAt());
                                entity.setUpdatedAt(note.getUpdatedAt());
                                entity.setSyncStatus(SyncStatus.SYNCED);
                                
                                noteDao.insert(entity);
                            }
                        }
                    });
                });
    }
}
