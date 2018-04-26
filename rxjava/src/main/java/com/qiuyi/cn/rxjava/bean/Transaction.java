package com.qiuyi.cn.rxjava.bean;

import android.util.Log;

/**
 * Created by Administrator on 2018/4/17.
 */
public class Transaction {

    private int status;

    private content content;

    public class content{
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.e("RxJava1", content.out );
    }
}
