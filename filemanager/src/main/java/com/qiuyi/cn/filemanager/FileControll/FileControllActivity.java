package com.qiuyi.cn.filemanager.FileControll;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;


import com.qiuyi.cn.filemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/25.
 */
public class FileControllActivity extends FragmentActivity implements View.OnClickListener{


    private RadioButton rb_recently,rb_native,rb_upan;
    private FrameLayout controll_fl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        initView();

    }


    //初始化Fragment
    private void changeFragment(Fragment fm) {

        FragmentManager fmManager = getSupportFragmentManager();
        FragmentTransaction transaction = fmManager.beginTransaction();
        transaction.replace(R.id.controll_fl,fm);

        transaction.commit();
    }

    //初始化界面
    private void initView() {
        rb_recently = findViewById(R.id.rb_recently);
        rb_native = findViewById(R.id.rb_native);
        rb_upan = findViewById(R.id.rb_upan);

        controll_fl = findViewById(R.id.controll_fl);

        rb_recently.setOnClickListener(this);
        rb_native.setOnClickListener(this);
        rb_upan.setOnClickListener(this);

        changeFragment(new recentlyFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_recently:
                changeFragment(new recentlyFragment());
                break;
            case R.id.rb_native:
                changeFragment(new nativeFragment());
                break;
            case R.id.rb_upan:
                changeFragment(new myuFragment());
                break;
        }
    }
}
