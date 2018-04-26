package com.qiuyi.cn.mvp.main;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
public interface MainView {

    void setRefresh(boolean flag);

    void showProgress();

    void hideProgress();

    void setRecyclerView(List<String> listItems);

    void showMessage(String message);
}
