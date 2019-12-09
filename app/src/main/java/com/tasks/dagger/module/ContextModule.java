package com.tasks.dagger.module;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.tasks.dagger.qualifier.ForActivityContext;
import com.tasks.dagger.qualifier.ForApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description:
 */

@Module
public class ContextModule {

    @Provides
    @ForApplicationContext
    Context provideApplication(Application application) {
        return application;
    }

    @Provides
    @ForActivityContext
    Context provideContext(FragmentActivity activity){
        return activity;
    }

    public interface Maker {
        @ForApplicationContext
        Context makeApplication();
        @ForActivityContext
        Context makeActivity();
    }
}
