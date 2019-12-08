package com.tasks.data.model;

import java.util.Date;

/**
 * Author: murphy
 * Description: a task model for display
 */
public class TaskModel {

    private String name;
    private String describe;
    private String category;
    private Date date;
    private boolean completed;

    public TaskModel(String name, String describe, String category, Date date, boolean completed) {
        this.name = name;
        this.describe = describe;
        this.category = category;
        this.date = date;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
