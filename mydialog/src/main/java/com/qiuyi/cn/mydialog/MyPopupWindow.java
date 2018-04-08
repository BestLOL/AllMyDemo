package com.qiuyi.cn.mydialog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by Administrator on 2016/10/24.
 * popupWindow
 */

public class MyPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context context;
    private View view;
    private OnItemClickListener mListener;
    private float mPosX, mPosY, mCurPosX, mCurPosY;//记录mListViewDevice 滑动的位置
    private static final int FLING_MIN_DISTANCE = 20;//mListView  滑动最小距离
    private static final int FLING_MIN_VELOCITY = 200;//mListView 滑动最大速度


    //按钮组父控件布局属性
    private RelativeLayout.LayoutParams params;
    private LinearLayout layout_container;
    private int height;


    public MyPopupWindow(final Activity context, OnItemClickListener mListener){
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.layout_popup_my, null);
        this.mListener = mListener;

        this.setContentView(view);
        //设置两个MATCH_PARENT能够实现下面的组件随着手势移动
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(false);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));


        //这是一个LinerLayout
        layout_container = view.findViewById(R.id.layout_container);
        //这样之后才能获取大小
        layout_container.measure(0,0);
        height = layout_container.getMeasuredHeight();
        //获取字控件在父控件中的位置
        params = (RelativeLayout.LayoutParams) layout_container.getLayoutParams();
        params.bottomMargin = -height;
        //然后在子控件中设置子控件应该放在哪
        layout_container.setLayoutParams(params);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //return listViewGesture.onTouchEvent(motionEvent);
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = motionEvent.getX();
                        mPosY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = motionEvent.getX();
                        mCurPosY = motionEvent.getY();

                        showButtonByStep(mPosY-mCurPosY);
                        //showAnimation();
                        Toast.makeText(context,"你好",Toast.LENGTH_SHORT).show();
                        //向上滑动
/*                        if(mCurPosY - mPosY < 0){
                            showButtonByStep(mPosY - mCurPosY);

                        }
                        //向下滑动
                        if(mCurPosY - mPosY > 0){

                        }*/
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向下滑動

                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向上滑动

                            float dy = mCurPosY - mPosY;
/*                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            lp.gravity = Gravity.CENTER;
                            View newView = LayoutInflater.from(context).inflate(R.layout.,null);
                            newView.setLayoutParams(lp);
                            myAddView.addView(newView);*/

                        }
                        break;
                }
                return true;
            }
        });

        ImageView myhead = view.findViewById(R.id.iv_avatar);
        ImageView myBackground = view.findViewById(R.id.iv_blur);
        Glide.with(context).load(R.drawable.newtimg2)
                .bitmapTransform(new BlurTransformation(context, 25),new CenterCrop(context))
                .into(myBackground);

        Glide.with(context).load(R.drawable.mypic)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(myhead);
        myhead.setOnClickListener(this);
    }

    //滑动动画
    private void showAnimation() {
        layout_container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.bottom_in));
    }

    //滑动事件
    private void showButtonByStep(float v) {
        if(params.bottomMargin<=0 && params.bottomMargin>=(-height)) {
            params.bottomMargin = (int) (params.bottomMargin + v);

            if(params.bottomMargin>0){
                params.bottomMargin = 0;
            }
            if(params.bottomMargin<(-height)){
                params.bottomMargin = -height;
            }

            layout_container.setLayoutParams(params);
        }
    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.iv_avatar:
                 mListener.onItemClick(this, 1);
                 break;
         }
    }


    public interface  OnItemClickListener{
          void onItemClick(PopupWindow popupWindow, int position);
    }

}
