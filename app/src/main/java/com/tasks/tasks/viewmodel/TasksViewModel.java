package com.tasks.tasks.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: getTaskDao fragment view`s Model
 */
public class TasksViewModel extends DaggerViewModel {

    private LiveData<List<CategoryStatusModel>> allCategoryStatus = new MutableLiveData<>();
    private LiveData<List<TaskModel>> hotTasks = new MutableLiveData<>();
    private Completable addTaskCompletable;

    private TasksRepository repository;

    public TasksViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public TasksRepository getRepository() {
        return repository;
    }
}
