package com.tasks._utils;

import android.content.res.Resources;

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
