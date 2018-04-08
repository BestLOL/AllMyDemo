package com.qiuyi.cn.glidetest;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/2/28.
 * 1 实现Glide中的组件替换
 * 2 这里实现替换之后，还需要在AndroidManifest.xml文件中实现配置
 * 这样就替换完毕了
 */
public class MyGlideModule implements GlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //这里是修改Glide基础设置,通过一系列重写的方法
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //这里是替换Glide中的组件,通过重写组件的方式

        //启动okhttp拦截器
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();

        //这里是替换Glide中的组件,通过重写组件的方式
        glide.register(GlideUrl.class, InputStream.class,new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }
}
