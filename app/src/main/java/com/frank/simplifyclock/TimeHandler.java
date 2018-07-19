package com.frank.simplifyclock;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class TimeHandler extends Handler {
    private TextView hour,minute,second,ymd,ymd2,day;
    public TimeHandler(TextView hour,TextView minute,TextView second,TextView ymd,TextView ymd2,TextView day) {
        this.hour   = hour;
        this.minute = minute;
        this.second = second;
        this.ymd    = ymd;
        this.ymd2    = ymd2;
        this.day    = day;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        second.setText(TimeUtil.second()+"s");

        if (msg.arg1 == 0){
            hour.setText(TimeUtil.hour());
            minute.setText(TimeUtil.minute());
            ymd.setText(TimeUtil.ymd());
            ymd2.setText(TimeUtil.lunar());
            day.setText(TimeUtil.halfDay()+" "+TimeUtil.week());
        }
    }
}

