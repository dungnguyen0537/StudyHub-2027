package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.repository.DeadlineRepository;
import com.studyhub.repository.SubjectRepository;

import java.util.List;

public class DeadlineViewModel extends AndroidViewModel {

    private final DeadlineRepository deadlineRepository;
    private final SubjectRepository subjectRepository;
    private final LiveData<List<DeadlineEntity>> allDeadlines;

    public DeadlineViewModel(@NonNull Application application) {
        super(application);
        deadlineRepository = new DeadlineRepository(application);
        subjectRepository = new SubjectRepository(application);
        allDeadlines = deadlineRepository.getAllDeadlines();
    }

    public LiveData<List<DeadlineEntity>> getAllDeadlines() {
        return allDeadlines;
    }
    
    public LiveData<List<DeadlineEntity>> getUpcomingDeadlines() {
        return deadlineRepository.getUpcomingDeadlines(System.currentTimeMillis());
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return subjectRepository.getAllSubjects();
    }

    public void insert(DeadlineEntity deadline) {
        deadlineRepository.insertDeadline(deadline);
    }

    public void update(DeadlineEntity deadline) {
        deadlineRepository.updateDeadline(deadline);
    }

    public void delete(DeadlineEntity deadline) {
        deadlineRepository.deleteDeadline(deadline);
    }
}
