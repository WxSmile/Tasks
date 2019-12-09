package com.tasks.data.source;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: task data source
 */
public interface TasksDataSource {

    Completable addTask(TaskEntity task);

    Completable updateTask(String taskName, boolean completed);

    LiveData<List<TaskModel>> getHotTasks();

    LiveData<List<TaskModel>> getCategoryTasks(String category);

    Completable addCategory(CategoryEntity categoryEntity);
}
