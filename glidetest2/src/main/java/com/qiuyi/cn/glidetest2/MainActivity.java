package com.qiuyi.cn.glidetest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 *
 * 自定义实现的瀑布流图片展示
 *  Glide图片缓存
 *
 */
public class MainActivity extends AppCompatActivity {

    private int totalTranslateX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("TOTAL", "onCreate: "+totalTranslateX);
    }
}
