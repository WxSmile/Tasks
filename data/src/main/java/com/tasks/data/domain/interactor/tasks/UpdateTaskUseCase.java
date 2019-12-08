package com.tasks.data.domain.interactor.tasks;

import com.tasks.data.repository.TasksRepository;
import com.tasks.data.model.TaskModel;
import com.tasks.domain.interactor.CompletableUseCase;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: update a task status
 */
public class UpdateTaskUseCase implements CompletableUseCase<TaskModel> {

    private final TasksRepository tasksRepository;

    public UpdateTaskUseCase(TasksRepository repository) {
        this.tasksRepository = repository;
    }

    @Override
    public Completable execute(TaskModel parameter) {
        return tasksRepository.updateTask(parameter.getName(), parameter.isCompleted());
    }
}
