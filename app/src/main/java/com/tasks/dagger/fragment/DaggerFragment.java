package com.tasks.dagger.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tasks.dagger.activity.ActivityComponent;
import com.tasks.dagger.activity.DaggerActivityComponent;
import com.tasks.dagger.activity.module.ActivityModule;
import com.tasks.dagger.application.ApplicationComponent;
import com.tasks.dagger.application.DaggerApplicationComponent;
import com.tasks.dagger.fragment.module.FragmentModule;

/**
 * Author: murphy
 * Description: dagger fragment make FragmentComponent instance to inject
 */
public abstract class DaggerFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(makeFragmentComponent());

    }

    protected abstract void inject(FragmentComponent makeFragmentComponent);

    private FragmentComponent makeFragmentComponent() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
        ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(getActivity())).build();
        return DaggerFragmentComponent.builder()
                .activityComponent(activityComponent)
                .fragmentModule(new FragmentModule(this))
                .build();
    }
}
