package com.tasks.dagger.module;

import android.app.Application;

import androidx.fragment.app.FragmentActivity;

import com.tasks.utils.KeyBoardProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author: murphy
 * Description:
 */
@Module
public class KeyBoardModule {

    @Provides
    public KeyBoardProvider provideKeyboardHelper(Application application, FragmentActivity activity) {
        return new KeyBoardProvider(application, activity);
    }

    public interface Maker {
        KeyBoardProvider makeKeyboardHelper();
    }
}
