package com.qiuyi.cn.suspensionwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


//悬浮窗小demo
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //当系统版本大于android6.0有些危险权限需要动态获取
        if(Build.VERSION.SDK_INT >=23){
            if(Settings.canDrawOverlays(MainActivity.this)){
                //已获得权限
                Intent intent = new Intent(MainActivity.this,SuspensionService.class);
                Toast.makeText(MainActivity.this,"已开启Toucher", Toast.LENGTH_SHORT).show();
                startService(intent);
                finish();
            }else{
                //未获得权限去获得
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(MainActivity.this,"需要取得权限以使用悬浮窗",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }else{
            //版本低于6.0
            Intent intent = new Intent(MainActivity.this,SuspensionService.class);
            startService(intent);
            finish();
        }
    }
}
