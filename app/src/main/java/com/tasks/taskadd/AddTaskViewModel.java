package com.tasks.taskadd;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.tasks._dagger.base.DaggerViewModel;
import com.tasks.data.model.CategoryModel;
import com.tasks.data.model.TaskModel;
import com.tasks.data.repository.TasksRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: murphy
 * Description: add task view`s model
 */
public class AddTaskViewModel extends DaggerViewModel {

    private TasksRepository repository;
    private LiveData<List<CategoryModelWrapper>> categoriesEvent;
    private TaskModel taskModel = new TaskModel();
    private MutableLiveData<Calendar> taskDateEvent = new MutableLiveData();

    private Completable addTaskEvent;

    public AddTaskViewModel(@NonNull TasksRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public boolean checkAndAddTask(String taskName) {

        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(taskModel.getCategory())) {
            return false;
        }

        taskModel.setName(taskName);
        taskModel.setDescribe(taskName + new Random().nextInt(10));
        taskModel.setCompleted(false);

        Calendar calendar = getTaskDateEvent().getValue();
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        taskModel.setDate(calendar.getTime());

        addTaskEvent = repository.addTask(taskModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return true;
    }

    public void fetchCategories() {
        LiveData<List<CategoryModel>> allCategories = repository.getAllCategories();
        categoriesEvent = Transformations.map(allCategories, input -> {
            ArrayList<CategoryModelWrapper> categoryModelWrappers = new ArrayList<>();
            for (CategoryModel categoryModel : input) {
                categoryModelWrappers.add(new CategoryModelWrapper(categoryModel));
            }
            return categoryModelWrappers;
        });
    }

    public LiveData<List<CategoryModelWrapper>> getCategoriesEvent() {
        return categoriesEvent;
    }

    public void setTaskCategory(String category) {
        taskModel.setCategory(category);
    }

    public String getTaskCategory() {
        return taskModel.getCategory();
    }

    public Completable getAddTaskEvent() {
        return addTaskEvent;
    }

    public MutableLiveData<Calendar> getTaskDateEvent() {
        return taskDateEvent;
    }

    public void resetDate() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.SECOND, 0);
        taskModel.setDate(instance.getTime());
        taskDateEvent.setValue(instance);
    }

    public void setTime(int hourOfDay, int minute, int second) {
        Calendar calendar = getTaskDateEvent().getValue();
        if (calendar == null) return;

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        getTaskDateEvent().setValue(calendar);
    }

    public void setDate(int year, int month, int dayOfMonth) {
        Calendar calendar = getTaskDateEvent().getValue();
        if (calendar == null) return;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        getTaskDateEvent().setValue(calendar);
    }
}
