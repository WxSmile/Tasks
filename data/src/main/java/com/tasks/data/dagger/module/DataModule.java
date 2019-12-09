package com.tasks.data.dagger.module;

import android.app.Application;

import androidx.room.Room;

import com.tasks.data.dagger.DataScope;
import com.tasks.data.dagger.qualifier.ForLocalRelease;
import com.tasks.data.dagger.qualifier.ForLocalTest;
import com.tasks.data.dagger.qualifier.ForRelease;
import com.tasks.data.dagger.qualifier.ForRemote;
import com.tasks.data.dagger.qualifier.ForTest;
import com.tasks.data.repository.TasksRepository;
import com.tasks.data.repository.TasksRepositoryImpl;
import com.tasks.data.source.TasksDataSource;
import com.tasks.data.source.local.LocalTasksDataSource;
import com.tasks.data.source.local.room.TasksDatabase;
import com.tasks.data.source.remote.RemoteTasksDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide a data relative instance
 */

@Module
public class DataModule {

    private Application application;

    public DataModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ForRelease
    TasksDatabase provideReleaseTasksDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(),
                TasksDatabase.class, "TodoTask.db").build();
    }

    @Provides
    @ForTest
    TasksDatabase provideTestTasksDatabase(Application application) {
        return Room.inMemoryDatabaseBuilder(application.getApplicationContext(),
                TasksDatabase.class).build();
    }

    @Provides
    @ForLocalTest
    TasksDataSource provideLocalTestTaskDataSource(@ForTest TasksDatabase database) {
        return new LocalTasksDataSource(database.getTaskDao());
    }

    @Provides
    @DataScope
    @ForLocalRelease
    TasksDataSource provideLocalReleaseTaskDataSource(@ForRelease TasksDatabase database) {
        return new LocalTasksDataSource(database.getTaskDao());
    }

    @Provides
    @DataScope
    @ForRemote
    TasksDataSource provideRemoteTaskDataSource() {
        return new RemoteTasksDataSource();
    }

    @Provides
    @DataScope
    @ForRelease
    TasksRepository provideReleaseTaskRepository(@ForLocalRelease TasksDataSource local,
                                          @ForRemote TasksDataSource remote) {
        return new TasksRepositoryImpl(local, remote);
    }

    @Provides
    @ForTest
    TasksRepository provideTestTaskRepository(@ForLocalTest TasksDataSource local,
                                          @ForRemote TasksDataSource remote) {
        return new TasksRepositoryImpl(local, remote);
    }

    public interface Maker {
        @ForRelease
        TasksDatabase makeReleaseTasksDatabase();

        @ForTest
        TasksDatabase makeTestTasksDatabase();

        @ForTest
        TasksRepository makeTestTaskRepository();

        @ForRelease
        TasksRepository makeReleaseTaskRepository();

        Application makeApplication();
    }
}
