package com.qiuyi.cn.mvpretrofitrxjava.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qiuyi.cn.mvpretrofitrxjava.BuildConfig;
import com.qiuyi.cn.mvpretrofitrxjava.utils.ConstantUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*    private static RetrofitClient mRetrofitClient = null;
    public static RetrofitClient getNewInstance(){
        if(mRetrofitClient==null){
            synchronized (RetrofitClient.class){
                if (mRetrofitClient==null){
                    mRetrofitClient = new RetrofitClient();
                }
            }
        }
        return mRetrofitClient;
    }*/
/**
 * Created by Administrator on 2018/4/26.
 *
 *
 */
public class RetrofitClient {

    private Retrofit mRetrofit;

    private RetrofitClient(){
        mRetrofit = createRetrofit();
    }
    private static class RetrofitClientHolder{
        private static final RetrofitClient instance = new RetrofitClient();
    }
    public static final RetrofitClient getInstance(){
        return RetrofitClientHolder.instance;
    }

    //创建Retrofit
    private Retrofit createRetrofit() {
        //初始化OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(9, TimeUnit.SECONDS)//连接超时9秒
                .readTimeout(10,TimeUnit.SECONDS);//读取超时10秒
        if(BuildConfig.DEBUG){
            //判断是否为debug模式，是的话添加日志拦截器
            // 如果为 debug 模式，则添加日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        //返回Retrofit对象
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.http_Uri)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return mRetrofit;
    }


    //创建相应的服务接口
    public <T> T create(Class<T> httpService){
        //判断不为空
        if(httpService==null){
            throw new NullPointerException("httpService is null");
        }
        return mRetrofit.create(httpService);
    }

}
