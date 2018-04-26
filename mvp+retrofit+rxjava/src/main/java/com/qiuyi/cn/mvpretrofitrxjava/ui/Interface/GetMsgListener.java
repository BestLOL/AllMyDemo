package com.qiuyi.cn.mvpretrofitrxjava.ui.Interface;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.RegionBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */
public interface GetMsgListener {
    void getProvinceList(List<ProviceBean> listProvince);
    void getCityList(List<CityBean> listCity);
    void getRegionList(List<RegionBean> listRegion);
}
