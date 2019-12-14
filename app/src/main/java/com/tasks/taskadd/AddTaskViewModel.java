package com.tasks.taskadd;

import androidx.annotation.NonNull;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.Date;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: murphy
 * Description: add task view`s model
 */
public class AddTaskViewModel extends DaggerViewModel {

    private TasksRepository repository;

    public AddTaskViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Completable addTask() {
        TaskModel task = new TaskModel("work11" + new Random().nextInt(), "ccccc11", "Work", new Date(), false);
        return repository.addTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
