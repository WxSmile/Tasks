package com.tasks.data.dagger.test;

import com.tasks.data.dagger.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: murphy
 * Description:
 */

@Component(modules = {TestDataModule.class, UseCaseModule.class})
@Singleton
public interface TestComponent extends TestDataModule.Maker, UseCaseModule.Maker {

}
