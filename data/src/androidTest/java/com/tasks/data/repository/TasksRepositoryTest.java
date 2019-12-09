package com.tasks.data.repository;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.data.utils.LiveDataUtils.getValue;

/**
 * Author: murphy
 * Description:
 */
public class TasksRepositoryTest {

    private TaskModel TEST_TASK_MODEL = TestUtils.createTaskModel("work");
    private TaskModel TEST_TASK_MODEL2 = TestUtils.createTaskModel("work");
    private TaskModel TEST_TASK_MODEL3 = TestUtils.createTaskModel("work");
    private TaskModel TEST_TASK_MODEL4 = TestUtils.createHotTaskModel("Normal");
    private TaskModel TEST_TASK_MODEL5 = TestUtils.createHotTaskModel("work");
    private TaskModel TEST_TASK_MODEL6 = TestUtils.createHotTaskModel("work");

    private DataComponent dataComponent;
    private TasksRepository tasksRepository;

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Application context = ApplicationProvider.getApplicationContext();
        dataComponent = DaggerDataComponent.builder().dataModule(new DataModule(context)).build();
        tasksRepository = dataComponent.makeTestTaskRepository();

        assertThat(tasksRepository).isInstanceOf(TasksRepositoryImpl.class);

        tasksRepository.addCategory("work").test().assertComplete();
        tasksRepository.addCategory("Normal").test().assertComplete();
    }

    @After
    public void tearDown() throws Exception {
        dataComponent.makeTestTasksDatabase().close();
    }

    @Test
    public void addTask() throws Exception{
        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();
    }

    @Test
    public void getCategoryTasks() throws Exception {
        LiveData<Map<String, List<TaskModel>>> workTasksGroupByDate = tasksRepository.getCategoryTasks("work");

        tasksRepository.addTask(TEST_TASK_MODEL2).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL3).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL4).test().assertComplete();

        tasksRepository.addTask(TEST_TASK_MODEL5).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL6).test().assertComplete();

        Map<String, List<TaskModel>> value = getValue(workTasksGroupByDate);
        int size = value.size();
        assertThat(size).isAtLeast(1);

        int count = 0;
        for (Map.Entry<String, List<TaskModel>> entry : value.entrySet()) {
            count += entry.getValue().size();
        }

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void getHotTasks() throws Exception {

        LiveData<List<TaskModel>> hotTasks = tasksRepository.getHotTasks();

        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL2).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL4).test().assertComplete();

        assertThat(getValue(hotTasks)).hasSize(1);
    }

    @Test
    public void getAllCategoryStatus() throws Exception{
        TaskModel work = TestUtils.createHotTaskModel("work");
        TaskModel work1 = TestUtils.createHotTaskModel("work");
        TaskModel work2 = TestUtils.createHotTaskModel("work");
        work.setCompleted(true);
        tasksRepository.addTask(work).test().assertComplete();
        tasksRepository.addTask(work1).test().assertComplete();
        tasksRepository.addTask(work2).test().assertComplete();

        LiveData<List<CategoryStatusModel>> allCategoryStatus = tasksRepository.getAllCategoryStatus();

        List<CategoryStatusModel> value = getValue(allCategoryStatus);

        assertThat(value).hasSize(1);

        CategoryStatusModel categoryStatusModel = value.get(0);
        assertThat(categoryStatusModel.getTotal()).isEqualTo(3);
        assertThat(categoryStatusModel.getCompletedCount()).isEqualTo(1);
        assertThat(categoryStatusModel.getNotCompletedCount()).isEqualTo(2);

    }
}