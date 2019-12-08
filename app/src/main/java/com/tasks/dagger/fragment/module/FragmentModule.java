package com.tasks.dagger.fragment.module;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description: provide fragment instance
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    FragmentManager provideFragmentManager(Fragment fragment) {
        return fragment.getFragmentManager();
    }

    public interface Maker {

        FragmentManager makeFragmentManager();
        Fragment makeFragment();
    }
}
