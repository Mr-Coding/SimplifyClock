package com.frank.simplifyclock.time;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer extends Timer {
    public void setOnTimeChange(final OnTimeChange timeChange){
        scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                timeChange.onSecond();
            }
        },0,1000);
    }

    public interface OnTimeChange{
        public void onSecond();
    }
}
