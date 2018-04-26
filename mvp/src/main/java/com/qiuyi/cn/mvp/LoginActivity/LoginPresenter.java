package com.qiuyi.cn.mvp.LoginActivity;

/**
 * Created by Administrator on 2018/4/15.
 */
public interface LoginPresenter {

    /**
     * 验证凭据
     * @param username
     * @param password
     */
    void validateCredentials(String username,String password);

    /**
     * 销毁当前页面
     */
    void onDestroy();
}
