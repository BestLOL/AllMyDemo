package com.qiuyi.cn.mvpretrofitrxjava.http;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.RegionBean;
import com.qiuyi.cn.mvpretrofitrxjava.utils.ConstantUtil;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/4/25.
 * Retrofit的应用
 */
public interface HttpService {

    @GET("/api/china")
    Observable<List<ProviceBean>> getAllProvince();

/*    @HTTP(method="GET",path="/api/china/{id}")
    Observable<List<CityBean>> getAllCity(@Path("id") int id);*/

    @GET("/api/china/{id}")
    Observable<List<CityBean>> getAllCity(@Path("id") int id);

    @GET("/api/china/{provinceId}/{cityId}")
    Observable<List<RegionBean>> getAllRegion(@Path("provinceId") int provinceId,@Path("cityId") int cityId);
}
