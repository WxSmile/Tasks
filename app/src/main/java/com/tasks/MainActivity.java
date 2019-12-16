package com.tasks;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tasks.base.BaseActivity;
import com.tasks.dagger.component.TasksComponent;
import com.tasks.databinding.ActivityMainBinding;
import com.tasks.taskadd.AddTaskBottomDialogFragment;
import com.tasks.taskadd.AddTaskViewModel;
import com.tasks.taskdetail.TaskDetailFragment;
import com.tasks.taskdetail.TaskDetailViewModel;
import com.tasks.tasks.TasksFragment;
import com.tasks.tasks.viewmodel.TasksViewModel;
import com.tasks.utils.CircleRevealHepler;
import com.tasks.utils.MainCallback;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainCallback {

    @Inject
    MainViewModel mainViewModel;
    @Inject
    TasksViewModel tasksViewModel;
    @Inject
    TaskDetailViewModel taskDetailViewModel;
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
        TaskDetailFragment taskDetailFragment = taskDetailFragmentIsLastElementInBackStack();
        if (taskDetailFragment == null) {
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
        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(taskCategory, hepler);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, taskDetailFragment, TaskDetailFragment.class.getSimpleName())
                .addToBackStack(TaskDetailFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        TaskDetailFragment taskDetailFragment = taskDetailFragmentIsLastElementInBackStack();
        if (taskDetailFragment != null) {
            taskDetailFragment.hide();
            return;
        }

        if (hasOnlyOneElementInBackStack()) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    private TaskDetailFragment taskDetailFragmentIsLastElementInBackStack() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(TaskDetailFragment.class.getSimpleName());
        return fragment instanceof TaskDetailFragment ? (TaskDetailFragment) fragment : null;
    }

    public boolean hasOnlyOneElementInBackStack() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        return supportFragmentManager.getBackStackEntryCount() == 1;
    }
}
