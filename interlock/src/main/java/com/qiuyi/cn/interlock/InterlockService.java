package com.qiuyi.cn.interlock;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.security.auth.login.LoginException;

/**
 *
 * 杀不死的service（失败）
 */
public class InterlockService extends Service{

    private static final String SHOW_TEXT = "com.qiuyi.cn.interlock";

    private ExecutorService executorService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("MyActivity", "onCreateService");
        //线程池默认大小30
        executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Intent intent = new Intent(SHOW_TEXT);
                    //Math.random获取的是0-1的随机数
                    intent.putExtra("service",(int)(Math.random()*10)+"");

                    Log.e("myServiceText",(int)(Math.random()*10)+"");

                    sendBroadcast(intent);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyActivity", "onStartCommandService");

/*        final String packageName = intent.getStringExtra("PackageName");
        //重启app
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        },3000);*/

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        Log.e("MyService", "onDestroy: ");
        super.onDestroy();
    }
}
