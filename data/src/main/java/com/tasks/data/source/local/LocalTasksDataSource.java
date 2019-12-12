package com.tasks.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.room.dao.TaskDao;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.data.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

import static com.tasks.data.util.DateUtils.getDateFormate;

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
    public Completable addTask(TaskEntity task) {
        return taskDao.insertTask(task);
    }

    @Override
    public Completable updateTask(String taskName, boolean completed) {
        return taskDao.updateTaskStatus(taskName, completed);
    }

    @Override
    public Completable deleteTask(String taskName, String describe) {
        return taskDao.deleteTask(taskName, describe);
    }

    @Override
    public LiveData<List<TaskModel>> getHotTasks() {
        return taskDao.getHotTasks(DateUtils.getNextTodayZeroClockTime());
    }

    @Override
    public LiveData<Map<String, List<TaskModel>>> getCategoryTasks(String category) {
        Date todayZeroClockTime = DateUtils.getTodayZeroClockTime();
        LiveData<List<TaskModel>> categoryTasks = taskDao.getCategoryTasksAfterOneTimestamp(category, todayZeroClockTime);
        return Transformations.map(categoryTasks, this::getCategoryTasks);
    }

    private Map<String, List<TaskModel>> getCategoryTasks(List<TaskModel> taskModels) {
        Map<String, List<TaskModel>> tasksGroupByDate = new HashMap<>();

        for (TaskModel taskModel : taskModels) {
            Date date = taskModel.getDate();
            String formatDate = getDateFormate(date);
            List<TaskModel> taskModelsGroupByDate = tasksGroupByDate.get(formatDate);
            if (taskModelsGroupByDate == null) {
                taskModelsGroupByDate = new ArrayList<>();
            }
            taskModelsGroupByDate.add(taskModel);
            tasksGroupByDate.put(formatDate, taskModelsGroupByDate);
        }
        return tasksGroupByDate;
    }


    @Override
    public Completable addCategory(CategoryEntity categoryEntity) {
        return taskDao.insertCategory(categoryEntity);
    }

    @Override
    public Completable addCategory(List<CategoryEntity> categoryEntities) {
        return taskDao.insertCategories(categoryEntities);
    }

    @Override
    public LiveData<List<CategoryStatusModel>> getAllCategoryStatus() {
        return taskDao.getAllCategoryStatusAfterOneTime(DateUtils.getTodayZeroClockTime());
    }

    @Override
    public LiveData<CategoryStatusModel> getCategoryStatus(String category) {
        return taskDao.getCategoryStatusAfterOneTime(category, DateUtils.getTodayZeroClockTime());
    }
}
