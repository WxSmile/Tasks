package com.tasks.data.source.local;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.TaskModel;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.room.dao.TaskDao;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: Using the Room database as a data source.
 */
public class LocalTasksDataSource implements TasksDataSource {

    private final TaskDao taskDao;

    public LocalTasksDataSource(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void addTask(TaskEntity task, CategoryEntity categoryEntity) {
        taskDao.insertTaskTransaction(categoryEntity, task);
    }

    @Override
    public Completable updateTask(String taskName, boolean completed) {
        return taskDao.updateTaskStatus(taskName, completed);
    }

    @Override
    public LiveData<List<TaskModel>> getHotTasks() {
        return taskDao.getHotTasks(Calendar.getInstance().getTime());
    }

    @Override
    public LiveData<List<TaskModel>> getCategoryTasks(String category) {
        return taskDao.getCategoryTasks(category);
    }
}
