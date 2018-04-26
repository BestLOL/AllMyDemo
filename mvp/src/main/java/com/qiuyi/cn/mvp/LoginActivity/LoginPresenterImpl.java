package com.qiuyi.cn.mvp.LoginActivity;

/**
 * Created by Administrator on 2018/4/15.
 */
public class LoginPresenterImpl implements LoginPresenter,OnLoginFinishedListener{

    private LoginView loginView;
    private LoginInteractor loginInteractor;


    public LoginPresenterImpl(LoginView loginView,LoginInteractor loginInteractor){
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(loginView!=null){
            loginView.showProgress();
        }
        if(loginInteractor!=null){
            loginInteractor.login(username,password,this);
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUserNameError() {
        if(loginView!=null){
            loginView.showUserNameError();
            loginView.hideProgress();
        }

    }

    @Override
    public void onUserPasswordError() {
        if(loginView!=null){
            loginView.showUserPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView!=null){
            loginView.navigateToHome();
        }
    }
}
