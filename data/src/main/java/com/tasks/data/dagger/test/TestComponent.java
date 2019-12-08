package com.tasks.data.dagger.test;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: murphy
 * Description:
 */

@Component(modules = {TestDataModule.class})
@Singleton
public interface TestComponent extends TestDataModule.Maker {

}
