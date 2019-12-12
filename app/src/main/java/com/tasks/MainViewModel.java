package com.tasks;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tasks.dagger.base.DaggerViewModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.Date;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

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

    public void addTask() {
        Log.d("Test", "addTask: ");

        TaskModel task = new TaskModel("work11" + new Random().nextInt(), "ccccc11", "Work", new Date(), false);
        repository.addTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }

}
