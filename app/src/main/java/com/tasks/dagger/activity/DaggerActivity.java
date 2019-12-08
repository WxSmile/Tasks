package com.tasks.dagger.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tasks.dagger.activity.module.ActivityModule;
import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.application.DaggerApplicationComponent;

/**
 * Author: murphy
 * Description: dagger activity make ActivityComponent instance to inject
 */
@SuppressLint("Registered")
public abstract class DaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(makeActivityComponent());
    }

    private ActivityComponent makeActivityComponent() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
        return DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this)).build();
    }

    protected abstract void inject(ActivityComponent activityComponent);
}
