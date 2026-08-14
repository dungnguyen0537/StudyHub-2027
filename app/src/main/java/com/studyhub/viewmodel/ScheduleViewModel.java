package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.repository.ScheduleRepository;
import com.studyhub.repository.SubjectRepository;

import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

    private final ScheduleRepository scheduleRepository;
    private final SubjectRepository subjectRepository;
    private final LiveData<List<ScheduleEntity>> allSchedules;
    private final MutableLiveData<Integer> selectedDay = new MutableLiveData<>();
    private final LiveData<List<ScheduleEntity>> schedulesForDay;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduleRepository = new ScheduleRepository(application);
        subjectRepository = new SubjectRepository(application);
        allSchedules = scheduleRepository.getAllSchedules();
        schedulesForDay = Transformations.switchMap(selectedDay, day -> scheduleRepository.getSchedulesByDay(day));
    }

    public LiveData<List<ScheduleEntity>> getAllSchedules() {
        return allSchedules;
    }
    
    public LiveData<List<ScheduleEntity>> getSchedulesByDay(int dayOfWeek) {
        return scheduleRepository.getSchedulesByDay(dayOfWeek);
    }
    
    public void setSelectedDay(int dayOfWeek) {
        selectedDay.setValue(dayOfWeek);
    }

    public Integer getSelectedDayValue() {
        return selectedDay.getValue();
    }

    public LiveData<List<ScheduleEntity>> getSchedulesForDay() {
        return schedulesForDay;
    }
    
    // We need subjects to link schedules to them in the UI
    public LiveData<List<SubjectEntity>> getAllSubjects() {
        return subjectRepository.getAllSubjects();
    }

    public LiveData<SubjectEntity> getSubjectById(String id) {
        return subjectRepository.getSubjectById(id);
    }

    public void insert(ScheduleEntity schedule) {
        scheduleRepository.insertSchedule(schedule);
    }

    public void update(ScheduleEntity schedule) {
        scheduleRepository.updateSchedule(schedule);
    }

    public void delete(ScheduleEntity schedule) {
        scheduleRepository.deleteSchedule(schedule);
    }
}
