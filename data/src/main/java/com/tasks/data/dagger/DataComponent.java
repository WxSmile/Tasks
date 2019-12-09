package com.tasks.data.dagger;

import com.tasks.data.dagger.module.DataModule;

import dagger.Component;

/**
 * Author: murphy
 * Description: provider Data related instance
 */
@DataScope
@Component(modules = {DataModule.class})
public interface DataComponent extends DataModule.Maker {

}
