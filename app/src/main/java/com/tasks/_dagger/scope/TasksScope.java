package com.tasks._dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Author: murphy
 * Description: tasks component scope
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TasksScope {
}
