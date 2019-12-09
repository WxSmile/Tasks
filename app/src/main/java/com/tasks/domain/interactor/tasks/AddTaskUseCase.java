package com.tasks.domain.interactor.tasks;

import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;
import com.tasks.domain.interactor.CompletableUseCase;
import com.tasks.domain.interactor.NonResultUseCase;

import io.reactivex.Completable;

/**
 * Author: murphy
 * Description: add a task as category
 */
public class AddTaskUseCase implements CompletableUseCase<TaskModel> {

    private final TasksRepository tasksRepository;

    public AddTaskUseCase(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public Completable execute(TaskModel parameter) {
        return tasksRepository.addTask(parameter);
    }
}
