package com.qiuyi.cn.mvp.main;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
public class FindItemInteractorImpl implements FindItemsInteractor{

    @Override
    public void findItems(final OnFinishedListener finishedListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               finishedListener.onFinished(createList());
            }
        },2000);
    }


    private List<String> createList() {
        List<String> listString = new ArrayList<>();
        for(int i=0;i<30;i++){
            listString.add("Item："+i);
        }
        return listString;
    }

}
