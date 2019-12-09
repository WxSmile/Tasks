package com.tasks.data.model;

/**
 * Author: murphy
 * Description: a category model for display
 */
public class CategoryModel {

    private String category;

    private int total;

    private int completedCount;

    private int notCompletedCount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getNotCompletedCount() {
        return notCompletedCount;
    }

    public void setNotCompletedCount(int notCompletedCount) {
        this.notCompletedCount = notCompletedCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
