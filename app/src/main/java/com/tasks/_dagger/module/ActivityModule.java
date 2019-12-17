package com.tasks._dagger.module;

import androidx.fragment.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description:
 */

@Module
public class ActivityModule {

    private FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    FragmentActivity provideFragmentActivity() {
        return activity;
    }

    public interface Maker {
        FragmentActivity makeFragmentActivity();
    }
}
