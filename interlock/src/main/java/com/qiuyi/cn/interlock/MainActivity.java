package com.qiuyi.cn.interlock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class MainActivity extends Activity {

    private static final String SHOW_TEXT = "com.qiuyi.cn.interlock";
    private static final String SERVICE_KILL = "com.qiuyi.cn.interServiceKill";

    private TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (TextView) findViewById(R.id.myText);

        Intent intent = new Intent(this,InterlockService.class);
        startService(intent);

/*        finish();*/

        Log.e("MyActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MyActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MyActivity", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MyActivity", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MyActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MyActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

/*        Intent intent = new Intent(SERVICE_KILL);
        intent.putExtra("PackageName",getApplicationContext().getPackageName());
        sendBroadcast(intent);*/
        Log.e("MyActivity", "onDestroy");
    }
}
