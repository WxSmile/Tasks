package com.tasks.data.repository;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.data.dagger.test.DaggerTestComponent;
import com.tasks.data.dagger.test.TestComponent;
import com.tasks.data.dagger.test.TestDataModule;
import com.tasks.data.model.TaskModel;
import com.tasks.data.source.local.room.TasksDatabase;
import com.tasks.data.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

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

    private TestComponent testComponent;
    private TasksRepository tasksRepository;

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        TasksDatabase tasksDatabase = Room.inMemoryDatabaseBuilder(context, TasksDatabase.class).build();
        TestDataModule testDataModule = new TestDataModule(tasksDatabase);
        testComponent = DaggerTestComponent.builder().testDataModule(testDataModule).build();
        tasksRepository = testComponent.makeTaskRepository();

        assertThat(tasksRepository).isInstanceOf(TasksRepositoryImpl.class);

        tasksRepository.addCategory("work").test().assertComplete();
        tasksRepository.addCategory("Normal").test().assertComplete();
    }

    @After
    public void tearDown() throws Exception {
        testComponent.makeTasksDatabase().close();
    }

    @Test
    public void addTask() throws Exception{
        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();

        LiveData<List<TaskModel>> getWorkTasks = tasksRepository.getCategoryTasks("work");
        List<TaskModel> value = getValue(getWorkTasks);
        assertThat(value).hasSize(1);

        TaskModel taskModel = value.get(0);

        assertThat(taskModel.getName()).isEqualTo(TEST_TASK_MODEL.getName());
        assertThat(taskModel.getCategory()).isEqualTo(TEST_TASK_MODEL.getCategory());
    }

    @Test
    public void getCategoryTasks() throws Exception {
        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL2).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL3).test().assertComplete();

        LiveData<List<TaskModel>> getWorkTasks = tasksRepository.getCategoryTasks("work");
        List<TaskModel> value = getValue(getWorkTasks);
        assertThat(value).hasSize(3);

    }

    @Test
    public void getHotTasks() throws Exception {
        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL2).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL3).test().assertComplete();
        tasksRepository.addTask(TEST_TASK_MODEL4).test().assertComplete();

        LiveData<List<TaskModel>> hotTasks = tasksRepository.getHotTasks();
        assertThat(getValue(hotTasks)).hasSize(1);
    }

    @Test
    public void updateTask() throws Exception {
        tasksRepository.addTask(TEST_TASK_MODEL).test().assertComplete();

        LiveData<List<TaskModel>> categoryTasks = tasksRepository.getCategoryTasks(TEST_TASK_MODEL.getCategory());

        assertThat(getValue(categoryTasks)).hasSize(1);

        assertThat(getValue(categoryTasks).get(0).isCompleted()).isFalse();

        tasksRepository.updateTask(TEST_TASK_MODEL.getName(), true).test().assertComplete();

        assertThat(getValue(categoryTasks).get(0).isCompleted()).isTrue();

    }
}