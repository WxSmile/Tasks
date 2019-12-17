package com.tasks._base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tasks._dagger.base.DaggerActivity;

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
