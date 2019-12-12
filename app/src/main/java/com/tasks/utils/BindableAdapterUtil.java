package com.tasks.utils;

import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.util.DateUtils;
import com.tasks.tasks.AllCategoryStatusAdapter;
import com.tasks.tasks.HotTasksAdapter;

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
        if (adapter instanceof HotTasksAdapter) {

            if (source == null) {
                Log.d("Test", "HotTasksAdapter: null" );
            }else {
                Log.d("Test", "HotTasksAdapter: " + source.size() );

            }
        }

        if (adapter instanceof AllCategoryStatusAdapter) {
            if (source == null) {
                Log.d("Test", "AllCategoryStatusAdapter: null" );
            }else {
                Log.d("Test", "AllCategoryStatusAdapter: " + source.size() );

            }
        }
        if (adapter instanceof BindableAdapter) {
            ((BindableAdapter) adapter).setData(source);
        }
    }

    @BindingAdapter("android:text")
    public static void setDateToString(TextView textView, Date date) {
        String dateFormate = DateUtils.getDateFormate(date, DateFormat.getDateInstance());
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
