package com.frank.simplifyclock;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
    private TextView hour,minute,second,ymd,ymd2,day;
    private LinearLayout secondControl;
    private TimeHandler timeHandler;
    private TimeThread timeThread = TimeThread.getInstance();
    private SettingsData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        init();
        if (data.getShowLunar(false) == true){
            ymd2.setVisibility(View.VISIBLE);
            ymd.setVisibility(View.GONE);
        }else {
            ymd2.setVisibility(View.GONE);
            ymd.setVisibility(View.VISIBLE);
        }

        hour.setText(TimeUtil.hour());
        minute.setText(TimeUtil.minute());
        second.setText(TimeUtil.second()+"s");
        ymd.setText(TimeUtil.ymd());
        ymd2.setText(TimeUtil.lunar());
        day.setText(TimeUtil.halfDay()+" "+TimeUtil.week());

        ymd.setOnClickListener(this);
        ymd2.setOnClickListener(this);
        secondControl.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data.getShowSecond(true) == false){
            second.setVisibility(View.GONE);
        }else {
            second.setVisibility(View.VISIBLE);
        }
        if (data.getShowLunar(false) == true){
            ymd2.setVisibility(View.VISIBLE);
            ymd.setVisibility(View.GONE);
        }else {
            ymd2.setVisibility(View.GONE);
            ymd.setVisibility(View.VISIBLE);
        }
        if (!timeThread.isAlive()){
            timeThread.start();
        }

        timeThread.setTimeListener(new TimeThread.SecondChange(){
            @Override
            public void onSecond(String second) {
                Message message=new Message();
                message.arg1=Integer.parseInt(second);
                timeHandler.sendMessage(message);
            }
        });
    }

    public void init(){
        data = new SettingsData(this);
        hour = findViewById(R.id.hour_tv);
        minute = findViewById(R.id.minute_tv);
        second = findViewById(R.id.second_tv);
        ymd = findViewById(R.id.ymd_tv);
        ymd2 = findViewById(R.id.ymd_2_tv);
        day = findViewById(R.id.day_tv);
        secondControl = findViewById(R.id.second_control);
        timeThread = TimeThread.getInstance();
        timeHandler = new TimeHandler(hour,minute,second,ymd,ymd2,day);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeThread = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ymd_tv:
                data.setShowLunar(true);
                ymd.setVisibility(View.GONE);
                ymd2.setVisibility(View.VISIBLE);
                break;
            case R.id.ymd_2_tv:
                data.setShowLunar(false);
                ymd2.setVisibility(View.GONE);
                ymd.setVisibility(View.VISIBLE);
                break;
            case R.id.second_control:
                if (second.getVisibility() == View.VISIBLE){
                    second.setVisibility(View.GONE);
                    data.setShowSecond(false);
                }else {
                    second.setVisibility(View.VISIBLE);
                    data.setShowSecond(true);
                }
                break;
        }
    }
}
