package com.tasks.data.source.local.room.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private TaskEntity TEST_TASK5 = TestUtils.createTask("Transaction");
    private TaskEntity TEST_TASK6 = TestUtils.createHotTask("Transaction");
    private TaskEntity TEST_TASK7 = TestUtils.createHotTask("Transaction");
    private TaskEntity TEST_TASK8 = TestUtils.createNotHotTask("Transaction");

    private CategoryEntity TEST_CATEGORY1 = TestUtils.createCategory("work");
    private CategoryEntity TEST_CATEGORY2 = TestUtils.createCategory("home");
    private CategoryEntity TEST_CATEGORY3 = TestUtils.createCategory("other");
    private CategoryEntity TEST_CATEGORY4 = TestUtils.createCategory("Transaction");
    private TasksDatabase database;
    private TaskDao taskDao;

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();


    @Before
    public void createDb() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TasksDatabase.class).build();
        taskDao = database.getTaskDao();

        taskDao.insertCategory(TEST_CATEGORY1);
        taskDao.insertCategory(TEST_CATEGORY2);
        taskDao.insertCategory(TEST_CATEGORY3);

        taskDao.insertTask(TEST_TASK1);
        taskDao.insertTask(TEST_TASK2);
        taskDao.insertTask(TEST_TASK3);
        taskDao.insertTask(TEST_TASK4);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void insertTaskTransaction() throws Exception {

        LiveData<List<TaskModel>> allTasks = taskDao.getAllTasks();
        LiveData<List<TaskModel>> workTasks = taskDao.getCategoryTasks("Transaction");
        assertThat(getValue(allTasks)).hasSize(4);
        assertThat(getValue(workTasks)).hasSize(0);

        taskDao.insertTaskTransaction(TEST_CATEGORY4, TEST_TASK5);

        assertThat(getValue(allTasks)).hasSize(5);
        assertThat(getValue(workTasks)).hasSize(1);

    }

    @Test
    public void insertTask() throws Exception {
        LiveData<List<TaskModel>> allTasks = taskDao.getAllTasks();
        int sizeBefore = getValue(allTasks).size();

        TaskEntity work = TestUtils.createTask("work");
        taskDao.insertTask(work);

        List<TaskModel> value = getValue(allTasks);

        assertThat(value.size()).isEqualTo(sizeBefore + 1);

        TaskModel taskModel = value.get(value.size() - 1);

        assertThat(taskModel.getName()).isEqualTo(work.getTask().name);
    }

    @Test
    public void getCategoryTasks() throws Exception {
        LiveData<List<TaskModel>> otherTasks = taskDao.getCategoryTasks("other");
        List<TaskModel> value = getValue(otherTasks);

        assertThat(value).isNotNull();
        assertThat(value.size()).isEqualTo(2);

        for (TaskModel taskModel : value) {
            assertThat(taskModel.getCategory()).isEqualTo("other");
        }
    }

    @Test
    public void deleteTask() throws Exception{
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
        taskDao.insertTaskTransaction(TEST_CATEGORY4, TEST_TASK6);
        taskDao.insertTaskTransaction(TEST_CATEGORY4, TEST_TASK7);
        taskDao.insertTaskTransaction(TEST_CATEGORY4, TEST_TASK8);

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
}