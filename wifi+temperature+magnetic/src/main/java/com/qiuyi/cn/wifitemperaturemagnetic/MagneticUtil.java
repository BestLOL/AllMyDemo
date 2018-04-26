package com.qiuyi.cn.wifitemperaturemagnetic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/12.
 */
public class MagneticUtil implements SensorEventListener{

    // 上下文Context对象
    private Context mContext;
    // MagnetManager对象
    private SensorManager sensorManagerManager;

    public MagneticUtil(Context mContext) {
        this.mContext = mContext;
        sensorManagerManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
    }

    public SensorManager getSensorManagerManager() {
        return sensorManagerManager;
    }

    public void setSensorManagerManager(SensorManager sensorManagerManager) {
        this.sensorManagerManager = sensorManagerManager;
    }

    /**
     * 传感器数据变化时回调
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()){
            //磁场传感器
            case Sensor.TYPE_MAGNETIC_FIELD:
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                Log.d("magnetic", "x："+x+" y："+y+" z："+z);
                break;
        }
    }

    /**
     * 传感器精度变化时回调
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
