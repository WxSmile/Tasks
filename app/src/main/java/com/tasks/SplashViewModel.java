package com.tasks;

import androidx.annotation.NonNull;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.repository.TasksRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description:
 */
public class SplashViewModel extends DaggerViewModel {

    private TasksRepository repository;

    public SplashViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Completable initCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Work");
        categories.add("Home");
        categories.add("Sport");
        categories.add("General");
        return repository.addCategory(categories);
    }
}
