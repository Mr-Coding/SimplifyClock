package com.frank.simplifyclock;

import android.util.Log;

public class TimeThread extends Thread {
    private static TimeThread timeThread;
    private SecondChange timeChange;
    private int second = 0;
    private TimeThread(){
        second = Integer.parseInt(TimeUtil.second());
    }

    public static TimeThread getInstance(){
        if (timeThread == null){
            timeThread = new TimeThread();
        }
        return timeThread;
    }
    @Override
    public void run() {
        super.run();
        do{
            try {
                Thread.sleep(1000);
                Log.i("TimeThread","--- One Second ---");
                second ++;
                if (second == 60){
                    second = 0;
                }
                timeChange.onSecond(second + "");
            } catch (Exception e){
                e.printStackTrace();
            }
        }while (true);
    }

    public void setTimeListener(SecondChange timeChange){
        this.timeChange = timeChange;
    }
    public interface SecondChange {
        public void onSecond(String second);
    }

}
