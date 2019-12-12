package com.tasks.data.source.local.room.dao;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;
import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.TasksDatabase;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.data.util.DateUtils;
import com.tasks.data.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.data.util.DateUtils.getTodayZeroClockTime;
import static com.tasks.data.utils.LiveDataUtils.getValue;

/**
 * Author: murphy
 * Description: test task dao
 */
public class TaskDaoTest {

    private TasksDatabase database;
    private TaskDao taskDao;

    private DataComponent dataComponent;

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();


    @Before
    public void createDb() throws Exception {

        Application context = ApplicationProvider.getApplicationContext();
        dataComponent = DaggerDataComponent.builder().dataModule(new DataModule(context)).build();

        database = dataComponent.makeTestTasksDatabase();
        taskDao = database.getTaskDao();

        List<CategoryEntity> categoryEntities = TestUtils.makeCategories();
        taskDao.insertCategories(categoryEntities).test().assertComplete();

        insertTask();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    public void insertTask() throws Exception {
        List<TaskEntity> taskEntities = TestUtils.makeTasks();
        for (TaskEntity taskEntity : taskEntities) {
            taskDao.insertTask(taskEntity).test().assertComplete();
        }
        LiveData<List<TaskModel>> allTasksEvent = taskDao.getAllTasks();
        List<TaskModel> allTasks = getValue(allTasksEvent);
        assertThat(allTasks).hasSize(taskEntities.size());
    }

    @Test
    public void getCategoryTasks() throws Exception {

        LiveData<List<TaskModel>> workTasksEvent = taskDao.getCategoryTasksAfterOneTimestamp("work", getTodayZeroClockTime());

        List<TaskModel> workTasks = getValue(workTasksEvent);

        assertThat(workTasks).hasSize(4);

        LiveData<Map<String, List<TaskModel>>> map = Transformations.map(workTasksEvent, input -> {
            Map<String, List<TaskModel>> tasksGroupByDate = new HashMap<>();
            DateFormat dateFormat = DateFormat.getDateInstance();

            for (TaskModel taskModel : input) {
                Date date = taskModel.getDate();
                String formatDate = dateFormat.format(date);
                List<TaskModel> taskModelsGroupByDate = tasksGroupByDate.get(formatDate);
                if (taskModelsGroupByDate == null) {
                    taskModelsGroupByDate = new ArrayList<>();
                }
                taskModelsGroupByDate.add(taskModel);
                tasksGroupByDate.put(formatDate, taskModelsGroupByDate);
            }
            return tasksGroupByDate;
        });

        assertThat(getValue(map)).hasSize(2);
    }

    @Test
    public void deleteTask() throws Exception {

        LiveData<List<TaskModel>> allTasksEvent = taskDao.getAllTasks();
        List<TaskModel> allTasks = getValue(allTasksEvent);

        assertThat(allTasks).isNotNull();

        int sizeBefore = allTasks.size();
        assertThat(sizeBefore).isAtLeast(1);

        TaskModel taskModel = allTasks.get(0);

        Completable completable = taskDao.deleteTask(taskModel.getName(), taskModel.getDescribe());
        completable.test().assertComplete();

        assertThat(getValue(allTasksEvent).size()).isEqualTo(sizeBefore - 1);
    }

    @Test
    public void updateTaskCompleted() throws Exception {

        LiveData<List<TaskModel>> allTasksEvent = taskDao.getAllTasks();

        List<TaskModel> allTasks = getValue(allTasksEvent);
        assertThat(allTasks.size()).isAtLeast(1);

        TaskModel taskModel = allTasks.get(0);

        boolean completed = taskModel.isCompleted();

        taskDao.updateTaskStatus(taskModel.getName(), !completed).test().assertComplete();

        assertThat(getValue(allTasksEvent).get(0).isCompleted()).isEqualTo(!completed);
    }

    @Test
    public void getHotTasks() throws Exception {

        Date nextTodayZeroClockTime = DateUtils.getNextTodayZeroClockTime();
        LiveData<List<TaskModel>> hotTasksEvent = taskDao.getHotTasks(nextTodayZeroClockTime);
        List<TaskModel> hotTasks = getValue(hotTasksEvent);

        assertThat(hotTasks).hasSize(5);

        for (TaskModel taskModel : hotTasks) {
            Date date = taskModel.getDate();

            assertThat(nextTodayZeroClockTime.getTime() - date.getTime()).isAtLeast(0L);
            assertThat(nextTodayZeroClockTime.getTime() - date.getTime()).isAtMost(86400000L);

            assertThat(taskModel.isCompleted()).isFalse();
        }

    }

    @Test
    public void getAllCategories() throws Exception {
        LiveData<List<CategoryModel>> allCategoriesEvent = taskDao.getAllCategories();
        assertThat(getValue(allCategoriesEvent)).hasSize(3);
    }

    @Test
    public void getAllCategoryStatus() throws Exception {
        Date todayZeroClockTime = getTodayZeroClockTime();
        LiveData<List<CategoryStatusModel>> allCategoryStatusEvent = taskDao.getAllCategoryStatusAfterOneTime(todayZeroClockTime);
        List<CategoryStatusModel> allCategoryStatus = getValue(allCategoryStatusEvent);

        assertThat(allCategoryStatus).hasSize(3);

        //assert when tasks table is empty
//        CategoryStatusModel categoryStatusModel1 = allCategoryStatus.get(0);
//        assertThat(categoryStatusModel1.getCompletedCount() + categoryStatusModel1.getNotCompletedCount()).isEqualTo(0);

        for (CategoryStatusModel categoryStatusModel : allCategoryStatus) {
            if ("work".equals(categoryStatusModel.getCategory())) {
                assertThat(categoryStatusModel.getCompletedCount()).isEqualTo(1);
                assertThat(categoryStatusModel.getNotCompletedCount()).isEqualTo(3);
                break;
            }
        }
    }

    @Test
    public void getCategoryStatus() throws Exception{
        Date todayZeroClockTime = getTodayZeroClockTime();
        LiveData<CategoryStatusModel> workTasksEvent = taskDao.getCategoryStatusAfterOneTime("work", todayZeroClockTime);
        CategoryStatusModel workTaskStatus = getValue(workTasksEvent);
        assertThat(workTaskStatus.getCompletedCount()).isEqualTo(1);
        assertThat(workTaskStatus.getNotCompletedCount()).isEqualTo(3);
    }
}