package com.qiuyi.cn.mvpretrofitrxjava.http;

/**
 * Created by Administrator on 2018/4/26.
 */
public class HttpServiceClient {

    private static HttpServiceClient httpServiceClient = null;
    private RetrofitClient mRetrofitClient;

    private HttpServiceClient(){
        mRetrofitClient = RetrofitClient.getInstance();
    };

    public static HttpServiceClient getInstance(){
        if(httpServiceClient==null){
            synchronized (HttpServiceClient.class){
                if(httpServiceClient==null){
                    httpServiceClient = new HttpServiceClient();
                }
            }
        }
        return httpServiceClient;
    }

    //Retrofit+Rxjava
    public <T> void startHttpService(Class<T> service){
        mRetrofitClient.create(service);
    }

}
