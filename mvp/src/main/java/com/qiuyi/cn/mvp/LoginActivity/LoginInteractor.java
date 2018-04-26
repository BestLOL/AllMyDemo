package com.qiuyi.cn.mvp.LoginActivity;

import android.view.View;

/**
 * Created by Administrator on 2018/4/15.
 */
public interface LoginInteractor {
    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @param finishListener 登录完毕，结果显示
     */
    void login(String username, String password, OnLoginFinishedListener finishListener);
}
