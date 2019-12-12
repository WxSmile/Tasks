package com.tasks.data.utils;

import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.domain.model.Category;
import com.tasks.domain.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Author: murphy
 * Description:
 */
public class TestUtils {

    public static List<CategoryEntity> makeCategories() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("work"));
        categories.add(new Category("home"));
        categories.add(new Category("sport"));
        for (Category category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategory(category);
            categoryEntities.add(categoryEntity);
        }
        return categoryEntities;
    }

    public static List<TaskEntity> makeTasks() {
        List<TaskEntity> taskEntities = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        Random random = new Random();
        tasks.add(new Task("Eat" + random.nextInt(), "eat a eat", "home", getDeprecatedDate(), true));
        tasks.add(new Task("Eat" + random.nextInt(), "eat a eat", "home", getDeprecatedDate(), true));
        tasks.add(new Task("Eat" + random.nextInt(), "eat a eat", "home", getHotDate(), true));
        tasks.add(new Task("Eat" + random.nextInt(), "eat a eat", "home", getHotDate(), false));
        tasks.add(new Task("Eat" + random.nextInt(), "eat a eat", "home", getHotDate(), false));

        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getDeprecatedDate(), true));
        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getHotDate(), false));
        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getHotDate(), true));
        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getHotDate(), false));
        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getFutureDate(), false));
        tasks.add(new Task("Sport" + random.nextInt(), "sport a sport", "sport", getFutureDate(), false));

        tasks.add(new Task("Work" + random.nextInt(), "work a work", "work", getHotDate(), true));
        tasks.add(new Task("Work" + random.nextInt(), "work a work", "work", getHotDate(), false));
        tasks.add(new Task("Work" + random.nextInt(), "work a work", "work", getFutureDate(), false));
        tasks.add(new Task("Work" + random.nextInt(), "work a work", "work", getFutureDate(), false));

        for (Task task : tasks) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setTask(task);
            taskEntities.add(taskEntity);
        }

        return taskEntities;
    }

    public static List<TaskModel> makeTaskModels() {
        List<TaskModel> taskModels = new ArrayList<>();
        Random random = new Random();
        taskModels.add(new TaskModel("ChiYao" + random.nextInt(), "qu chi yao le", "home", getDeprecatedDate(), true));
        taskModels.add(new TaskModel("ChiYao" + random.nextInt(), "qu chi yao le", "home", getDeprecatedDate(), true));
        taskModels.add(new TaskModel("ChiYao" + random.nextInt(), "qu chi yao le", "home", getHotDate(), true));
        taskModels.add(new TaskModel("ChiYao" + random.nextInt(), "qu chi yao le", "home", getHotDate(), false));
        taskModels.add(new TaskModel("ChiYao" + random.nextInt(), "qu chi yao le", "home", getHotDate(), false));

        taskModels.add(new TaskModel("Sport" + random.nextInt(), "sport running", "sport", getDeprecatedDate(), true));
        taskModels.add(new TaskModel("Sport" + random.nextInt(), "sport running", "sport", getHotDate(), false));
        taskModels.add(new TaskModel("Sport" + random.nextInt(), "sport running", "sport", getHotDate(), true));
        taskModels.add(new TaskModel("Sport" + random.nextInt(), "sport running", "sport", getHotDate(), false));
        taskModels.add(new TaskModel("Sport" + random.nextInt(), "sport running", "sport", getFutureDate(), false));

        taskModels.add(new TaskModel("Work" + random.nextInt(), "work running", "work", getHotDate(), true));
        taskModels.add(new TaskModel("Work" + random.nextInt(), "work running", "work", getHotDate(), false));
        taskModels.add(new TaskModel("Work" + random.nextInt(), "work running", "work", getFutureDate(), false));
        taskModels.add(new TaskModel("Work" + random.nextInt(), "work running", "work", getFutureDate(), false));
        return taskModels;
    }

    public static Date getHotDate() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 24);
        instance.set(Calendar.MINUTE, -30);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }

    public static Date getFutureDate() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, 2);
        return instance.getTime();
    }

    public static Date getDeprecatedDate() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -2);
        return instance.getTime();
    }

}
