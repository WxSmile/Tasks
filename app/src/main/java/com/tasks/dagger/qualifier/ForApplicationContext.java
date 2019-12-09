package com.tasks.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: murphy
 * Description: dagger Qualifier, for Application Context
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForApplicationContext {
}
