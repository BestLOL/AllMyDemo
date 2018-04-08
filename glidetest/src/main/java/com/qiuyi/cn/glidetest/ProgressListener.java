package com.qiuyi.cn.glidetest;

/**
 * Created by Administrator on 2018/2/28.
 * 进度监听回调工具
 */
public interface ProgressListener {

    //在拦截器中实现进度监听回调
    void onProgress(int progress);
}
