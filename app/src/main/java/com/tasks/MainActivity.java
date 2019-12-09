package com.tasks;

import android.os.Bundle;

import com.tasks.base.BaseActivity;
import com.tasks.dagger.component.TasksComponent;
import com.tasks.tasks.TasksFragment;

public class MainActivity extends BaseActivity {

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
    public void onInject(TasksComponent component) {
    }
}
