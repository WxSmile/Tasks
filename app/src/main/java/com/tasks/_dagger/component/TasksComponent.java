package com.tasks._dagger.component;

import com.tasks._dagger.module.ActivityModule;
import com.tasks._dagger.module.ContextModule;
import com.tasks._dagger.module.KeyBoardModule;
import com.tasks._dagger.module.ViewModelModule;
import com.tasks._dagger.scope.TasksScope;
import com.tasks.data.dagger.DataComponent;

import dagger.Component;

/**
 * Author: murphy
 * Description: inject
 */

@TasksScope
@Component(dependencies = DataComponent.class,
        modules = {ActivityModule.class, ContextModule.class, ViewModelModule.class, KeyBoardModule.class})
public interface TasksComponent extends ActivityModule.Maker, ContextModule.Maker,
                ViewModelModule.Maker, KeyBoardModule.Maker {

}
