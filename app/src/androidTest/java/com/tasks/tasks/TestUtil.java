package com.tasks.tasks;

import com.tasks.data.model.TaskModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Author: murphy
 * Description:
 */
public class TestUtil {

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
