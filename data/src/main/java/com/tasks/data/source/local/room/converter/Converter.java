package com.tasks.data.source.local.room.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Author: murphy
 * Description: a type converter for room
 */
public class Converter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
