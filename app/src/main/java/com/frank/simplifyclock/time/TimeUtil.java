package com.frank.simplifyclock.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    //得到long类型当前时间
    private static long l;
    //new日期对象
    private static Date date = new Date(l);
    //转换提日期输出格式
    private static SimpleDateFormat dateFormat ;

    private static void init(){
        l    = System.currentTimeMillis();
        date = new Date(l);
    }

    public static String ymd(){
        init();
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(date);
    }

    public static String week(){
        init();
        dateFormat = new SimpleDateFormat("EEEE");
        String week = dateFormat.format(date);
        return week;
    }

    public static String lunar(){
        Calendar today = Calendar.getInstance();
        try {
            today.setTime(Lunar.chineseDateFormat.parse(TimeUtil.ymd()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Lunar lunar = new Lunar(today);
        return lunar+"";
    }

    public static String halfDay(){
        int h = Integer.parseInt(hour());
        if (h >= 13){
            return "PM";
        }
        return "AM";
    }

    public static String hour(){
        init();
        dateFormat = new SimpleDateFormat("HH");
        return dateFormat.format(date);
    }

    public static String minute(){
        init();
        dateFormat = new SimpleDateFormat("mm");
        return dateFormat.format(date);
    }

    public static String second(){
        init();
        dateFormat = new SimpleDateFormat("ss");
        return dateFormat.format(date);
    }
}
