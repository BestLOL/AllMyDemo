package com.qiuyi.cn.rxjava.request;

import com.qiuyi.cn.rxjava.bean.Transaction;
import com.qiuyi.cn.rxjava.bean.Transaction2;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/4/17.
 */
public interface GetRequest {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=Hello Register")
    Observable<Transaction> getCall_Register();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=Hello Login")
    Observable<Transaction2> getCall_Login();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // 采用Observable<...>接口
    // getCall()是接受网络请求数据的方法
}
