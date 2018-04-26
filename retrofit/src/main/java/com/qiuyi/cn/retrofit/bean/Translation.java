package com.qiuyi.cn.retrofit.bean;

/**
 * Created by Administrator on 2018/4/17.
 * 第一步：创建服务器返回数据类
 * 也就是创建一个json数据类型的接收
 */
public class Translation {

    private int status;

    private content content;

    private class content{
        private String from;//原文内容类型
        private String to;//译文翻译类型
        private String vendor;//来源
        private String out;//译文内容
        private int mycuowu;//请求成功时取0

        @Override
        public String toString() {
            return "content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", mycuowu=" + mycuowu +
                    '}';
        }
    }

    //定义输出返回数据的方法

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
