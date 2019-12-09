package com.tasks.taskdetail;

import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.viewmodel.DaggerViewModel;

public class TaskDetailViewModel extends DaggerViewModel {

    @Override
    protected void inject(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }
}
