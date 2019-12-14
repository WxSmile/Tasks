package com.tasks.dagger.module;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelProviders;

import com.tasks.MainActivity;
import com.tasks.MainViewModel;
import com.tasks.SplashActivity;
import com.tasks.SplashViewModel;
import com.tasks.dagger.base.TaskViewModelFactory;
import com.tasks.data.dagger.qualifier.ForRelease;
import com.tasks.data.repository.TasksRepository;
import com.tasks.taskadd.AddTaskViewModel;
import com.tasks.taskdetail.TaskDetailViewModel;
import com.tasks.tasks.viewmodel.TasksViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide any ViewModel instance
 */

@Module
public class ViewModelModule {

    @Provides
    Factory provideViewModelFactory(@ForRelease TasksRepository tasksRepository) {
        return new TaskViewModelFactory(tasksRepository);
    }

    @Provides
    TasksViewModel provideTasksViewModel(FragmentActivity activity, Factory factory) {
        return ViewModelProviders.of(activity, factory).get(TasksViewModel.class);
    }

    @Provides
    AddTaskViewModel provideAddTaskViewModel(FragmentActivity activity, Factory factory) {
        return ViewModelProviders.of(activity, factory).get(AddTaskViewModel.class);
    }

    @Provides
    TaskDetailViewModel provideTaskDetailViewModel(FragmentActivity activity, Factory factory) {
        return ViewModelProviders.of(activity, factory).get(TaskDetailViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(FragmentActivity activity, Factory factory) {
        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }

    @Provides
    SplashViewModel provideSplashViewModel(FragmentActivity activity, Factory factory) {
        return ViewModelProviders.of(activity, factory).get(SplashViewModel.class);
    }

    public interface Maker extends Injector {
        MainViewModel makeMainViewModel();
        TaskDetailViewModel makeTaskDetailViewModel();
        TasksViewModel makeTasksViewModel();
        AddTaskViewModel makeAddTaskViewModel();
        SplashViewModel makeSplashViewModel();
    }

    public interface Injector {

        void inject(MainActivity mainActivity);

        void inject(SplashActivity splashActivity);
    }
}
