package com.tasks.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tasks.R;
import com.tasks.base.BaseFragment;
import com.tasks.dagger.fragment.FragmentComponent;
import com.tasks.tasks.viewmodel.TasksViewModel;

import javax.inject.Inject;

/**
 * Author: murphy
 * Description: home view
 */
public class TasksFragment extends BaseFragment {

    @Inject
    TasksViewModel mViewModel;

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
