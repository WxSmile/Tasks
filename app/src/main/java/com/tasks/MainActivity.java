package com.tasks;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tasks._base.BaseActivity;
import com.tasks._dagger.component.TasksComponent;
import com.tasks.categorydetail.CategoryDetailViewModel;
import com.tasks.databinding.ActivityMainBinding;
import com.tasks.taskadd.AddTaskBottomDialogFragment;
import com.tasks.taskadd.AddTaskViewModel;
import com.tasks.categorydetail.CategoryDetailFragment;
import com.tasks.tasks.TasksFragment;
import com.tasks.tasks.viewmodel.TasksViewModel;
import com.tasks._utils.CircleRevealHepler;
import com.tasks._utils.MainCallback;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainCallback {

    @Inject
    MainViewModel mainViewModel;
    @Inject
    TasksViewModel tasksViewModel;
    @Inject
    CategoryDetailViewModel categoryDetailViewModel;
    @Inject
    AddTaskViewModel addTaskViewModel;

    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        TasksFragment fragment = TasksFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, TasksFragment.class.getSimpleName())
                .addToBackStack(TasksFragment.class.getSimpleName())
                .commit();
    }

    public void addTask(View view) {
        AddTaskBottomDialogFragment addTaskFragment = AddTaskBottomDialogFragment.getInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        addTaskFragment.show(supportFragmentManager, AddTaskBottomDialogFragment.class.getSimpleName());
    }


    @Override
    public void onInject(TasksComponent component) {
        component.inject(this);
    }

    @Override
    public void onTaskAdded(String taskCategory) {
        CategoryDetailFragment categoryDetailFragment = taskDetailFragmentIsLastElementInBackStack();
        if (categoryDetailFragment == null) {
            CircleRevealHepler circleRevealHepler = new CircleRevealHepler();
            circleRevealHepler.setCenterView(dataBinding.fabAddTask);
            circleRevealHepler.setReferView(dataBinding.container);
            showTaskDetailFragment(taskCategory, circleRevealHepler);
        }
    }

    @Override
    public void onCategoryCardClicked(String category, CircleRevealHepler hepler) {
        hepler.setReferView(dataBinding.container);
        showTaskDetailFragment(category, hepler);
    }

    private void showTaskDetailFragment(String taskCategory, CircleRevealHepler hepler) {
        CategoryDetailFragment categoryDetailFragment = CategoryDetailFragment.newInstance(taskCategory, hepler);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, categoryDetailFragment, CategoryDetailFragment.class.getSimpleName())
                .addToBackStack(CategoryDetailFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        CategoryDetailFragment categoryDetailFragment = taskDetailFragmentIsLastElementInBackStack();
        if (categoryDetailFragment != null) {
            categoryDetailFragment.hide();
            return;
        }

        if (hasOnlyOneElementInBackStack()) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    private CategoryDetailFragment taskDetailFragmentIsLastElementInBackStack() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(CategoryDetailFragment.class.getSimpleName());
        return fragment instanceof CategoryDetailFragment ? (CategoryDetailFragment) fragment : null;
    }

    public boolean hasOnlyOneElementInBackStack() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        return supportFragmentManager.getBackStackEntryCount() == 1;
    }
}
