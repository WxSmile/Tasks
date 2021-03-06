package com.tasks.data.repository;

import androidx.lifecycle.LiveData;

import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.domain.model.Category;
import com.tasks.domain.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: task repository
 */
public class TasksRepositoryImpl implements TasksRepository {

    private TasksDataSource localDataSource;
    private TasksDataSource remoteDataSource;

    public TasksRepositoryImpl(TasksDataSource localDataSource, TasksDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Completable addCategory(String category) {
        return localDataSource.addCategory(adapt2CategoryEntity(category));
    }

    @Override
    public Completable addCategory(List<String> categories) {
        return localDataSource.addCategory(adapt2CategoryEntity(categories));
    }

    @Override
    public Completable addTask(TaskModel task) {
        return localDataSource.addTask(adapt2TaskEntity(task));
    }

    @Override
    public Completable deleteTask(String name, String describe) {
        return localDataSource.deleteTask(name, describe);
    }

    @Override
    public LiveData<Map<String, List<TaskModel>>> getCategoryTasks(String category) {
        return localDataSource.getCategoryTasks(category);
    }

    @Override
    public LiveData<List<TaskModel>> getHotTasks() {
        return localDataSource.getHotTasks();
    }

    @Override
    public Completable updateTask(String taskName, boolean completed) {
        return localDataSource.updateTask(taskName, completed);
    }

    @Override
    public LiveData<List<CategoryStatusModel>> getAllCategoryStatus() {
        return localDataSource.getAllCategoryStatus();
    }

    @Override
    public LiveData<CategoryStatusModel> getCategoryStatus(String category) {
        return localDataSource.getCategoryStatus(category);
    }

    @Override
    public LiveData<List<CategoryModel>> getAllCategories() {
        return localDataSource.getAllCategories();
    }

    private TaskEntity adapt2TaskEntity(TaskModel taskModel) {
        TaskEntity taskEntity = new TaskEntity();
        Task task = new Task(taskModel.getName(), taskModel.getDescribe(),
                taskModel.getCategory(), taskModel.getDate(), taskModel.isCompleted());
        taskEntity.setTask(task);
        return taskEntity;
    }

    private CategoryEntity adapt2CategoryEntity(String category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(new Category(category));
        return categoryEntity;
    }

    private List<CategoryEntity> adapt2CategoryEntity(List<String> categories) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (String category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategory(new Category(category));
            categoryEntities.add(categoryEntity);
        }

        return categoryEntities;
    }
}
