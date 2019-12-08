package com.tasks.data.source.local.room.table;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import com.tasks.domain.model.Task;

/**
 * Author: murphy
 * Description: Immutable model class for a Task
 */

@Entity(tableName = "Tasks",
        indices = {@Index("category"), @Index(value = {"name"}, unique = true)},
        foreignKeys = {
                @ForeignKey(
                        entity = CategoryEntity.class,
                        parentColumns = "category_name",
                        childColumns = "category")})
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @Embedded public Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
