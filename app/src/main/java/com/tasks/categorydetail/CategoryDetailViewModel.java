package com.tasks.categorydetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.tasks._dagger.base.DaggerViewModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoryDetailViewModel extends DaggerViewModel {

    private TasksRepository repository;

    private LiveData<Map<String, List<TaskModel>>> categoryTasksEvent;
    private LiveData<CategoryStatusModel> categoryStatusEvent;

    public Completable updateTaskStatusEvent;
    public Completable deleteTaskEvent;

    private String category;

    public CategoryDetailViewModel(@NonNull TasksRepository repository) {
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

    public void deleteTask(TaskModel model) {
        deleteTaskEvent = repository.deleteTask(model.getName(), model.getDescribe());
        Disposable subscribe = deleteTaskEvent.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {});
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LiveData<CategoryStatusModel> getCategoryStatusEvent() {
        return categoryStatusEvent;
    }

    public LiveData<Map<String, List<TaskModel>>> getCategoryTasksEvent() {
        return categoryTasksEvent;
    }

    public void updateTaskStatus(TaskModel model) {
        Completable updateHotTaskCompletedEvent = repository.updateTask(model.getName(), model.isCompleted());
        Disposable disposable = updateHotTaskCompletedEvent
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {});
    }
}
