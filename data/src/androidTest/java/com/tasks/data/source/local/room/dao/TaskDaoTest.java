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
import com.tasks.data.source.local.room.converter.Converter;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;
import com.tasks.data.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.data.utils.LiveDataUtils.getValue;

/**
 * Author: murphy
 * Description: test task dao
 */
public class TaskDaoTest {

    private TaskEntity TEST_TASK1 = TestUtils.createTask("work");
    private TaskEntity TEST_TASK2 = TestUtils.createTask("home");
    private TaskEntity TEST_TASK3 = TestUtils.createTask("other");
    private TaskEntity TEST_TASK4 = TestUtils.createTask("other");
    private TaskEntity TEST_TASK5 = TestUtils.createHotTask("other");
    private TaskEntity TEST_TASK6 = TestUtils.createHotTask("Transaction");
    private TaskEntity TEST_TASK7 = TestUtils.createHotTask("Transaction");
    private TaskEntity TEST_TASK8 = TestUtils.createNotHotTask("Transaction");

    private CategoryEntity TEST_CATEGORY1 = TestUtils.createCategory("work");
    private CategoryEntity TEST_CATEGORY2 = TestUtils.createCategory("home");
    private CategoryEntity TEST_CATEGORY3 = TestUtils.createCategory("other");
    private CategoryEntity TEST_CATEGORY4 = TestUtils.createCategory("Transaction");
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

