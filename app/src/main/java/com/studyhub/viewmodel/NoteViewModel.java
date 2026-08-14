package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.studyhub.database.entity.NoteEntity;
import com.studyhub.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository noteRepository;
    private final LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public LiveData<NoteEntity> getNoteById(String id) {
        return noteRepository.getNoteById(id);
    }

    public LiveData<List<NoteEntity>> searchNotes(String query) {
        return noteRepository.searchNotes(query);
    }

    public void insert(NoteEntity note) {
        noteRepository.insertNote(note);
    }

    public void update(NoteEntity note) {
        noteRepository.updateNote(note);
    }

    public void delete(NoteEntity note) {
        noteRepository.deleteNote(note);
    }
}
