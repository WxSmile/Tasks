package com.tasks.taskadd;

import com.tasks.data.model.CategoryModel;

/**
 * Author: murphy
 * Description:
 */
public class CategoryModelWrapper extends CategoryModel {

    private CategoryModel categoryModel;

    public CategoryModelWrapper(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
