package com.qiuyi.cn.mvp.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.qiuyi.cn.mvp.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
public class MainPresenterImpl implements MainPresenter,OnFinishedListener{

    private MainView myView;
    private FindItemsInteractor findItemsInteractor;

    private List<String> stringList = new ArrayList<>();

    public MainPresenterImpl(MainView myView,FindItemsInteractor findItemsInteractor){
        this.myView = myView;
        this.findItemsInteractor = findItemsInteractor;
    }

    @Override
    public void onFinished(List<String> listString) {
        if(myView!=null){
            stringList.clear();

            stringList = listString;

            myView.setRecyclerView(listString);
            myView.hideProgress();

            myView.setRefresh(false);
        }
    }

    @Override
    public void onResume() {
        if(myView!=null){
            myView.showProgress();
        }

        findItemsInteractor.findItems(this);
    }

    @Override
    public void onDestroy() {
        myView = null;
    }

    @Override
    public void onRefresh() {
        onResume();
    }

    @Override
    public void onItemClick(final TextAdapter myAdapter) {
        myAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e("now", "click");

                if(stringList!=null && stringList.size()>0){
                    String name = stringList.get(position);
                    myView.showMessage("name："+name);
                }
            }
        });
    }

}
