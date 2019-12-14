package com.tasks.taskdetail;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tasks.R;
import com.tasks.databinding.FragmentTaskDetailBinding;
import com.tasks.utils.CircleRevealHepler;


public class TaskDetailFragment extends Fragment {

    private String category;
    private TaskDetailViewModel mViewModel;
    private CircleRevealHepler circleRevealHepler;
    private FragmentTaskDetailBinding dataBinding;

    public static TaskDetailFragment newInstance(String category, CircleRevealHepler circleRevealHepler) {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setCircleRevealHepler(category, circleRevealHepler);
        return taskDetailFragment;
    }

    private void setCircleRevealHepler(String category, CircleRevealHepler circleRevealHepler) {
        this.category = category;
        this.circleRevealHepler = circleRevealHepler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false);
        View root = dataBinding.getRoot();
        circleRevealHepler.setTarget(root);
        root.addOnLayoutChangeListener(this::onLayoutChanged);
        return root;
    }

    private void onLayoutChanged(View view, int left, int top, int right, int bottom, int oldLeft,
                                 int oldTop, int oldRight, int oldBottom) {
        circleRevealHepler.startCircleRevealAnimator();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleRevealHepler.setTarget(view);
        circleRevealHepler.setListenerAdapter(this::popBackStack);
        dataBinding.toolbar.setNavigationOnClickListener(this::onNavCloseClicked);
    }

    private void popBackStack() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
    private void onNavCloseClicked(View view) {
        hide();
    }

    public void hide() {
        circleRevealHepler.startCircleRevealAnimator();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(TaskDetailViewModel.class);
    }
}
