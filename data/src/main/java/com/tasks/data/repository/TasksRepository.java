package com.tasks.data.repository;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.TaskModel;

import java.util.List;

import io.reactivex.Completable;

public interface TasksRepository {

    Completable addCategory(String category);

    Completable addTask(TaskModel task);

    LiveData<List<TaskModel>> getCategoryTasks(String category);

    LiveData<List<TaskModel>> getHotTasks();

    Completable updateTask(String taskName, boolean completed);
}
