package com.tasks.domain.interactor.tasks;

import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;
import com.tasks.domain.interactor.NonResultUseCase;

/**
 * Author: murphy
 * Description: add a task as category
 */
public class AddTaskUseCase implements NonResultUseCase<TaskModel> {

    private final TasksRepository tasksRepository;

    public AddTaskUseCase(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public void execute(TaskModel parameter) {
        tasksRepository.addTask(parameter);
    }
}
