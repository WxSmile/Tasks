package com.tasks.tasks.viewmodel;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.categorydetail.CategoryDetailViewModel;
import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;
import com.tasks.data.util.DateUtils;
import com.tasks.tasks.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.tasks.LiveDataUtils.getValue;

/**
 * Author: murphy
 * Description: Unit tests for the implementation of TasksViewModel
 */
public class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    private List<TaskModel> taskModels;

    private TasksViewModel tasksViewModel;
    private CategoryDetailViewModel categoryDetailViewModel;
    private TasksRepository testTaskRepository;

    @Before
    public void setUp() throws Exception {
        Application application = ApplicationProvider.getApplicationContext();
        DataComponent dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(application))
                .build();
        testTaskRepository = dataComponent.makeTestTaskRepository();
        tasksViewModel = new TasksViewModel(testTaskRepository);
        categoryDetailViewModel = new CategoryDetailViewModel(testTaskRepository);

        assertThat(tasksViewModel).isNotNull();

        testTaskRepository.addCategory("home").test().assertComplete();
        testTaskRepository.addCategory("sport").test().assertComplete();
        testTaskRepository.addCategory("work").test().assertComplete();

        taskModels = TestUtil.makeTaskModels();
    }

    private void insertTasks() {
        for (TaskModel taskModel : taskModels) {
            testTaskRepository.addTask(taskModel).test().assertComplete();
        }
    }

    @Test
    public void tasksViewModel_getAllCategoriesTaskStatus_getHotTasks_addTask() throws Exception {

        assertThat(tasksViewModel.allCategoryStatusEvent).isNull();
        assertThat(tasksViewModel.hotTasksEvent).isNull();

        tasksViewModel.fetchAllCategoryStatus();
        tasksViewModel.fetchHotTasks();

        List<CategoryStatusModel> value = getValue(tasksViewModel.allCategoryStatusEvent);
        assertThat(value).hasSize(3);
        assertThat(value.get(0).getTotal()).isEqualTo(0);
        assertThat(getValue(tasksViewModel.hotTasksEvent)).hasSize(0);

        insertTasks();

        assertThat(getValue(tasksViewModel.allCategoryStatusEvent)).hasSize(3);
        assertThat(getValue(tasksViewModel.hotTasksEvent)).hasSize(5);
    }

    @Test
    public void tasksDetailViewModel_getCategoryTasksAndStatus_addTask_markTask_deleteTask() throws Exception {
        assertThat(categoryDetailViewModel.categoryTasksEvent).isNull();
        assertThat(categoryDetailViewModel.categoryStatusEvent).isNull();

        categoryDetailViewModel.setCategory("work");

        categoryDetailViewModel.fetchCategoryStatus();
        categoryDetailViewModel.fetchCategoryTasks();

        CategoryStatusModel categoryStatus = getValue(categoryDetailViewModel.categoryStatusEvent);
        assertThat(categoryStatus).isNull();
        assertThat(getValue(categoryDetailViewModel.categoryTasksEvent)).hasSize(0);

        insertTasks();

        categoryStatus = getValue(categoryDetailViewModel.categoryStatusEvent);

        assertThat(categoryStatus.getCategory()).isEqualTo(categoryDetailViewModel.getCategory());
        assertThat(categoryStatus.getTotal()).isEqualTo(4);
        assertThat(categoryStatus.getNotCompletedCount()).isEqualTo(3);
        assertThat(categoryStatus.getCompletedCount()).isEqualTo(1);

        Map<String, List<TaskModel>> categoryTasks = getValue(categoryDetailViewModel.categoryTasksEvent);
        assertThat(categoryTasks).hasSize(2);

        String hotTaskGroup = DateUtils.getDateFormate(TestUtil.getHotDate());
        assertThat(categoryTasks).containsKey(hotTaskGroup);

        List<TaskModel> taskModels = categoryTasks.get(hotTaskGroup);

        assertThat(taskModels).isNotNull();
        assertThat(taskModels).hasSize(2);

        TaskModel taskModel = taskModels.get(0);

        categoryDetailViewModel.markTaskStatus(taskModel.getName(), !taskModel.isCompleted());

        categoryDetailViewModel.updateTaskStatusEvent.test().assertComplete();

        categoryTasks = getValue(categoryDetailViewModel.categoryTasksEvent);
        taskModels = categoryTasks.get(hotTaskGroup);

        assertThat(taskModels).isNotNull();
        TaskModel markedTaskModel = taskModels.get(0);

        assertThat(markedTaskModel.getName()).isEqualTo(taskModel.getName());
        assertThat(markedTaskModel.getDate()).isEqualTo(taskModel.getDate());
        assertThat(markedTaskModel.isCompleted()).isEqualTo(!taskModel.isCompleted());

        categoryDetailViewModel.deleteTask(markedTaskModel);

        categoryDetailViewModel.deleteTaskEvent.test().assertComplete();

        categoryTasks = getValue(categoryDetailViewModel.categoryTasksEvent);
        String markTaskDateGroup = DateUtils.getDateFormate(markedTaskModel.getDate());
        taskModels = categoryTasks.get(markTaskDateGroup);

        assertThat(taskModels).isNotNull();
        assertThat(taskModels).hasSize(1);
        assertThat(taskModels.get(0).getName()).isNotEqualTo(markedTaskModel.getName());

    }

    @After
    public void tearDown() throws Exception {

    }
}