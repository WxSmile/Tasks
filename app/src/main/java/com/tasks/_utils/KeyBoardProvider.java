package com.tasks._utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Field;

/**
 * Author: murphy
 * Description: keyboard height helper
 */
public class KeyBoardProvider {


    private Application application;

    private Activity activity;

    private KeyboardPopupWindow popupWindow;

    private MutableLiveData<Integer> keyboardHeight = new MutableLiveData<>();

    public KeyBoardProvider(Application application, FragmentActivity activity) {
        this.application = application;
        this.activity = activity;
    }

    public Application getApplication() {
        return application;
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * Show the soft input.
     */
    public void showSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    public void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Hide the soft input.
     */
    public void hideSoftInput() {
        Activity activity = getActivity();
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Toggle the soft input display or not.
     */
    public void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Return whether soft input is visible.
     * <p>The minimum height is 200</p>
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isSoftInputVisible() {
        return isSoftInputVisible(200);
    }

    /**
     * Return whether soft input is visible.
     *
     * @param minHeightOfSoftInput The minimum height of soft input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isSoftInputVisible(final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight() >= minHeightOfSoftInput;
    }

    private int getContentViewInvisibleHeight() {
        final FrameLayout contentView = getActivity().findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final Rect outRect = new Rect();
        contentViewChild.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtil", "Content View Bottom: "+ contentViewChild.getBottom());
        Log.d("KeyboardUtil", "Content View Visible Bottom: "+ outRect.bottom);
        int invisibleHeight = contentViewChild.getBottom() - outRect.bottom;
        Log.d("KeyboardUtil", "Content View Invisible Height: "+ invisibleHeight);
        return invisibleHeight;
    }

    /**
     * Register soft input changed listener.
     *
     */
    public void registerSoftInputChangedListener() {
        registerSoftInputChangedListener2();
    }

    private void registerSoftInputChangedListener2() {
        popupWindow = new KeyboardPopupWindow(getActivity());
        popupWindow.getContentView().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            View contentView = popupWindow.getContentView();
            if (contentView != null) {
                Point screenSize = new Point();
                getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);

                Rect rect = new Rect();
                contentView.getWindowVisibleDisplayFrame(rect);

                // REMIND, you may like to change this using the fullscreen size of the phone
                // and also using the status bar and navigation bar heights of the phone to calculate
                // the keyboard height. But this worked fine on a Nexus.
                int height = screenSize.y - rect.bottom;
                keyboardHeight.setValue(height);
            }
        });

        popupWindow.start();
    }

    /**
     * Fix the leaks of soft input.
     * <p>Call the function in {@link Activity onDestroy}.</p>
     *
     */
    public void fixSoftInputLeaks() {
        if (getActivity() == null) return;
        InputMethodManager imm =
                (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == getActivity()) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public LiveData<Integer> getKeyboardHeight() {
        return keyboardHeight;
    }
}
