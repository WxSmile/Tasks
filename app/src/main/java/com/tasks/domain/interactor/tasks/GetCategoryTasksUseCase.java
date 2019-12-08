package com.tasks.domain.interactor.tasks;

import androidx.lifecycle.LiveData;

import com.tasks.data.repository.TasksRepository;
import com.tasks.data.model.TaskModel;
import com.tasks.domain.interactor.SingleUseCase;

import java.util.List;

/**
 * Author: murphy
 * Description: get getTaskDao as category
 */
public class GetCategoryTasksUseCase implements SingleUseCase<List<TaskModel>, String> {

    private final TasksRepository tasksRepository;

    public GetCategoryTasksUseCase(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public LiveData<List<TaskModel>> execute(String parameter) {
        return tasksRepository.getCategoryTasks(parameter);
    }
}
