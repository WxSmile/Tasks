package com.tasks;

import androidx.lifecycle.LiveData;

import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.viewmodel.DaggerViewModel;
import com.tasks.data.domain.interactor.tasks.AddTaskUseCase;
import com.tasks.data.domain.interactor.tasks.GetHotTasksUseCase;
import com.tasks.data.model.TaskModel;
import com.tasks.domain.interactor.Parameter;

import java.util.List;

import javax.inject.Inject;

/**
 * Author: murphy
 * Description: main view`s model
 */
public class MainViewModel extends DaggerViewModel {

    @Inject
    GetHotTasksUseCase getHotTasksUseCase;
    @Inject
    AddTaskUseCase addTaskUseCase;

    private LiveData<List<TaskModel>> hotTasks;

    @Override
    protected void inject(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    LiveData<List<TaskModel>> getHotTasks() {
        hotTasks = getHotTasksUseCase.execute(Parameter.returnVoid());
        return hotTasks;
    }
}
