package com.tasks.utils;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.util.DateUtils;
import com.tasks.taskadd.CategoryModelWrapper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: murphy
 * Description: function for databinding @BindingAdapter
 */
public class BindableAdapterUtil {

    @BindingAdapter("data")
    public static <T> void setRecyclerViewDataSource(RecyclerView recyclerView, List<T> source) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BindableInterface) {
            ((BindableInterface) adapter).setData(source);
        }
    }

    @BindingAdapter("viewmodel")
    public static <T> void setRecyclerViewViewModel(RecyclerView recyclerView, T model) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BindableInterface) {
            ((BindableInterface) adapter).setViewModel(model);
        }
    }

    @BindingAdapter("select_category")
    public static void setCategorySelect(ConstraintLayout view, CategoryModelWrapper model) {
        view.setSelected(model.isSelect());
    }

    @BindingAdapter("hot_task_time")
    public static void setHotTimeToString(TextView textView, Date date) {
        String dateFormate = DateUtils.getDateFormate(date, DateFormat.getTimeInstance(DateFormat.SHORT));
        textView.setText(dateFormate);
    }

    @BindingAdapter("category_status_text")
    public static void setCategorySatus(TextView textView, CategoryStatusModel statusModel) {
        int completedCount = statusModel.getCompletedCount();
        int notCompletedCount = statusModel.getNotCompletedCount();
        String dateFormate = completedCount + " of " + (completedCount + notCompletedCount) + " completed";
        textView.setText(dateFormate);
    }

    @BindingAdapter("add_task_date")
    public static void setAddTaskDate(TextView textView, Calendar calendar) {
        String dateFormate = DateUtils.getDateFormate(calendar.getTime());
        textView.setText(dateFormate);
    }


    @BindingAdapter("add_task_time")
    public static void setAddTaskTime(TextView textView, Calendar calendar) {
        String dateFormate = DateUtils.getDateFormate(calendar.getTime(), DateFormat.getTimeInstance(DateFormat.SHORT));
        textView.setText(dateFormate);
    }
}
