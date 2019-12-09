package com.tasks.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tasks.dagger.base.DaggerActivity;

/**
 * Author: murphy
 * Description: base activity to do abstract thing
 */
public abstract class BaseActivity extends DaggerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
