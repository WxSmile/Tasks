package com.tasks.utils;

import android.content.res.Resources;

import com.tasks.data.util.DateUtils;

/**
 * Author: murphy
 * Description:
 */
public class UnitUtil {

    public static float dp2px(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return dp * density + 0.5f;
    }
}
