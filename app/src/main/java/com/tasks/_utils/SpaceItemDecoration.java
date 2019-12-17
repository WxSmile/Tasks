package com.tasks._utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: murphy
 * Description:
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int mod = parent.getChildAdapterPosition(view) % 4;
        if (mod == 0) outRect.left = 0;
        if (mod != 0) outRect.left = space;

    }
}
