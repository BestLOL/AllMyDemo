package com.qiuyi.cn.filemanager.FileControll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiuyi.cn.filemanager.R;

/**
 * Created by Administrator on 2018/3/25.
 */
public abstract class BaseFragment extends Fragment implements MyScrollerListener{

    //获取activity
    public Activity mActivity;
    //创建自己的布局
    public View mRootView;
    //Group
    public MyViewGroup myViewGroup;
    private ImageView myLock;
    private TextView myDropRefresh;
    private FrameLayout myFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

/*        if(mRootView!=null){
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            return mRootView;
        }*/

        View view = inflater.inflate(R.layout.pager_baserefresh_layout,null);
        myViewGroup = view.findViewById(R.id.myViewGroup);
        myLock = view.findViewById(R.id.lock);
        myDropRefresh = view.findViewById(R.id.tv_dropRefresh);
        myFrameLayout = view.findViewById(R.id.myFrameLayout);

        addView(myFrameLayout);

        mRootView = view;


        return mRootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void addView(FrameLayout myFrameLayout);
    protected abstract void initData();

    @Override
    public void setViewDispaly(float distance, int refreshHeight, int secretHeight) {
        //松开时的滑动变化
        if(0<distance && distance<=refreshHeight){
            //显示下拉刷新
            myViewGroup.scrollTo(0,0);
        }
        if(refreshHeight<distance && distance<=(refreshHeight+(secretHeight/5.0f))){
            //显示松开刷新
            myViewGroup.scrollTo(0,-refreshHeight);
            myDropRefresh.setText("○正在刷新○");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myViewGroup.scrollTo(0,0);
                }
            }).start();

        }
        if((refreshHeight+(secretHeight/5.0f))<distance && distance<=(secretHeight+refreshHeight)){
            //显示松开进入私密模块
            myViewGroup.scrollTo(0,0);
/*            Intent intent = new Intent(mActivity, SecretActivity.class);
            mActivity.startActivity(intent);*/
        }
    }

    @Override
    public void setLockDisplay(float distance, int refreshHeight, int secretHeight) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myLock.getLayoutParams();

        //正在滑动时的UI变化
        if(0<distance && distance<=refreshHeight){
            myDropRefresh.setText("↓↓下拉刷新");
            //显示下拉刷新
        }
        if(refreshHeight<distance && distance<=(refreshHeight+(secretHeight/5.0f))){
            //显示松开刷新
            myDropRefresh.setText("↑↑松开刷新");
        }
        if((refreshHeight+(secretHeight/5.0f))<distance && distance<=(secretHeight+refreshHeight)){
            //显示松开进入私密模块
            //SecretView显示一个小锁
            myDropRefresh.setText("↑进入私密模块↑");
        }

        float realDistance = distance - refreshHeight;
        if(realDistance>0){
            params.topMargin = (int) (secretHeight -realDistance + 150);
            myLock.setLayoutParams(params);
        }
    }
}
