package com.qiuyi.cn.mvp.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.qiuyi.cn.mvp.adapter.TextAdapter;

/**
 * Created by Administrator on 2018/4/16.
 */
public interface MainPresenter {

    void onResume();

    void onDestroy();

    void onRefresh();

    void onItemClick(TextAdapter myAdapter);

}
