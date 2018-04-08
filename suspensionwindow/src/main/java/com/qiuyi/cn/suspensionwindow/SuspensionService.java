package com.qiuyi.cn.suspensionwindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.telecom.GatewayInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/5.
 * 悬浮窗服务
 */
public class SuspensionService extends Service{

    private static final String TAG = "SuspensionService";

    //悬浮窗
    LinearLayout myLayout;
    //实例化的WindowManager
    WindowManager windowManager;
    //布局参数
    WindowManager.LayoutParams params;

    TextView myText;

    //状态栏高度
    int statusBarHeight = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //创建悬浮窗
        createToucher();
    }

    //创建悬浮窗
    public void createToucher(){
        //获取布局参数设置
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        //设置悬浮窗的type,系统提示性窗口,一般都在应用程序窗口之上
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //并设置背景色透明
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控,接收外部点击事件
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.CENTER;

        params.x = 0;
        params.y = 0;

        //设置悬浮窗口的长宽数据
        params.width =  dipTopx(150);
        params.height = dipTopx(150);

        //获取悬浮窗布局
        myLayout = (LinearLayout) LayoutInflater.from(getApplication()).inflate(R.layout.activity_main,null);

        //添加布局
        windowManager.addView(myLayout,params);

        //主动计算出当前View的宽高信息.
        myLayout.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0)
        {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG,"状态栏高度为:" + statusBarHeight);

        //浮动窗口按钮.
        myText =  myLayout.findViewById(R.id.myText);

        //点击事件
        myText.setOnClickListener(new View.OnClickListener() {
            long[] hints = new long[2];
            @Override
            public void onClick(View v) {
                Log.i(TAG,"点击了");
                //将第一次点击的时间保存到hint数组中
                System.arraycopy(hints,1,hints,0,hints.length -1);
                hints[hints.length -1] = SystemClock.uptimeMillis();
                //第二次点击更新比较
                if (SystemClock.uptimeMillis() - hints[0] >= 700)
                {
                    Log.i(TAG,"要执行");
                    Toast.makeText(SuspensionService.this,"连续点击两次以退出",Toast.LENGTH_SHORT).show();
                }else
                {
                    Log.i(TAG,"即将关闭");
                    Toast.makeText(SuspensionService.this,"已退出",Toast.LENGTH_SHORT).show();
                    stopSelf();
                }
            }
        });

        myText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = windowManager.getDefaultDisplay().getWidth();
                int y = windowManager.getDefaultDisplay().getHeight();
                //设置移动
                params.x = (int) event.getRawX() - x/2;
                //这就是状态栏偏移量用的地方
                params.y = (int) event.getRawY() - y/2 -statusBarHeight;

                windowManager.updateViewLayout(myLayout,params);
                return false;
            }
        });


    }

    //px转dp
    public int pxTodip(float pxValue){
        float scale =  getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

    //dp转px
    public int dipTopx(float dpValue){
        float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale +0.5f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy()
    {
        //用myText检查悬浮窗还在不在，这里可以不要。优化悬浮窗时要用到。
        if (myText != null)
        {
            windowManager.removeView(myLayout);
        }
        super.onDestroy();
    }

}
