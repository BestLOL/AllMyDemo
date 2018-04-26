package com.qiuyi.cn.wifitemperaturemagnetic;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */
public class WifiUtil {
    // 上下文Context对象
    private Context mContext;
    // WifiManager对象
    private WifiManager mWifiManager;

    public WifiUtil(Context mContext) {
        this.mContext = mContext;
        mWifiManager = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取当前手机所连接的wifi信息
     */
    public WifiInfo getCurrentWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * 搜索附近的热点信息，并返回所有热点为信息的SSID集合数据
     */
    public List<ScanResult> getScanWifiResult() {
        // 扫描的热点数据
        List<ScanResult> resultList;
        // 开始扫描热点
        mWifiManager.startScan();
        resultList = mWifiManager.getScanResults();
/*        ArrayList<String> ssids = new ArrayList<String>();
        if (resultList != null) {
            for (ScanResult scan : resultList) {
                ssids.add(scan.SSID);// 遍历数据，取得ssid数据集
            }
        }*/
        return resultList;
    }

}
