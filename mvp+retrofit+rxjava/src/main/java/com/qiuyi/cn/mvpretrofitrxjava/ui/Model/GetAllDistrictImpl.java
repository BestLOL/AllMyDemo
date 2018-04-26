package com.qiuyi.cn.mvpretrofitrxjava.ui.Model;

import android.util.Log;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.RegionBean;
import com.qiuyi.cn.mvpretrofitrxjava.http.HttpService;
import com.qiuyi.cn.mvpretrofitrxjava.http.RetrofitClient;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Interface.GetMsgListener;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter.GetAllDistrictPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/26.
 */
public class GetAllDistrictImpl implements GetAllDistrict{
    //获得所有省份信息
    @Override
    public void getProvinceMsg(final GetMsgListener myListener) {
        RetrofitClient.getInstance()
                .create(HttpService.class)
                .getAllProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProviceBean>>() {
                    @Override
                    public void accept(List<ProviceBean> proviceBeen) throws Exception {
                        myListener.getProvinceList(proviceBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MSG", "province error");
                    }
                });
    }


    @Override
    public void getCityMsg(int provinceid,final GetMsgListener myListener) {
        RetrofitClient.getInstance()
                .create(HttpService.class)
                .getAllCity(provinceid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CityBean>>() {
                    @Override
                    public void accept(List<CityBean> cityBeen) throws Exception {
                        myListener.getCityList(cityBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MSG", "city error");
                    }
                });
    }

    @Override
    public void getRegion(int provinceId, int cityId, final GetMsgListener myListener) {
        RetrofitClient.getInstance()
                .create(HttpService.class)
                .getAllRegion(provinceId,cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RegionBean>>() {
                    @Override
                    public void accept(List<RegionBean> regionBeen) throws Exception {
                        myListener.getRegionList(regionBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("MSG", "region error");
                    }
                });
    }


}
