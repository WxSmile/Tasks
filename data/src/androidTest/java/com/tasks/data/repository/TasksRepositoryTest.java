package com.tasks.data.repository;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;
import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.util.DateUtils;
import com.tasks.data.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.data.utils.LiveDataUtils.getValue;

/**
 * Author: murphy
 * Description:
 */
public class TasksRepositoryTest {

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

        tasksRepository.addCategory("home").test().assertComplete();
        tasksRepository.addCategory("sport").test().assertComplete();
        tasksRepository.addCategory("work").test().assertComplete();

        insertTask();

    }

    @After
    public void tearDown() throws Exception {
        dataComponent.makeTestTasksDatabase().close();
    }

    public void insertTask() throws Exception {

        List<TaskModel> taskModels = TestUtils.makeTaskModels();
        for (TaskModel taskModel : taskModels) {
            tasksRepository.addTask(taskModel).test().assertComplete();
        }
    }

    @Test
    public void getCategoryTasks() throws Exception {
        LiveData<Map<String, List<TaskModel>>> workTasksEvent = tasksRepository.getCategoryTasks("work");
        Map<String, List<TaskModel>> workTasks = getValue(workTasksEvent);

        String hotDateGroup = DateUtils.getDateFormate(TestUtils.getHotDate());
        String futureDateGroup = DateUtils.getDateFormate(TestUtils.getFutureDate());

        assertThat(workTasks).containsKey(hotDateGroup);
        assertThat(workTasks).containsKey(futureDateGroup);

        assertThat(workTasks.get(hotDateGroup)).hasSize(2);
        assertThat(workTasks.get(futureDateGroup)).hasSize(2);

    }

    @Test
    public void getHotTasks() throws Exception {
        LiveData<List<TaskModel>> hotTasksEvent = tasksRepository.getHotTasks();
        List<TaskModel> hotTasks = getValue(hotTasksEvent);
        assertThat(hotTasks).hasSize(5);

        assertThat(hotTasks.get(0).isCompleted()).isFalse();
    }

    @Test
    public void getAllCategoryStatus() throws Exception{
        LiveData<List<CategoryStatusModel>> allCategoryStatusEvent = tasksRepository.getAllCategoryStatus();
        List<CategoryStatusModel> allCategoryStatus = getValue(allCategoryStatusEvent);

        assertThat(allCategoryStatus).hasSize(3);

        for (CategoryStatusModel categoryStatusModel : allCategoryStatus) {
            if ("work".equals(categoryStatusModel.getCategory())) {
                assertThat(categoryStatusModel.getCompletedCount()).isEqualTo(1);
                assertThat(categoryStatusModel.getNotCompletedCount()).isEqualTo(3);
                break;
            }
        }
    }

    @Test
    public void getCategories() throws Exception{
        LiveData<List<CategoryModel>> allCategories = tasksRepository.getAllCategories();
        assertThat(getValue(allCategories)).hasSize(3);
    }
}