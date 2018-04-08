package com.qiuyi.cn.mysqlite;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/3/8.
 * android SharedPreference存储
 */
public class SharedPreUtl {

    private static SharedPreferences myShared = null;

    public static void setStringMsg(Context context,String name,String content){
        if(myShared==null){
            myShared = context.getSharedPreferences("mySharedData",Context.MODE_PRIVATE);
        }
        myShared.edit().putString(name,content).apply();
    }

    public static String getStringMsg(Context context,String name,String content){
        if(myShared==null){
            myShared = context.getSharedPreferences("mySharedData",Context.MODE_PRIVATE);
        }
        return myShared.getString(name,content);
    }
}
