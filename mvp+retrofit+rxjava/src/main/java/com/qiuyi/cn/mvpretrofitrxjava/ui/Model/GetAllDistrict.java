package com.qiuyi.cn.mvpretrofitrxjava.ui.Model;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Interface.GetMsgListener;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter.GetAllDistrictPresenterImpl;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */
public interface GetAllDistrict {

    //获得省份信息
    void getProvinceMsg(GetMsgListener myListener);

    //获取城市信息
    void getCityMsg(int provinceid,GetMsgListener myListener);

    //获取区域信息
    void getRegion(int provinceId, int cityId, GetMsgListener myListener);


}
