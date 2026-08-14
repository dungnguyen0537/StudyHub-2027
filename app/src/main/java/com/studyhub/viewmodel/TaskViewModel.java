package com.studyhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.studyhub.database.entity.TaskEntity;
import com.studyhub.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository taskRepository;
    private final LiveData<List<TaskEntity>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<TaskEntity>> getIncompleteTasks() {
        return taskRepository.getIncompleteTasks();
    }

    public LiveData<TaskEntity> getTaskById(String id) {
        return taskRepository.getTaskById(id);
    }

    public void insert(TaskEntity task) {
        taskRepository.insertTask(task);
    }

    public void update(TaskEntity task) {
        taskRepository.updateTask(task);
    }

    public void delete(TaskEntity task) {
        taskRepository.deleteTask(task);
    }
}
