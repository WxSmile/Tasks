package com.tasks.tasks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.tasks.R;
import com.tasks.base.BaseFragment;
import com.tasks.databinding.FragmentTasksBinding;
import com.tasks.tasks.viewmodel.TasksViewModel;

import java.util.List;

/**
 * Author: murphy
 * Description: home view
 */
public class TasksFragment extends BaseFragment {

    private TasksViewModel tasksViewModel;

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTasksBinding dataBinding = DataBindingUtil.bind(view);
        assert dataBinding != null;
        dataBinding.setModel(tasksViewModel);
        dataBinding.setLifecycleOwner(this);

        dataBinding.rcvHotTasks.setItemAnimator(null);
        dataBinding.rcvCategoryStatus.setItemAnimator(null);

        dataBinding.rcvHotTasks.setAdapter(new HotTasksAdapter());
        dataBinding.rcvCategoryStatus.setAdapter(new AllCategoryStatusAdapter());

        tasksViewModel.fetchAllCategoryStatus();
        tasksViewModel.fetchHotTasks();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        assert activity != null;
        tasksViewModel = ViewModelProviders.of(activity).get(TasksViewModel.class);
    }

}
