package com.qiuyi.cn.mvp.LoginActivity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by Administrator on 2018/4/15.
 */
public class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener finishListener) {

        //延迟2秒返回结果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username)){
                    finishListener.onUserNameError();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    finishListener.onUserPasswordError();
                    return;
                }
                finishListener.onSuccess();
            }
        },2000);
    }
}
