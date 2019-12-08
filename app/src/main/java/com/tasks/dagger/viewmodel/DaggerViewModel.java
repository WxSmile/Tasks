package com.tasks.dagger.viewmodel;

import androidx.lifecycle.ViewModel;

import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.application.DaggerApplicationComponent;

/**
 * Author: murphy
 * Description: dagger inject ViewModel
 */
public abstract class DaggerViewModel extends ViewModel {

    public DaggerViewModel() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
        inject(applicationComponent);
    }

    protected abstract void inject(ApplicationComponent applicationComponent);
}
