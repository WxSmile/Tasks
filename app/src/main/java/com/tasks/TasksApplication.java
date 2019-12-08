package com.tasks;

import android.app.Application;
import android.content.Context;

/**
 * Author: murphy
 * Description: getTaskDao application
 */
public class TasksApplication extends Application {

    private static TasksApplication mApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static TasksApplication get() {
        return mApplication;
    }
}
