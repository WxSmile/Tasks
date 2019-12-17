package com.tasks._utils;

import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tasks.R;
import com.tasks.categorydetail.CategorySectionAdapter;
import com.tasks.categorydetail.TaskModelSection;
import com.tasks.data.model.CategoryStatusModel;
import com.tasks.data.util.DateUtils;
import com.tasks.taskadd.CategoryModelWrapper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

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

    @BindingAdapter("section_data")
    public static <T> void setSectionRecyclerViewDataSource(RecyclerView recyclerView, Map<String, List<T>> source) {
        if (source == null) return;

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (!(adapter instanceof CategorySectionAdapter)) return;

        CategorySectionAdapter categorySectionAdapter = (CategorySectionAdapter) adapter;

        for (Map.Entry<String, List<T>> entry : source.entrySet()) {
            Section section = categorySectionAdapter.getSection(entry.getKey());
            if (section == null) {
                TaskModelSection modelSection = new TaskModelSection(entry.getKey(), categorySectionAdapter);
                modelSection.setViewModel(categorySectionAdapter.getViewModel());
                categorySectionAdapter.addSection(entry.getKey(), modelSection);
                ((BindableInterface)modelSection).setData(entry.getValue());
            }else {
                TaskModelSection modelSection = (TaskModelSection) section;
                ((BindableInterface)modelSection).setData(entry.getValue());
            }
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

    @BindingAdapter("category_task_date")
    public static void setCategoryTaskDateToString(TextView textView, Date date) {
        String dateFormate = DateUtils.getDateFormate(date, DateFormat.getDateInstance());
        textView.setText(dateFormate);
    }

    @BindingAdapter("hot_task_time")
    public static void setHotTimeToString(TextView textView, Date date) {
        String dateFormate = DateUtils.getDateFormate(date, DateFormat.getTimeInstance(DateFormat.SHORT));
        textView.setText(dateFormate);
    }

    @BindingAdapter("category_status_text")
    public static void setCategorySatus(TextView textView, CategoryStatusModel statusModel) {
        if (statusModel == null) {
            String dateFormate = " 0 of 0 completed";
            textView.setText(dateFormate);
            return;
        }

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

    @BindingAdapter("category_group_name")
    public static void setAddTaskTime(TextView textView, String groupOfDate) {
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        if (TextUtils.equals(groupOfDate, DateUtils.getDateFormate(time))) {
            textView.setText(R.string.today);
            return;
        }

        instance.add(Calendar.DAY_OF_MONTH, 1);
        time = instance.getTime();
        if (TextUtils.equals(groupOfDate, DateUtils.getDateFormate(time))) {
            textView.setText(R.string.tomorrow);
            return;
        }

        textView.setText(groupOfDate);
    }

    @BindingAdapter("set_category_title")
    public static void setCategoryTitle(Toolbar toolbar, String category) {
        toolbar.setTitle(category + " Tasks");
    }
}
