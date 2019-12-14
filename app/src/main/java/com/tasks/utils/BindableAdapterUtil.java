package com.tasks.utils;

import android.util.Log;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.util.DateUtils;

import java.text.DateFormat;
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
        Log.d("Test", "setRecyclerViewViewModel: ");
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BindableInterface) {
            ((BindableInterface) adapter).setViewModel(model);
        }
    }

    @BindingAdapter("android:text")
    public static void setDateToString(TextView textView, Date date) {
        String dateFormate = DateUtils.getDateFormate(date, DateFormat.getDateTimeInstance());
        textView.setText(dateFormate);
    }

    @BindingAdapter("android:text")
    public static void setCategorySatus(TextView textView, CategoryStatusModel statusModel) {
        int completedCount = statusModel.getCompletedCount();
        int notCompletedCount = statusModel.getNotCompletedCount();
        String dateFormate = completedCount + " of " + (completedCount + notCompletedCount) + " completed";
        textView.setText(dateFormate);
    }
}
