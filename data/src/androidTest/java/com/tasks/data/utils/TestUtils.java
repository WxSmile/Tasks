package com.tasks.data.utils;

import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.domain.model.Category;
import com.tasks.domain.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Author: murphy
 * Description:
 */
public class TestUtils {

    public static TaskEntity createTask(String category) {
        TaskEntity taskEntity = new TaskEntity();
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, 1999);
        Date date = instance.getTime();
        int random = new Random().nextInt();
        taskEntity.setTask(new Task("normal_task" + random, "normal", category, date, false));
        return taskEntity;
    }

    public static TaskEntity createStatusTask(String category, boolean completed) {
        TaskEntity taskEntity = new TaskEntity();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 1);
        Date date = instance.getTime();
        int random = new Random().nextInt();
        taskEntity.setTask(new Task("normal_task" + random, "normal", category, date, completed));
        return taskEntity;
    }

    public static TaskEntity createHotTask(String category) {
        TaskEntity taskEntity = new TaskEntity();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, new Random().nextInt(10) + 1);
        Date date = instance.getTime();
        int random = new Random().nextInt();
        taskEntity.setTask(new Task("normal_task" + random, "normal", category, date, false));
        return taskEntity;
    }

    public static TaskEntity createNotHotTask(String category) {
        TaskEntity taskEntity = new TaskEntity();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, new Random().nextInt(10) + 24);
        Date date = instance.getTime();
        int random = new Random().nextInt();
        taskEntity.setTask(new Task("normal_task" + random, "normal", category, date, false));
        return taskEntity;
    }

    public static CategoryEntity createCategory(String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category(name);
        categoryEntity.setCategory(category);
        return categoryEntity;
    }

    public static TaskModel createTaskModel(String category) {
        return new TaskModel("hecha" + new Random().nextInt(), "hot ca", category, new Date(), false);
    }

    public static TaskModel createHotTaskModel(String category) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, new Random().nextInt(10));
        Date date = instance.getTime();
        return new TaskModel("hecha not" + new Random().nextInt(), "hot ca", category, date, false);
    }

}
