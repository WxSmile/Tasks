package com.tasks;

import android.app.Application;
import android.content.Context;

import com.tasks.data.dagger.DaggerDataComponent;
import com.tasks.data.dagger.DataComponent;
import com.tasks.data.dagger.module.DataModule;

/**
 * Author: murphy
 * Description: getTaskDao application
 */
public class TasksApplication extends Application {

    private static TasksApplication mApplication;
    private static DataComponent dataComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        dataComponent = DaggerDataComponent.builder()
                .dataModule(new DataModule(get())).build();
    }

    public static TasksApplication get() {
        return mApplication;
    }

    public static DataComponent getDataComponent() {
        return dataComponent;
    }
}
