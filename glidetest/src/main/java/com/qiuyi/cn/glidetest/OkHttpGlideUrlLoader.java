package com.qiuyi.cn.glidetest;

import android.content.Context;
import android.media.JetPlayer;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/2/28.
 * 实现ModelLoader
 * 重新实现了Glide中HttpUrlConnect的过程，只不过是使用okhttp来实现
 */
public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl,InputStream>{

    private OkHttpClient okHttpClient;

    public static class Factory implements ModelLoaderFactory<GlideUrl,InputStream>{

        private OkHttpClient client;

        public Factory(){
        }

        public Factory(OkHttpClient client){
            this.client = client;
        }

        private synchronized OkHttpClient getOkHttpClient(){
            if(client == null){
                client = new OkHttpClient();
            }
            return client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpGlideUrlLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {

        }
    }


    public OkHttpGlideUrlLoader(OkHttpClient client){
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OkHttpFetcher(okHttpClient,model);
    }
}
