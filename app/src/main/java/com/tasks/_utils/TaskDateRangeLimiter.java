package com.tasks._utils;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.wdullaer.materialdatetimepicker.date.DateRangeLimiter;

import java.util.Calendar;

/**
 * Author: murphy
 * Description:
 */
public class TaskDateRangeLimiter implements DateRangeLimiter {


    @NonNull
    @Override
    public Calendar getStartDate() {
        return Calendar.getInstance();
    }

    @NonNull
    @Override
    public Calendar getEndDate() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, 100);
        return instance;
    }

    @Override
    public boolean isOutOfRange(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        if (instance.get(Calendar.YEAR) == year
                && instance.get(Calendar.MONTH) == month && day < instance.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Calendar setToNearestDate(@NonNull Calendar day) {
        return day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public TaskDateRangeLimiter() {
    }

    private TaskDateRangeLimiter(Parcel in) {
    }

    public static final Creator<TaskDateRangeLimiter> CREATOR = new Creator<TaskDateRangeLimiter>() {
        @Override
        public TaskDateRangeLimiter createFromParcel(Parcel source) {
            return new TaskDateRangeLimiter(source);
        }

        @Override
        public TaskDateRangeLimiter[] newArray(int size) {
            return new TaskDateRangeLimiter[size];
        }
    };
}
