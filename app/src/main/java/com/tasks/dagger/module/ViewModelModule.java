package com.tasks.dagger.module;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelProviders;

import com.tasks.MainActivity;
import com.tasks.MainViewModel;
import com.tasks.dagger.base.TaskViewModelFactory;
import com.tasks.data.dagger.qualifier.ForRelease;
import com.tasks.data.repository.TasksRepository;
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
    MainViewModel provideMainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    public interface Maker extends Injector {
        MainViewModel makeMainViewModel();
        TasksViewModel makeTasksViewModel();
    }

    public interface Injector {

        void inject(MainActivity mainActivity);
    }
}
