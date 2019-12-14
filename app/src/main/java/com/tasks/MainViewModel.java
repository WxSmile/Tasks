package com.tasks;

import androidx.annotation.NonNull;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.repository.TasksRepository;

/**
 * Author: murphy
 * Description: main view`s model
 */
public class MainViewModel extends DaggerViewModel {

    private TasksRepository repository;

    public MainViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }



}
