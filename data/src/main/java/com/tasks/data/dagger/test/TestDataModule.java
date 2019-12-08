package com.tasks.data.dagger.test;

import com.tasks.data.dagger.ForLocalTaskDataSource;
import com.tasks.data.dagger.ForRemoteTaskDataSource;
import com.tasks.data.repository.TasksRepository;
import com.tasks.data.repository.TasksRepositoryImpl;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.LocalTasksDataSource;
import com.tasks.data.source.local.room.TasksDatabase;
import com.tasks.data.source.remote.RemoteTasksDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide a data relative instance
 */

@Module
public class TestDataModule {

    private TasksDatabase tasksDatabase;

    public TestDataModule(TasksDatabase tasksDatabase) {
        this.tasksDatabase = tasksDatabase;
    }

    @Provides
    @Singleton
    public TasksDatabase provideTaskDatabase() {
        return tasksDatabase;
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
