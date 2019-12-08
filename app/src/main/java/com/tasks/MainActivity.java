package com.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tasks.base.BaseActivity;
import com.tasks.dagger.activity.ActivityComponent;
import com.tasks.data.model.TaskModel;
import com.tasks.taskdetail.TaskDetailFragment;
import com.tasks.tasks.TasksFragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TasksFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}
