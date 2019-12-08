package com.tasks.data.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: murphy
 * Description: dagger Qualifier, for remote task data source instance
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForRemoteTaskDataSource {
}
