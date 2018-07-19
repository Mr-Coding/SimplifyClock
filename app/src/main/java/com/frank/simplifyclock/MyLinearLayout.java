package com.frank.simplifyclock;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
    private AnimatorSet animSet;
    private ObjectAnimator moveIn;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            moveIn = ObjectAnimator.ofFloat(this, "translationY", -130, 130, -130);
            moveIn.setRepeatCount(ValueAnimator.INFINITE);
            animSet = new AnimatorSet();
            animSet.play(moveIn);
            animSet.setDuration(13000);
            animSet.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void isMove(boolean isMove){
        if (isMove){
            animSet.start();
        }else {
            animSet.cancel();
        }
    }
}
