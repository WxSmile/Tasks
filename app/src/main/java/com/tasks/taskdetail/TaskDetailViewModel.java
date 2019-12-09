package com.tasks.taskdetail;

import androidx.annotation.NonNull;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.repository.TasksRepository;

public class TaskDetailViewModel extends DaggerViewModel {

    private TasksRepository repository;

    public TaskDetailViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
