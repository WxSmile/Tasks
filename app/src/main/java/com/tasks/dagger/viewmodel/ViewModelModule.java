package com.tasks.dagger.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.tasks.MainViewModel;
import com.tasks.tasks.viewmodel.TasksViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description:
 */

@Module
public class ViewModelModule {

    @Provides
    TasksViewModel provideHomeViewModel(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(TasksViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    public interface Maker {
        MainViewModel makeMainViewModel();
        TasksViewModel makeHomeViewModel();
    }
}
