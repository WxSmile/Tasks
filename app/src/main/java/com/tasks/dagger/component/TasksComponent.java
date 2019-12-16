package com.tasks.dagger.component;

import com.tasks.dagger.module.ActivityModule;
import com.tasks.dagger.module.ContextModule;
import com.tasks.dagger.module.KeyBoardModule;
import com.tasks.dagger.module.ViewModelModule;
import com.tasks.dagger.scope.TasksScope;
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
