package com.tasks.utils;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * Author: murphy
 * Description: dissallow recyclerView item cahnge blink
 */
public class RecyclerViewItemAnimatorUtil {

    public static void dissallowChangeAnimations(RecyclerView.ItemAnimator itemAnimator) {
        if (itemAnimator == null) return;

        ((SimpleItemAnimator)itemAnimator).setSupportsChangeAnimations(false);

    }
}
