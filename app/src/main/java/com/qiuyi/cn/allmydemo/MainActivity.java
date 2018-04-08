package com.qiuyi.cn.allmydemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


//验证码倒计时
public class MainActivity extends Activity {
    private Button button;
    private BaseUtil baseUtil = new BaseUtilImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.myButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CodeTimerService.class);
                startService(intent);
            }
        });
    }


    // 广播接收者
    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            switch (action) {
                case Constant.RUNNING_START:
                    if (button.isEnabled())
                        button.setEnabled(false);
                    // 正在倒计时
                    button.setText(intent.getStringExtra("time"));
                    Log.e("nownow", "倒计时中(" + intent.getStringExtra("time") + ")");
                    break;
                case Constant.RUNNING_END:
                    // 完成倒计时
                    button.setEnabled(true);
                    button.setText("重新发送");
                    break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        // 注册广播
        registerReceiver(mUpdateReceiver, baseUtil.updateIntentFilter(new String[]{Constant.RUNNING_END, Constant.RUNNING_START}));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除注册
        unregisterReceiver(mUpdateReceiver);
    }


}
