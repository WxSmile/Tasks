package com.tasks.tasks.viewmodel;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;
import com.tasks.data.repository.TasksRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Author: murphy
 * Description: Unit tests for the implementation of TasksViewModel
 */
public class TasksViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    private TasksViewModel tasksViewModel;

    @Before
    public void setUp() throws Exception {
        Application application = ApplicationProvider.getApplicationContext();
        DataComponent dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(application))
                .build();
        TasksRepository testTaskRepository = dataComponent.makeTestTaskRepository();
        tasksViewModel = new TasksViewModel(testTaskRepository);

        assertThat(tasksViewModel).isNotNull();

        TasksRepository repository = tasksViewModel.getRepository();

        assertThat(repository).isEqualTo(testTaskRepository);
    }

    @Test
    public void getAllCategoriesTaskStatus_getHotTasks_addTask() {
    }

    @After
    public void tearDown() throws Exception {

    }
}