        taskDao.insertCategory(TEST_CATEGORY1).test().assertComplete();
        taskDao.insertCategory(TEST_CATEGORY2).test().assertComplete();
        taskDao.insertCategory(TEST_CATEGORY3).test().assertComplete();
        taskDao.insertCategory(TEST_CATEGORY4).test().assertComplete();
    }

    private void initTask() {

        taskDao.insertTask(TEST_TASK1).test().assertComplete();
        taskDao.insertTask(TEST_TASK2).test().assertComplete();
        taskDao.insertTask(TEST_TASK3).test().assertComplete();
        taskDao.insertTask(TEST_TASK4).test().assertComplete();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insertTask() throws Exception {
        initTask();

        LiveData<List<TaskModel>> allTasks = taskDao.getAllTasks();
        int sizeBefore = getValue(allTasks).size();

        TaskEntity work = TestUtils.createTask("work");
        taskDao.insertTask(work).test().assertComplete();

        List<TaskModel> value = getValue(allTasks);

        assertThat(value.size()).isEqualTo(sizeBefore + 1);

        TaskModel taskModel = value.get(value.size() - 1);

        assertThat(taskModel.getName()).isEqualTo(work.getTask().name);
    }

    @Test
    public void getCategoryTasks() throws Exception {
        initTask();
        taskDao.insertTask(TEST_TASK5).test().assertComplete();

        LiveData<List<TaskModel>> otherTasks = taskDao.getCategoryTasks("other");
        List<TaskModel> value = getValue(otherTasks);

        assertThat(value).isNotNull();
        assertThat(value.size()).isEqualTo(3);

        for (TaskModel taskModel : value) {
            assertThat(taskModel.getCategory()).isEqualTo("other");
        }

        assertThat(value.get(1).getDate()).isLessThan(value.get(2).getDate());

        LiveData<Map<String, List<TaskModel>>> map = Transformations.map(otherTasks, input -> {
            Map<String, List<TaskModel>> tasksGroupByDate = new HashMap<>();
            DateFormat dateFormat = DateFormat.getDateInstance();

            Calendar instance = Calendar.getInstance();
            long currentTimestamp = instance.getTimeInMillis();

            for (TaskModel taskModel : input) {
                Date date = taskModel.getDate();
                long timestamp = date.getTime();
                if (timestamp < currentTimestamp) continue;

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

        assertThat(getValue(map)).hasSize(1);
    }

    @Test
    public void getCategoryTaskAfterCurrentTimestamp() throws Exception {
        initTask();
        taskDao.insertTask(TEST_TASK5).test().assertComplete();

        LiveData<List<TaskModel>> otherTasks = taskDao.getCategoryTasksAfterOneTimestamp("other", new Date());
        List<TaskModel> value = getValue(otherTasks);

        assertThat(value).isNotNull();

        assertThat(value).hasSize(1);
    }

    @Test
    public void deleteTask() throws Exception {
        initTask();

        LiveData<List<TaskModel>> liveData = taskDao.getAllTasks();
        List<TaskModel> allTasks = getValue(liveData);

        assertThat(allTasks).isNotNull();

        int sizeBefore = allTasks.size();
        assertThat(sizeBefore).isAtLeast(1);

        TaskModel taskModel = allTasks.get(0);

        Completable completable = taskDao.deleteTask(taskModel.getName(), taskModel.getDescribe());
        completable.test().assertComplete();

        assertThat(getValue(liveData).size()).isEqualTo(sizeBefore - 1);
    }

    @Test
    public void updateTaskCompleted() throws Exception {
        initTask();

        LiveData<List<TaskModel>> allTasks = taskDao.getAllTasks();

        List<TaskModel> value = getValue(allTasks);
        assertThat(value.size()).isAtLeast(1);

        TaskModel taskModel = value.get(0);

        assertThat(taskModel.isCompleted()).isFalse();

        taskDao.updateTaskStatus(taskModel.getName(), true).test().assertComplete();

        assertThat(getValue(allTasks).get(0).isCompleted()).isTrue();
    }

    @Test
    public void getHotTasks() throws Exception {
        initTask();

        taskDao.insertTask(TEST_TASK6).test().assertComplete();
        taskDao.insertTask(TEST_TASK7).test().assertComplete();
        taskDao.insertTask(TEST_TASK8).test().assertComplete();

        LiveData<List<TaskModel>> hotTasks = taskDao.getHotTasks(Calendar.getInstance().getTime());
        List<TaskModel> value = getValue(hotTasks);
        long current = Calendar.getInstance().getTimeInMillis();

        for (TaskModel taskModel : value) {
            Date date = taskModel.getDate();
            assertThat(date).isNotNull();

            long timestamp = Converter.dateToTimestamp(date);
            assertThat(timestamp - current).isAtLeast(0L);
            assertThat(timestamp - current).isAtMost(86400000L);
        }

        assertThat(value).hasSize(2);
    }

    @Test
    public void getAllCategories() throws Exception {
        LiveData<List<CategoryModel>> allCategories = taskDao.getAllCategories();
        assertThat(getValue(allCategories)).hasSize(4);
    }

    @Test
    public void getAllCategoryStatus() throws Exception {
        LiveData<List<CategoryStatusModel>> allCategoryStatus = taskDao.getAllCategoryStatusAfterOneTime(new Date());

        List<TaskEntity> unCompletedTasks = new ArrayList<>();
        List<TaskEntity> completedTasks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            unCompletedTasks.add(TestUtils.createStatusTask("work", false));
            unCompletedTasks.add(TestUtils.createStatusTask("home", false));
            unCompletedTasks.add(TestUtils.createStatusTask("other", false));
        }
        for (int i = 0; i < 3; i++) {
            completedTasks.add(TestUtils.createStatusTask("work", true));
            completedTasks.add(TestUtils.createStatusTask("home", true));
            completedTasks.add(TestUtils.createStatusTask("other", true));
        }

        for (TaskEntity unCompletedTask : unCompletedTasks) {
            taskDao.insertTask(unCompletedTask).test().assertComplete();
        }

        for (TaskEntity completedTask : completedTasks) {
            taskDao.insertTask(completedTask).test().assertComplete();
        }


        List<CategoryStatusModel> value = getValue(allCategoryStatus);

        assertThat(value).hasSize(3);

        for (CategoryStatusModel categoryStatusModel : value) {
            if ("work".equals(categoryStatusModel.getCategory())) {
                assertThat(categoryStatusModel.getTotal()).isEqualTo(7);
                assertThat(categoryStatusModel.getCompletedCount()).isEqualTo(3);
                assertThat(categoryStatusModel.getNotCompletedCount()).isEqualTo(4);
                break;
            }
        }
    }

    @Test
    public void getCategoryStatus() throws Exception{
        LiveData<List<CategoryStatusModel>> workTasks = taskDao.getCategoryStatus("work");
        List<TaskEntity> unCompletedTasks = new ArrayList<>();
        List<TaskEntity> completedTasks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            unCompletedTasks.add(TestUtils.createStatusTask("work", false));
        }
        for (int i = 0; i < 3; i++) {
            completedTasks.add(TestUtils.createStatusTask("work", true));
        }

        for (TaskEntity unCompletedTask : unCompletedTasks) {
            taskDao.insertTask(unCompletedTask).test().assertComplete();
        }

        for (TaskEntity completedTask : completedTasks) {
            taskDao.insertTask(completedTask).test().assertComplete();
        }

        List<CategoryStatusModel> value = getValue(workTasks);
        assertThat(value).hasSize(1);
        assertThat(value.get(0).getTotal()).isEqualTo(7);
        assertThat(value.get(0).getCompletedCount()).isEqualTo(3);
        assertThat(value.get(0).getNotCompletedCount()).isEqualTo(4);
    }
}