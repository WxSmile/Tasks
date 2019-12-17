package com.tasks._utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.view.View;

import static android.view.ViewAnimationUtils.createCircularReveal;

/**
 * Author: murphy
 * Description: for circle reveal animator params
 */
public class CircleRevealHepler {

    private View target;

    private View referView;

    private View centerView;

    private boolean expended;

    private OnTargetViewHideListener listenerAdapter;

    public void setTarget(View target) {
        this.target = target;
    }

    public void setCenterView(View centerView) {
        this.centerView = centerView;
    }

    public void setReferView(View referView) {
        this.referView = referView;
    }

    public void setListenerAdapter(OnTargetViewHideListener listenerAdapter) {
        this.listenerAdapter = listenerAdapter;
    }

    private synchronized void startEnterCircleRevealAnimator() {
        Rect centerGlobalRect = new Rect();
        centerView.getGlobalVisibleRect(centerGlobalRect);
        int centerX = (centerGlobalRect.left + centerGlobalRect.right) / 2;
        int centerY = (centerGlobalRect.top + centerGlobalRect.bottom) / 2;

        int startRadius = 0;
        int endRadius = (int) Math.hypot(referView.getMeasuredWidth(), referView.getMeasuredHeight());

//        Log.d("Test", "centerX: " + centerX);
//        Log.d("Test", "centerY: " + centerY);
//        Log.d("Test", "startRadius: " + startRadius);
//        Log.d("Test", "endRadius: " + endRadius);

        target.setVisibility(View.VISIBLE);
        Animator circularReveal = createCircularReveal(target, centerX, centerY, startRadius, endRadius);
        circularReveal.setDuration(1200).start();
    }

    private synchronized void startExitCircleRevealAnimator() {
        Rect centerGlobalRect = new Rect();
        centerView.getGlobalVisibleRect(centerGlobalRect);
        int centerX = (centerGlobalRect.left + centerGlobalRect.right) / 2;
        int centerY = (centerGlobalRect.top + centerGlobalRect.bottom) / 2;

        int endRadius = 0;
        int startRadius = (int) Math.hypot(target.getWidth(), target.getHeight());
        Animator circularReveal = createCircularReveal(target, centerX, centerY, startRadius, endRadius);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                target.setVisibility(View.GONE);
                if (listenerAdapter != null) listenerAdapter.onTargetViewHide();
            }
        });
        circularReveal.setDuration(1000).start();
    }

    public synchronized void startCircleRevealAnimator() {
        if (!expended) {
            expended = true;
            startEnterCircleRevealAnimator();
        }else {
            expended = false;
            startExitCircleRevealAnimator();
        }
    }

    public interface OnTargetViewHideListener {
        void onTargetViewHide();
    }
}
