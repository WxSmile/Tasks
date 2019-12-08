package com.tasks.dagger.activity;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: murphy
 * Description: dagger Qualifier, activity context
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ActivityContext {
}
