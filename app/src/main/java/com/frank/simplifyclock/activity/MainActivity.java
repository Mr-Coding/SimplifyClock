package com.frank.simplifyclock.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.frank.simplifyclock.device.MySensor;
import com.frank.simplifyclock.R;
import com.frank.simplifyclock.SettingsData;
import com.frank.simplifyclock.time.MyTimer;
import com.frank.simplifyclock.time.TimeHandler;
import com.frank.simplifyclock.time.TimeThread;
import com.frank.simplifyclock.time.TimeUtil;
import com.frank.simplifyclock.view.MoveLayout;
import com.frank.simplifyclock.view.MyTextView;

public class MainActivity extends Activity implements View.OnClickListener{
    private MyTextView hour,minute,second,ymd,ymd2,day,colon;
    private LinearLayout secondControl;
    private MoveLayout moveLayout;
    private TimeHandler timeHandler;
    private TimeThread timeThread;
    private SettingsData data;
    private MySensor mySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main_activity);
        getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        init();
        if (data.getShowLunar(false)){
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

        mySensor = new MySensor((SensorManager) getSystemService(SENSOR_SERVICE));
        mySensor.setOnLightChanged(new MySensor.OnLightChanged() {
            @Override public void onHeight() {
                Log.i("MainActivity","亮度高");
                hour.toW();minute.toW();second.toW();ymd.toW();ymd2.toW();day.toW();colon.toW();
            }
            @Override public void onLow() {
                Log.i("MainActivity","亮度低");
                hour.toG();minute.toG();second.toG();ymd.toG();ymd2.toG();day.toG();colon.toG();
            }
        });

        new MyTimer().setOnTimeChange(new MyTimer.OnTimeChange() {
            @Override public void onSecond() {
                timeHandler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideBottomUIMenu();

        if (!timeThread.isAlive()){
            timeThread.isRunning = true;
            timeThread.start();
        }
        timeThread.setTimeListener(new TimeThread.SecondChange() {
            @Override public void onSecond() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        moveLayout.setMove();
                    }
                });
            }
        });

        if (!data.getShowSecond(true)){
            second.setVisibility(View.GONE);
        }else {
            second.setVisibility(View.VISIBLE);
        }
        if (data.getShowLunar(false)){
            ymd2.setVisibility(View.VISIBLE);
            ymd.setVisibility(View.GONE);
        }else {
            ymd2.setVisibility(View.GONE);
            ymd.setVisibility(View.VISIBLE);
        }
    }

    public void init(){
        data = new SettingsData(this);
        hour = findViewById(R.id.hour_tv);
        minute = findViewById(R.id.minute_tv);
        second = findViewById(R.id.second_tv);
        ymd = findViewById(R.id.ymd_tv);
        ymd2 = findViewById(R.id.ymd_2_tv);
        day = findViewById(R.id.day_tv);
        colon = findViewById(R.id.colon_tv);
        secondControl = findViewById(R.id.second_control);
        timeHandler = new TimeHandler(hour,minute,second,ymd,ymd2,day);
        timeThread = TimeThread.getInstance();
        moveLayout = findViewById(R.id.move_layout);
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

    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
