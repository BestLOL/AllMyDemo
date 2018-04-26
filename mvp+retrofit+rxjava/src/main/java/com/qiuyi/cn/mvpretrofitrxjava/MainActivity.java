package com.qiuyi.cn.mvpretrofitrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qiuyi.cn.mvpretrofitrxjava.bean.CityBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.ProviceBean;
import com.qiuyi.cn.mvpretrofitrxjava.bean.RegionBean;
import com.qiuyi.cn.mvpretrofitrxjava.http.HttpService;
import com.qiuyi.cn.mvpretrofitrxjava.http.RetrofitClient;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Model.GetAllDistrictImpl;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter.GetAllDistrictPresenter;
import com.qiuyi.cn.mvpretrofitrxjava.ui.Presenter.GetAllDistrictPresenterImpl;
import com.qiuyi.cn.mvpretrofitrxjava.ui.View.MainView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainView {

    private GetAllDistrictPresenter getPresenter;

    private ListView listView;
    private ArrayAdapter adapter;

    private List<String> dataList;

    private int Level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getMsg();

        initClick();
    }

    //click
    private void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (Level){
                    case 1:
                        String provinceName = dataList.get(i);
                        getPresenter.getCityMsg(provinceName);
                        Level = 2;
                        break;
                    case 2:
                        String cityName = dataList.get(i);
                        getPresenter.getReginMsg(cityName);
                        Level = 3;
                        break;
                }
            }
        });
    }

    //得到省信息
    private void getMsg() {
        getPresenter.getProvinceMsg();
    }

    //初始化界面
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        getPresenter = new GetAllDistrictPresenterImpl(new GetAllDistrictImpl(),this);
    }

    @Override
    public void showProvinceAdapter(List<ProviceBean> listProvice) {
        if(listProvice.size()>0){
            dataList.clear();
            for(ProviceBean province:listProvice){
                dataList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCityAdapter(List<CityBean> listCitys) {
        if(listCitys.size()>0){
            dataList.clear();
            for(CityBean city:listCitys){
                dataList.add(city.getName());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showRegionAdapter(List<RegionBean> listRegion) {
        if(listRegion.size()>0){
            dataList.clear();
            for(RegionBean region:listRegion){
                dataList.add(region.getName());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if(Level==1){
            super.onBackPressed();
        }else{
            getPresenter.backUp(Level);
            if(Level == 2){
                Level = 1;
            }else if(Level == 3){
                Level = 2;
            }
        }

    }
}
