package com.tasks.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tasks.data.source.local.room.converter.Converter;
import com.tasks.data.source.local.room.dao.TaskDao;
import com.tasks.data.source.local.room.table.CategoryEntity;
import com.tasks.data.source.local.room.table.TaskEntity;

/**
 * Author: murphy
 * Description: The Room database  "TodoTask.db"
 */

@Database(entities = {TaskEntity.class, CategoryEntity.class}, version = 1)
@TypeConverters(value = Converter.class)
public abstract class TasksDatabase extends RoomDatabase {

    public abstract TaskDao getTaskDao();

}
