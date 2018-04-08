package com.qiuyi.cn.allmydemo;

import android.content.IntentFilter;
import android.util.Base64;

/**
 * Created by Administrator on 2018/1/28.
 * 功能模块
 */
public class BaseUtilImpl implements BaseUtil{
    @Override
    public IntentFilter updateIntentFilter(String[] myBroads) {
        IntentFilter intentFilter = new IntentFilter();
        for(String myBroad : myBroads){
            intentFilter.addAction(myBroad);
        }
        return intentFilter;
    }
}
