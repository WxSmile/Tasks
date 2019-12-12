package com.tasks.tasks.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.List;

/**
 * Author: murphy
 * Description: getTaskDao fragment view`s Model
 */
public class TasksViewModel extends DaggerViewModel {

    LiveData<List<CategoryStatusModel>> allCategoryStatusEvent;
    LiveData<List<TaskModel>> hotTasksEvent;

    private TasksRepository repository;

    public TasksViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void fetchAllCategoryStatus() {
        allCategoryStatusEvent = repository.getAllCategoryStatus();
    }

    public void fetchHotTasks() {
        hotTasksEvent = repository.getHotTasks();
    }

    public LiveData<List<CategoryStatusModel>> getAllCategoryStatusEvent() {
        return allCategoryStatusEvent;
    }

    public LiveData<List<TaskModel>> getHotTasksEvent() {
        return hotTasksEvent;
    }
}
