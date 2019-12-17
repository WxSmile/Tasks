package com.tasks._dagger.base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tasks.TasksApplication;
import com.tasks._dagger.component.DaggerTasksComponent;
import com.tasks._dagger.component.TasksComponent;
import com.tasks._dagger.module.ActivityModule;
import com.tasks.data.dagger.DataComponent;

/**
 * Author: murphy
 * Description: dagger activity make ActivityComponent instance to inject
 */
@SuppressLint("Registered")
public abstract class DaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInject(makeTasksComponent());
    }

    private TasksComponent makeTasksComponent() {
        DataComponent dataComponent = TasksApplication.getDataComponent();
        return DaggerTasksComponent.builder()
                .dataComponent(dataComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    public abstract void onInject(TasksComponent component);
}
