package com.tasks.data.repository;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

public interface TasksRepository {

    Completable addCategory(String category);

    Completable addCategory(List<String> categories);

    Completable addTask(TaskModel task);

    Completable deleteTask(String name, String describe);

    LiveData<Map<String, List<TaskModel>>> getCategoryTasks(String category);

    LiveData<List<TaskModel>> getHotTasks();

    Completable updateTask(String taskName, boolean completed);

    LiveData<List<CategoryStatusModel>> getAllCategoryStatus();

    LiveData<CategoryStatusModel> getCategoryStatus(String category);
}
