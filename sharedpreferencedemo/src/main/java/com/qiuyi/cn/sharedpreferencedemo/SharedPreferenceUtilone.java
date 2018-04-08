package com.qiuyi.cn.sharedpreferencedemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/31.
 * SharePreference方法
 */
public class SharedPreferenceUtilone {
    private static final String SAVE_NAME = "saveMsg";
    private static SharedPreferences sp;

    public static Boolean getBoolean(Context context,String key,boolean value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,value);
    }

    public static void setBoolean(Context context,String key,boolean value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static void removeBoolean(Context context,String key,boolean value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

    public static String getString(Context context,String key,String value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        return sp.getString(key,value);
    }

    public static void setString(Context context,String key,String value) {
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static void removeString(Context context,String key,String value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
}
