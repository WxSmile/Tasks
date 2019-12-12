package com.tasks;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.tasks.base.BaseActivity;
import com.tasks.dagger.component.TasksComponent;
import com.tasks.databinding.ActivityMainBinding;
import com.tasks.tasks.TasksFragment;
import com.tasks.tasks.viewmodel.TasksViewModel;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject MainViewModel mainViewModel;
    @Inject TasksViewModel tasksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataBinding.setMainModel(mainViewModel);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, TasksFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onInject(TasksComponent component) {
        component.inject(this);
    }
}
