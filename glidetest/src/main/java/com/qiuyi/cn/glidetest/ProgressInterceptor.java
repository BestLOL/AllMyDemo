package com.qiuyi.cn.glidetest;

import android.graphics.Matrix;
import android.widget.LinearLayout;

import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.request.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/2/28.
 * OkHttp的拦截器
 * 在MyGlideModule中启动这个拦截器
 */
public class ProgressInterceptor implements Interceptor{

    public static final Map<String,ProgressListener> LISTENER_MAP = new HashMap<>();

    //添加下载监听
    public static void addListener(String url,ProgressListener listener){
        LISTENER_MAP.put(url,listener);
    }

    //移除下载监听
    public static void removeListener(String url){
        LISTENER_MAP.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这是一个没有任何逻辑的空拦截器
        okhttp3.Request request = chain.request();
        Response response = chain.proceed(request);

        //在拦截器中使用计算下载进度逻辑
        String url = request.url().toString();
        ResponseBody body = response.body();

        Response newResponse = response.newBuilder().body(new ProgressResponseBody(url,body)).build();

        return newResponse;
    }
}
