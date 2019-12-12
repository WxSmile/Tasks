package com.tasks.data.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: murphy
 * Description:
 */
public class DateUtils {

    public static Date getTodayZeroClockTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }

    public static Date getNextTodayZeroClockTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 24);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }

    public static Date getCurrentTime() {
        Calendar instance = Calendar.getInstance();
        return instance.getTime();
    }

    public static String getDateFormate(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(date);
    }

    public static String getDateFormate(Date date, DateFormat dateFormat) {
        if (dateFormat == null) {
            return getDateFormate(date);
        }

        return dateFormat.format(date);
    }
}
