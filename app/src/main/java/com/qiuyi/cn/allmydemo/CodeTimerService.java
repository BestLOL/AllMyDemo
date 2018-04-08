package com.qiuyi.cn.allmydemo;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/1/28.
 *  倒计时demo
 */
public class CodeTimerService extends Service{

    private CountDownTimer mCodeTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 1
    @Override
    public void onCreate() {
        super.onCreate();
    }
    // 2
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //两个参数第一个是总时间,第二个是间隔时间
        mCodeTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //广播剩余时间
                broadcastUpdate(Constant.RUNNING_START,millisUntilFinished/1000+"");
            }

            @Override
            public void onFinish() {
                //广播倒计时结束
                broadcastUpdate(Constant.RUNNING_END);
                //停止服务
                stopSelf();
            }
        };
        mCodeTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }



    //发送带数据的广播
    private void broadcastUpdate(String runningStart, String s) {
        Intent intent = new Intent(runningStart);
        intent.putExtra("time",s);
        sendBroadcast(intent);
    }

    //发送不带数据的广播
    private void broadcastUpdate(String runningEnd) {
        Intent intent = new Intent(runningEnd);
        sendBroadcast(intent);
    }


    //3
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    //4
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
