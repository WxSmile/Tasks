package com.tasks.dagger.application;

import com.tasks.MainViewModel;
import com.tasks.dagger.application.module.ApplicationModule;
import com.tasks.dagger.usecase.UseCaseModule;
import com.tasks.data.dagger.DataModule;
import com.tasks.taskdetail.TaskDetailViewModel;
import com.tasks.tasks.viewmodel.TasksViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: murphy
 * Description: make global application instance as scope
 */

@Singleton
@Component(modules = {ApplicationModule.class, UseCaseModule.class, DataModule.class})
public interface ApplicationComponent extends ApplicationModule.Maker,
        UseCaseModule.Maker, DataModule.Maker {

    void inject(TasksViewModel tasksViewModel);

    void inject(MainViewModel mainViewModel);

    void inject(TaskDetailViewModel detailViewModel);
}
