package com.tasks.data.source.remote;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.TaskModel;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: Using the internet as a data source.
 */
public class RemoteTasksDataSource implements TasksDataSource {

    @Override
    public void addTask(TaskEntity task, CategoryEntity categoryEntity) {
    }

    @Override
    public Completable updateTask(String taskName, boolean completed) {
        return null;
    }
    @Override
    public LiveData<List<TaskModel>> getHotTasks() {
        return null;
    }

    @Override
    public LiveData<List<TaskModel>> getCategoryTasks(String category) {
        return null;
    }

}
