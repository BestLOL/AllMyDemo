package com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter;

import android.util.Log;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.RegionBean;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Interface.GetMsgListener;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Model.GetAllDistrict;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Model.GetAllDistrictImpl;
import com.qiuyi.cn.mvpretrofitrxjava.ui.View.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Administrator on 2018/4/26.
 */
public class GetAllDistrictPresenterImpl implements GetAllDistrictPresenter,GetMsgListener{

    private GetAllDistrict getAllDistrict;
    private MainView mainView;

    private List<ProviceBean> proviceBeanList;
    private int provinceId;
    private List<CityBean> cityBeanList;
    private int cityId;
    private List<RegionBean> regionBeenList;

    public GetAllDistrictPresenterImpl(GetAllDistrict getAllDistrict,MainView mainView){
        this.getAllDistrict = getAllDistrict;
        this.mainView = mainView;
    }

    @Override
    public void getProvinceMsg() {
        getAllDistrict.getProvinceMsg(this);
    }

    @Override
    public void getProvinceList(List<ProviceBean> listProvince) {
        proviceBeanList = listProvince;
        mainView.showProvinceAdapter(listProvince);
    }

    @Override
    public void getCityMsg(String provinceName) {
        for(ProviceBean bean : proviceBeanList){
            if(bean.getName().equals(provinceName)){
                provinceId = bean.getId();
                getAllDistrict.getCityMsg(provinceId,this);
            }
        }
    }

    @Override
    public void getCityList(List<CityBean> listCity) {
        cityBeanList = listCity;
        mainView.showCityAdapter(listCity);
    }


    @Override
    public void getReginMsg(String cityName) {
        for(CityBean bean:cityBeanList){
            if(bean.getName().equals(cityName)){
                cityId = bean.getId();
                getAllDistrict.getRegion(provinceId,cityId,this);
            }
        }
    }


    @Override
    public void getRegionList(List<RegionBean> listRegion) {
        regionBeenList = listRegion;
        mainView.showRegionAdapter(listRegion);
    }

    @Override
    public void backUp(int level) {
        if(level == 2){
            getAllDistrict.getProvinceMsg(this);
        }else if(level == 3){
            getAllDistrict.getCityMsg(provinceId,this);
        }
    }

}
