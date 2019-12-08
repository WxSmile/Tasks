package com.tasks.dagger.activity.module;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.tasks.dagger.activity.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide activity instance
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    FragmentActivity provideFragmentActivity() {
        return (FragmentActivity) activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(Activity activity) {
        return activity;
    }

    public interface Maker {

        Activity makeActivity();

        FragmentActivity makeFragmentActivity();

        @ActivityContext
        Context makeContext();
    }
}
