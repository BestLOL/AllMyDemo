package com.qiuyi.cn.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018/3/5.
 * 自定义的ViewGroup
 * 1 实现侧滑测单
 * 2 实现在主界面动态添加卡片
 */
public class MyViewGroup extends ViewGroup{

    private static final String TAG = "MyViewGroup";

    //菜单栏
    private View mMenuView;
    //内容栏
    private View mContentView;

    //屏幕的宽
    private int mScreenWidth;
    //屏幕的高
    private int myScreenHeight;
    //菜单栏的宽
    private int mMenuWidth;

    private Scroller mScroller;

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    /**
     * 测量ChildView的宽度和高度，然后根据ChildView的计算结果，设置ViewGroup自己的宽高
     *
     * @param widthMeasureSpec 水平空间规格说明
     * @param heightMeasureSpec 竖直空间规格说明
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //宽度
        mScreenWidth = MeasureSpec.getSize(widthMeasureSpec);
        //高度
        myScreenHeight = MeasureSpec.getSize(heightMeasureSpec);

        //setMeasuredDimension(mScreenWidth,myScreenHeight);
    }

    //重新布局
    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");

        //菜单栏
        mMenuView = getChildAt(1);
        mMenuWidth = mMenuView.getMeasuredWidth();

        //内容栏
        mContentView = getChildAt(0);

        mMenuView.layout(-mMenuWidth,0,0,myScreenHeight);
        mContentView.layout(0,0,mScreenWidth,myScreenHeight);
    }

    private float mPosX,mPosY,mCurPosX,mCurPosY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPosX = event.getX();
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosX = event.getX();
                if((mCurPosX-mPosX)>(mMenuWidth/2)){
                    scrollTo(-mMenuWidth,0);
                }
                if((mPosX-mCurPosX)>(mMenuWidth/2)){
                    scrollTo(0,0);
                }
                mPosX = mCurPosX;
                mPosY = mCurPosY;
                break;
            case MotionEvent.ACTION_UP:

                mScroller.startScroll(getScrollX(),0,-(mMenuWidth + getScrollX()), 0);
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}


/*        *//**
 * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
 *//*
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //子View的宽和高
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        //根据childView的宽高，以及margin，计算出ViewGroup在wrap_content时的宽和高
        for(int i = 0; i < getChildCount();i++){
            View childView = getChildAt(i);
            //得到childView的宽高和margin
            cWidth = childView.getWidth();
            cHeight = childView.getHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
        }*/