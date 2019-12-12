package com.tasks.data.source.remote;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: Using the internet as a data source.
 */
public class RemoteTasksDataSource implements TasksDataSource {

    @Override
    public Completable addTask(TaskEntity task) {
        return null;
    }

    @Override
    public Completable updateTask(String taskName, boolean completed) {
        return null;
    }

    @Override
    public Completable deleteTask(String taskName, String describe) {
        return null;
    }

    @Override
    public LiveData<List<TaskModel>> getHotTasks() {
        return null;
    }

    @Override
    public LiveData<Map<String, List<TaskModel>>> getCategoryTasks(String category) {
        return null;
    }


    @Override
    public Completable addCategory(CategoryEntity categoryEntity) {
        return null;
    }

    @Override
    public Completable addCategory(List<CategoryEntity> categoryEntity) {
        return null;
    }

    @Override
    public LiveData<List<CategoryStatusModel>> getAllCategoryStatus() {
        return null;
    }

    @Override
    public LiveData<CategoryStatusModel> getCategoryStatus(String category) {
        return null;
    }

}
