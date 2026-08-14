package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.repository.SubjectRepository;

import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private final SubjectRepository repository;
    private final LiveData<List<SubjectEntity>> allSubjects;

    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository = new SubjectRepository(application);
        allSubjects = repository.getAllSubjects();
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return allSubjects;
    }

    public LiveData<SubjectEntity> getSubjectById(String id) {
        return repository.getSubjectById(id);
    }

    public void insert(SubjectEntity subject) {
        repository.insertSubject(subject);
    }

    public void update(SubjectEntity subject) {
        repository.updateSubject(subject);
    }

    public void delete(SubjectEntity subject) {
        repository.deleteSubject(subject);
    }
}
