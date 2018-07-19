package com.frank.simplifyclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;

public class SettingsData {
    private SharedPreferences sharedPreferences;
    private Editor            editor;
    public SettingsData(Context context){
        sharedPreferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean getShowSecond(boolean defaultValue){
        return sharedPreferences.getBoolean("showSecond",defaultValue);
    }
    public boolean setShowSecond(boolean isOpenFindMd){
        return editor.putBoolean("showSecond",isOpenFindMd).commit();
    }

    public boolean getShowLunar(boolean defaultValue){
        return sharedPreferences.getBoolean("showLunar",defaultValue);
    }
    public boolean setShowLunar(boolean isOpenFindMd){
        return editor.putBoolean("showLunar",isOpenFindMd).commit();
    }
}
