package com.tasks.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.tasks.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Author: murphy
 * Description:
 */
public class KeyboardPopupWindow extends PopupWindow {

    private Activity activity;

    public KeyboardPopupWindow(Activity activity) {
        super(activity);
        this.activity = activity;

        LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        assert inflator != null;
        setContentView(inflator.inflate(R.layout.popupwindow, null, false));

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        setWidth(0);
        setHeight(MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void start() {
        getActivityContentView().post(() -> {
            if (!isShowing() && getActivityContentView().getWindowToken() != null) {
                Log.d("Test", "isShowing: ");
                showAtLocation(getActivityContentView(), Gravity.NO_GRAVITY, 0, 0);
            }
        });

    }

    public View getActivityContentView() {
        return getActivity().findViewById(android.R.id.content);
    }

    public Activity getActivity() {
        return activity;
    }
}
