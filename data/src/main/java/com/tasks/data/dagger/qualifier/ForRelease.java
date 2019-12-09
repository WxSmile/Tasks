package com.tasks.data.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: murphy
 * Description: dagger Qualifier, for release local task database
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForRelease {
}
