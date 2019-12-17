package com.tasks._dagger.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tasks.data.repository.TasksRepository;

import java.lang.reflect.Constructor;

/**
 * Author: murphy
 * Description:
 */
public class TaskViewModelFactory implements ViewModelProvider.Factory {

    private TasksRepository tasksRepository;

    public TaskViewModelFactory(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (tasksRepository == null) {
            throw new IllegalArgumentException("ViewModel arguments " +
                    "'tasksRepository' cannot be null");
        }
        try {
            Constructor<T> constructor = modelClass.getConstructor(TasksRepository.class);
            return constructor.newInstance(tasksRepository);
        }catch (Exception e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("cannot create ViewModel " +
                "named: " + modelClass.getName());
    }
}
