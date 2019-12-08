package com.tasks.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tasks.dagger.fragment.DaggerFragment;
import com.tasks.dagger.fragment.FragmentComponent;

/**
 * Author: murphy
 * Description: base abstract fragment
 */
public abstract class BaseFragment extends DaggerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {

    }
}
