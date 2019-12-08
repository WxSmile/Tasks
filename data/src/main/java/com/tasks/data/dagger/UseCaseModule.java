package com.tasks.data.dagger;

import com.tasks.data.domain.interactor.tasks.AddTaskUseCase;
import com.tasks.data.domain.interactor.tasks.GetCategoryTasksUseCase;
import com.tasks.data.domain.interactor.tasks.GetHotTasksUseCase;
import com.tasks.data.domain.interactor.tasks.UpdateTaskUseCase;
import com.tasks.data.repository.TasksRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide use case instance
 */
@Module
public class UseCaseModule {

    @Provides
    static AddTaskUseCase provideAddTaskUseCase(TasksRepository repository) {
        return new AddTaskUseCase(repository);
    }

    @Provides
    static GetCategoryTasksUseCase provideGetCategoryTasksUseCase(TasksRepository repository) {
        return new GetCategoryTasksUseCase(repository);
    }

    @Provides
    static GetHotTasksUseCase provideHostTasksUseCase(TasksRepository repository) {
        return new GetHotTasksUseCase(repository);
    }

    @Provides
    static UpdateTaskUseCase provideUpdateTaskUseCase(TasksRepository repository) {
        return new UpdateTaskUseCase(repository);
    }

    public interface Maker {
        AddTaskUseCase makeAddTaskUseCase();
        GetCategoryTasksUseCase makeGetCategoryTasksUseCase();
        GetHotTasksUseCase makeGetHotTasksUseCase();
        UpdateTaskUseCase makeUpdateTaskUseCase();
    }
}
