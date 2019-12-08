package com.tasks.data.dagger;

import android.app.Application;

import androidx.room.Room;

import com.tasks.data.repository.TasksRepository;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.repository.TasksRepositoryImpl;
import com.tasks.data.source.remote.RemoteTasksDataSource;
import com.tasks.data.source.local.LocalTasksDataSource;
import com.tasks.data.source.local.room.TasksDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide a data relative instance
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    public TasksDatabase provideTaskDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(),
                TasksDatabase.class, "TodoTask.db").build();
    }

    @Provides
    @ForLocalTaskDataSource
    TasksDataSource provideLocalTaskDataSource(TasksDatabase database) {
        return new LocalTasksDataSource(database.getTaskDao());
    }

    @Provides
    @ForRemoteTaskDataSource
    TasksDataSource provideRemoteTaskDataSource() {
        return new RemoteTasksDataSource();
    }

    @Provides
    @Singleton
    TasksRepository provideTaskRepository(@ForLocalTaskDataSource TasksDataSource local,
                                          @ForRemoteTaskDataSource TasksDataSource remote) {
        return new TasksRepositoryImpl(local, remote);
    }

    public interface Maker {
        TasksDatabase makeTasksDatabase();
        TasksRepository makeTaskRepository();
    }
}
