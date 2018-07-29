package com.frank.simplifyclock.time;

public class TimeThread extends Thread {
    private static TimeThread timeThread;
    private SecondChange timeChange;
    public volatile boolean isRunning = true;

    private TimeThread(){ }

    public static TimeThread getInstance(){
        if (timeThread == null){
            timeThread = new TimeThread();
        }
        return timeThread;
    }
    @Override
    public void run() {
        super.run();
        while (isRunning){
            try {
                Thread.sleep(30000);
                if (isRunning) {
                    timeChange.onSecond();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        };
    }

    public void setTimeListener(SecondChange timeChange){
        this.timeChange = timeChange;
    }
    public interface SecondChange {
        public void onSecond();
    }

}
