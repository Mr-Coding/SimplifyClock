package com.frank.simplifyclock.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
    private AnimatorSet animSet;
    private ObjectAnimator moveIn;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void move(float... values){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            moveIn = ObjectAnimator.ofFloat(this, "translationY", values);
            moveIn.setRepeatCount(ValueAnimator.INFINITE);
            moveIn.setInterpolator(new Interpolator() {
                @Override public float getInterpolation(float input) {
                    return input;
                }
            });
            animSet = new AnimatorSet();
            animSet.play(moveIn);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏
                animSet.setDuration(30000);
            }else {
                animSet.setDuration(9000);
            }
            animSet.start();
        }
    }
}
