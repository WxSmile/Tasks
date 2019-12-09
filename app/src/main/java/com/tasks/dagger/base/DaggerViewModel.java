package com.tasks.dagger.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.tasks.data.repository.TasksRepository;

/**
 * Author: murphy
 * Description: dagger inject ViewModel
 */
public abstract class DaggerViewModel extends ViewModel {

    public DaggerViewModel(@NonNull TasksRepository repository) {
        // TODO: sub class implement
    }
}
