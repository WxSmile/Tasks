package com.tasks.dagger.fragment;

import com.tasks.dagger.activity.ActivityComponent;
import com.tasks.dagger.fragment.module.FragmentModule;
import com.tasks.dagger.viewmodel.ViewModelModule;
import com.tasks.tasks.TasksFragment;

import dagger.Component;

/**
 * Author: murphy
 * Description: provide fragment instance
 */

@FragmentScope
@Component(dependencies = ActivityComponent.class,
        modules = {FragmentModule.class, ViewModelModule.class})
public interface FragmentComponent extends FragmentModule.Maker, ViewModelModule.Maker {

    void inject(TasksFragment tasksFragment);
}
