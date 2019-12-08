package com.tasks.domain.interactor.tasks;

import androidx.lifecycle.LiveData;

import com.tasks.data.repository.TasksRepository;
import com.tasks.data.model.TaskModel;
import com.tasks.domain.interactor.SingleUseCase;

import java.util.List;

/**
 * Author: murphy
 * Description: get host getTaskDao
 */
public class GetHotTasksUseCase implements SingleUseCase<List<TaskModel>, Void> {

    private final TasksRepository tasksRepository;

    public GetHotTasksUseCase(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public LiveData<List<TaskModel>> execute(Void noParam) {
        return tasksRepository.getHotTasks();
    }
}
