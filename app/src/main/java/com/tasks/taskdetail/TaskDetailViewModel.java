package com.tasks.taskdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

public class TaskDetailViewModel extends DaggerViewModel {

    private TasksRepository repository;

    public LiveData<Map<String, List<TaskModel>>> categoryTasksEvent;
    public LiveData<CategoryStatusModel> categoryStatusEvent;
    public Completable updateTaskStatusEvent;
    public Completable deleteTaskEvent;

    private String category;

    public TaskDetailViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void fetchCategoryTasks() {
        categoryTasksEvent = repository.getCategoryTasks(getCategory());
    }

    public void fetchCategoryStatus() {
        categoryStatusEvent = repository.getCategoryStatus(getCategory());
    }

    public void markTaskStatus(String taskName, boolean completed) {
        updateTaskStatusEvent = repository.updateTask(taskName, completed);
    }

    public void deleteTask(String taskName, String taskDescribe) {
        deleteTaskEvent = repository.deleteTask(taskName, taskDescribe);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
