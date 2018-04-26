package com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter;

/**
 * Created by Administrator on 2018/4/26.
 */
public interface GetAllDistrictPresenter {

    //获取省份信息
    void getProvinceMsg();

    //获取城市信息
    void getCityMsg(String provinceName);

    //获取区域信息
    void getReginMsg(String cityName);

    //返回键
    void backUp(int level);
}
