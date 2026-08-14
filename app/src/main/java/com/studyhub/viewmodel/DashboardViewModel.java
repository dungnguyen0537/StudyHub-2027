package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.repository.ScheduleRepository;
import com.studyhub.repository.SubjectRepository;

import java.util.Calendar;
import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final SubjectRepository subjectRepository;
    private final ScheduleRepository scheduleRepository;
    private final LiveData<List<ScheduleEntity>> todaysSchedules;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        subjectRepository = new SubjectRepository(application);
        scheduleRepository = new ScheduleRepository(application);
        
        // Determine today's day of week
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int today = (day == Calendar.SUNDAY) ? 8 : day;
        
        todaysSchedules = scheduleRepository.getSchedulesByDay(today);
    }

    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return subjectRepository.getAllSubjects();
    }

    public LiveData<List<ScheduleEntity>> getTodaysSchedules() {
        return todaysSchedules;
    }
}
