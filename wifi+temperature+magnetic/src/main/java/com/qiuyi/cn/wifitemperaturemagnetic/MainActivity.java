package com.qiuyi.cn.wifitemperaturemagnetic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private WifiUtil mWifiUtils;
    private SensorManager mSensorManager;
    //磁场传感器
    private Sensor mMagneticSensor;
    //温度传感器
    private Sensor mTemperatureSensor;

    private Timer timer;

    private TextView tv_show;
    private EditText et_time;
    private Button bt_start,bt_end;

    private float mMagnetic_X = 0.f,mMagnetic_Y = 0.f,mMagnetic_Z = 0.f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiUtils = new WifiUtil(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        tv_show = (TextView) findViewById(R.id.show_tv);
        et_time = (EditText) findViewById(R.id.et_time);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_end = (Button) findViewById(R.id.bt_end);


        //findWifiInfo(3000);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etTime = et_time.getText().toString();
                long timeRF = 0;
                if(!etTime.equals("")){
                    timeRF = Long.parseLong(et_time.getText().toString())*1000;
                }
                if(timeRF != 0){
                    Log.e("time", timeRF+"");
                    if(timer!=null){
                        timer.cancel();
                    }
                    tv_show.setText("");
                    findWifiInfo(timeRF);
                }
            }
        });

        bt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer!=null){
                    timer.cancel();
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        //为传感器注册监听器
        /*SensorManager.SENSOR_DELAY_FASTEST:
        指定可能最快的传感器更新速率
        SensorManager.SENSOR_DELAY_GAME:
        指定适合在游戏中使用的更新速率
        SensorManager.SENSOR_DELAY_NORMAL:
        指定默认的更新速率
        SensorManager.SENSOR_DELAY_UI:
        指定适合于更新UI的更新速率*/
        mSensorManager.registerListener(mySensorListener,mMagneticSensor,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mySensorListener,mTemperatureSensor,SensorManager.SENSOR_DELAY_UI);
    }

    private SensorEventListener mySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_MAGNETIC_FIELD:
                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];
                    //Log.e("Magnetic", "X："+x+" Y："+y+" Z："+z);
                    mMagnetic_X = x;
                    mMagnetic_Y = y;
                    mMagnetic_Z = z;
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    Log.e("Temperature", "temperature");
                    float temperatureValue = sensorEvent.values[0]; // 得到温度
                    Log.e("Temperature", "temperature："+temperatureValue);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    //查找周围Wifi信息
    private void findWifiInfo(long timeRF) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final List<ScanResult> listInfo = mWifiUtils.getScanWifiResult();
                //Log.e("mac", mWifiUtils.getCurrentWifiInfo().getMacAddress());
                final float x = mMagnetic_X;
                final float y = mMagnetic_Y;
                final float z = mMagnetic_Z;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String showTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        tv_show.append("时间："+showTime+"\n");
                        tv_show.append("WIFi信息："+"\n");
                        for(ScanResult result:listInfo){
                            //Log.e("Info","Time："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" Name："+result.BSSID+" SSID："+result.SSID+" Level："+result.level);
                            tv_show.append("Mac地址："+result.BSSID+" 名称："+result.SSID+" 强度："+result.level+"\n");
                        }
                        tv_show.append("磁场X："+x+" 磁场Y："+y+" 磁场Z："+z+"\n");
                        tv_show.append("-----------------------------------------"+"\n");
                    }
                });

            }
        },1000,timeRF);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // 取消监听
        mSensorManager.unregisterListener(mySensorListener);
    }
}
/*        List<ScanResult> listInfo = mWifiUtils.getScanWifiResult();
        for(ScanResult result:listInfo){
            Log.e("newInfo", "Name："+result.BSSID+" SSID："+result.SSID+" Level："+result.level);
        }*/
/*
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:sensors){
            Log.e("sensor", "sensor: "+sensor.getName());
        }
*/
