package com.tasks.tasks;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;
import com.tasks.R;
import com.tasks.base.BaseFragment;
import com.tasks.databinding.FragmentTasksBinding;
import com.tasks.tasks.viewmodel.TasksViewModel;

import static com.tasks.utils.RecyclerViewItemAnimatorUtil.dissallowChangeAnimations;

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
        dataBinding.setViewModel(tasksViewModel);
        dataBinding.setLifecycleOwner(this);

        dataBinding.appBar.addOnOffsetChangedListener(this::onAppBarOffsetChanged);

        dissallowChangeAnimations(dataBinding.rcvHotTasks.getItemAnimator());
        dissallowChangeAnimations(dataBinding.rcvCategoryStatus.getItemAnimator());

        dataBinding.rcvHotTasks.setAdapter(new HotTasksAdapter());
        dataBinding.rcvCategoryStatus.setAdapter(new AllCategoryStatusAdapter(requireActivity()));

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

    private void onAppBarOffsetChanged(AppBarLayout appBarLayout, int offset) {
        //normal: offset == 0

        int totalScrollRange = appBarLayout.getTotalScrollRange();

        boolean expended = Math.abs(offset) != totalScrollRange || totalScrollRange == 0;
        Log.d("AppBar", "app bar layout is expended: " + expended);
    }
}
