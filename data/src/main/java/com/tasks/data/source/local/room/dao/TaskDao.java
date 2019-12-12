package com.tasks.data.source.local.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.CategoryStatusModel;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertCategory(CategoryEntity category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertCategories(List<CategoryEntity> category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertTask(TaskEntity task);

    @Query("SELECT name, describe, category, date, completed FROM Tasks")
    public abstract LiveData<List<TaskModel>> getAllTasks();

    @Query("SELECT name, describe, category, date, completed FROM Tasks WHERE category = :category AND date >= :oneTime order by date")
    public abstract LiveData<List<TaskModel>> getCategoryTasksAfterOneTimestamp(String category, Date oneTime);

    @Query("DELETE FROM tasks WHERE name = :name AND describe = :describe")
    public abstract Completable deleteTask(String name, String describe);

    @Query("UPDATE tasks SET completed = :completed WHERE name = :name")
    public abstract Completable updateTaskStatus(String name, boolean completed);

    @Query("SELECT name, describe, category, date, completed FROM Tasks " +
                            "WHERE date < :hotTimeStamp AND :hotTimeStamp - date <= 86400000 AND completed = 0")
    public abstract LiveData<List<TaskModel>> getHotTasks(Date hotTimeStamp);

    @Query("SELECT category_name as category, " +
                            "sum(case completed when 1 then 1 else 0 end) AS completedCount, " +
                            "sum(case completed when 0 then 1 else 0 end) AS notCompletedCount from categories" +
                            " left join (select * from tasks where date >= :oneTime ) as newtasks on category_name = newtasks.category" +
                            " group by category_name")
    public abstract LiveData<List<CategoryStatusModel>> getAllCategoryStatusAfterOneTime(Date oneTime);

    @Query("SELECT category_name as category, " +
                            "sum(case completed when 1 then 1 else 0 end) AS completedCount, " +
                            "sum(case completed when 0 then 1 else 0 end) AS notCompletedCount from categories" +
                            " left join (select * from tasks where date >= :oneTime ) as newtasks on category_name = newtasks.category" +
                            " where category = :category group by category_name")
    public abstract LiveData<CategoryStatusModel> getCategoryStatusAfterOneTime(String category, Date oneTime);

    @Query("SELECT category_name AS category FROM CATEGORIES GROUP BY category_name")
    public abstract LiveData<List<CategoryModel>> getAllCategories();

}
