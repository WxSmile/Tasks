package com.tasks.data.source.local.room.table;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.tasks.domain.model.Category;

/**
 * Author: murphy
 * Description: Immutable model class for a Category
 */

@Entity(tableName = "Categories",
            indices = @Index(value = "category_name", unique = true))
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    public int id;

    @Embedded(prefix = "category_") public Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
