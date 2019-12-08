package com.tasks.domain.model;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Author: murphy
 * Description: model of domain
 */
public class Task {

    @NonNull
    public String name;

    public String describe;

    public String category;

    public Date date;

    public boolean completed;

    public Task(@NonNull String name, String describe,
                String category, Date date, boolean completed) {
        this.name = name;
        this.describe = describe;
        this.category = category;
        this.date = date;
        this.completed = completed;
    }
}
