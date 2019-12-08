package com.tasks.dagger.application.module;

import android.app.Application;
import android.content.Context;

import com.tasks.TasksApplication;
import com.tasks.dagger.application.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: project application instances
 */

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Application provideTasksApplication() {
        return TasksApplication.get();
    }

    @Provides
    @Singleton()
    @ApplicationContext
    Context provideApplicationContext() {
        return TasksApplication.get().getApplicationContext();
    }

    public interface Maker {
        Application makeTaskApplication();

        @ApplicationContext
        Context makeApplicationContext();
    }
}
