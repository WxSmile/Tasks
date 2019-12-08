package com.tasks.dagger.activity;

import com.tasks.MainActivity;
import com.tasks.dagger.activity.module.ActivityModule;
import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.viewmodel.ViewModelModule;

import dagger.Component;

/**
 * Author: murphy
 * Description: make activity`s instance as scope
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, ViewModelModule.class})
public interface ActivityComponent extends ActivityModule.Maker{

    void inject(MainActivity activity);

}
