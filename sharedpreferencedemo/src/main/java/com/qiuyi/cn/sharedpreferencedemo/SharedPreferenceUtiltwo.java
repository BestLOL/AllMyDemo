package com.qiuyi.cn.sharedpreferencedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/1/31.
 *
 * 这种通用类的SharedPreferenceUtiltwo,实现了基本类型的转换，
 * 由于String是包装类，所以还需另外写if else判断，在这里就不写了，
 * 总的来说感觉很麻烦，不如直接用原来提供的方法
 *
 */
public class SharedPreferenceUtiltwo {

    private static final String SAVE_NAME = "saveMsg";
    private static SharedPreferences sp;

    //SharedPreference存储
    public static <T> void setObjectMessage(Context context,String name,T value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }

        //得到put的方法
        SharedPreferences.Editor editor = sp.edit();
        Class<?> myClass = SharedPreferences.Editor.class;

        Class<?> baseClass = value.getClass();
        try {
            //将封装类转换为基本类
            Field f = baseClass.getDeclaredField("TYPE");
            Object obj = f.get(baseClass);

            String myObj = obj.toString();
            String upObj = myObj.toUpperCase();
            String objLast = upObj.charAt(0)+myObj.substring(1,myObj.length());
            //方法名
            String myPut = "put"+objLast;

            Log.e("myName",((Class)obj).toString() +"");
            //将得到的基本类型int boolean
            Method method = myClass.getDeclaredMethod(myPut,new Class[]{String.class,(Class) obj});
            SharedPreferences.Editor myEdit = (SharedPreferences.Editor) method.invoke(editor,name,value);
            myEdit.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SharedPreference得到数据
    public static <T>T getObjectMessage(Context context,String key,T value){
        if(sp==null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        Class<?> myClass = SharedPreferences.class;

        Class<?> baseClass = value.getClass();
        try {
            //将封装类转换为基本类
            Field f = baseClass.getDeclaredField("TYPE");
            Object obj = f.get(baseClass);

            String myObj = obj.toString();
            String upObj = myObj.toUpperCase();
            String objLast = upObj.charAt(0)+myObj.substring(1,myObj.length());
            //方法名
            String myGet = "get"+objLast;


            Log.e("myName",((Class)obj).toString() +"");
            Method method = myClass.getMethod(myGet,String.class,(Class)obj);
            T myMsg = (T) method.invoke(sp,key,value);
            return myMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //SharedPreference存储移除
    public static void removeObjectMessage(Context context,String name){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().remove(name).commit();
    }

    //Boolean类型的设置与得到
    public static Boolean getBoolean(Context context,String key,Boolean value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,value);
    }

    public static void setBoolean(Context context,String key,Boolean value){
        if(sp == null){
            sp = context.getSharedPreferences(SAVE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }
}
