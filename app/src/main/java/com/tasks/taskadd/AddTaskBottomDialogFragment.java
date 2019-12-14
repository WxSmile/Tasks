package com.tasks.taskadd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tasks.R;
import com.tasks.databinding.FragmentAddTaskBinding;

import io.reactivex.disposables.Disposable;

/**
 * Author: murphy
 * Description:
 */
public class AddTaskBottomDialogFragment extends BottomSheetDialogFragment {

    private FragmentAddTaskBinding dataBinding;
    private AddTaskViewModel addTaskViewModel;
    private Disposable subscribe;

    public static AddTaskBottomDialogFragment getInstance() {
        return new AddTaskBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false);
        dataBinding.btnAddTask.setOnClickListener(this::addTask);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTaskViewModel = ViewModelProviders.of(requireActivity()).get(AddTaskViewModel.class);
    }

    private void addTask(View view) {
        subscribe = addTaskViewModel.addTask().subscribe(this::onAddTaskSuccess);
    }

    private void onAddTaskSuccess() {
        dismiss();

        if (requireActivity() instanceof OnAddTaskListener) {
            ((OnAddTaskListener) requireActivity()).onTaskAdded("Work");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (subscribe != null) subscribe.dispose();
    }

    public interface OnAddTaskListener {
        void onTaskAdded(String taskCategory);
    }
}
