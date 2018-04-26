package com.qiuyi.cn.mvp.LoginActivity;

/**
 * Created by Administrator on 2018/4/15.
 */
public interface OnLoginFinishedListener {

    /**
     * 登录账号错误
     */
    void onUserNameError();

    /**
     * 登录密码错误
     */
    void onUserPasswordError();

    /**
     * 登录成功
     */
    void onSuccess();
}
