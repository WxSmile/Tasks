package com.tasks.domain.model;

import androidx.annotation.NonNull;

/**
 * Author: murphy
 * Description: model of domain
 */
public class Category {

    @NonNull
    public String name;

    public Category(@NonNull String name) {
        this.name = name;
    }
}
