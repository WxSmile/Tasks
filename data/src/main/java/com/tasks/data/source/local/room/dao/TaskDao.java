package com.tasks.data.source.local.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description:  Data Access Object for the Tasks table.
 */

@Dao
public abstract class TaskDao {

    @Transaction
    public void insertTaskTransaction(CategoryEntity category, TaskEntity taskEntity) {
        insertCategory(category);
        insertTask(taskEntity);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCategory(CategoryEntity category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTask(TaskEntity task);

    @Query("SELECT name, describe, category, date, completed FROM Tasks")
    public abstract LiveData<List<TaskModel>> getAllTasks();

    @Query("SELECT name, describe, category, date, completed FROM Tasks WHERE category = :category")
    public abstract LiveData<List<TaskModel>> getCategoryTasks(String category);

    @Query("DELETE FROM tasks WHERE name = :name AND describe = :describe")
    public abstract Completable deleteTask(String name, String describe);

    @Query("UPDATE tasks SET completed = :completed WHERE name = :name")
    public abstract Completable updateTaskStatus(String name, boolean completed);

    @Query("SELECT name, describe, category, date, completed FROM Tasks WHERE date - :current < 86400000 AND date - :current > 0")
    public abstract LiveData<List<TaskModel>> getHotTasks(Date current);

    @Query("SELECT category, count(*) AS total, sum(case completed when 1 then 1 else 0 end) AS completedCount, " +
                            "sum(case completed when 0 then 1 else 0 end) AS notCompletedCount from tasks group by category")
    public abstract LiveData<List<CategoryModel>> getAllCategoryStatus();
}
