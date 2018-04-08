package com.qiuyi.cn.myviewgroup_recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/3/19.
 */
public class MyViewGroup extends ViewGroup{

    //隐私模块
    private View mSecret;
    //下拉刷新
    private View mRefresh;
    //正常显示模块
    private LinearLayout mShowBlock;

    //屏幕高度
    public int mScreenHeight;
    //屏幕宽度
    public int mScreenWidth;
    //隐私模块高度
    public int mSecretHeight;
    //下拉刷新模块高度
    public int mRefreshHeight;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //屏幕宽度
        mScreenWidth = MeasureSpec.getSize(widthMeasureSpec);

        //屏幕高度
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //隐私模块
        mSecret = getChildAt(0);
        mSecretHeight = mSecret.getMeasuredHeight();

        //刷新模块
        mRefresh = getChildAt(1);
        mRefreshHeight = mRefresh.getMeasuredHeight();

        //显示模块
        mShowBlock = (LinearLayout) getChildAt(2);
        mShowBlock.getChildAt(0);

        //隐私模块
        mSecret.layout(0,-(mRefreshHeight+mSecretHeight),mScreenWidth,0);
        //刷新模块
        mRefresh.layout(0,-mRefreshHeight,mScreenWidth,0);
        //显示模块
        mShowBlock.layout(0,1,mScreenWidth,mScreenHeight);
    }



}
