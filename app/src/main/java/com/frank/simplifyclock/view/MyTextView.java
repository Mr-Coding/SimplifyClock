package com.frank.simplifyclock.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyTextView extends TextView {
    private AnimatorSet animSet;
    private ObjectAnimator toWhite;
    private ObjectAnimator toGray;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        toWhite = ObjectAnimator.ofInt(this,"textColor",Color.argb(255,126,126,126),Color.WHITE);
        toWhite.setEvaluator(new ArgbEvaluator());

        toGray = ObjectAnimator.ofInt(this,"textColor",Color.WHITE,Color.argb(255,126,126,126));
        toGray.setEvaluator(new ArgbEvaluator());
    }

    public void toW(){
        animSet = new AnimatorSet();
        animSet.play(toWhite);
        animSet.setDuration(300);
        animSet.start();
    }

    public void toG(){
        animSet = new AnimatorSet();
        animSet.play(toGray);
        animSet.setDuration(300);
        animSet.start();
    }
}
