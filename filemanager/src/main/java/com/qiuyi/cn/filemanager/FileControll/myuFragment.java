package com.qiuyi.cn.filemanager.FileControll;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qiuyi.cn.filemanager.R;

/**
 * Created by Administrator on 2018/3/25.
 */
public class myuFragment extends BaseFragment{

    @Override
    protected void addView(FrameLayout myFrameLayout) {
        View view = View.inflate(mActivity, R.layout.fragment_upan,null);
        myFrameLayout.addView(view);
    }

    @Override
    protected void initData() {

    }
}
