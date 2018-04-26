package com.qiuyi.cn.mvp.LoginActivity;

/**
 * Created by Administrator on 2018/4/15.
 *
 */
public interface LoginView {

    /**
     * 展示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 用户名错误展示
     */
    void showUserNameError();

    /**
     * 密码错误展示
     */
    void showUserPasswordError();

    /**
     * 前往主页面
     */
    void navigateToHome();
}
