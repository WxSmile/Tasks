package com.tasks.data.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: murphy
 * Description: dagger Qualifier, for test local task data source instance
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForLocalTest {
}
