package com.tasks.data.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Author: murphy
 * Description: data component scope
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
}
