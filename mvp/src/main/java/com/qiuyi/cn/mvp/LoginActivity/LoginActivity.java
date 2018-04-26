package com.qiuyi.cn.mvp.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qiuyi.cn.mvp.main.MainActivity;
import com.qiuyi.cn.mvp.R;
import com.qiuyi.cn.myloadingdialog.LoadingDialog;

/**
 * Created by Administrator on 2018/4/15.
 */
public class LoginActivity extends Activity implements LoginView,View.OnClickListener{

    private EditText et_username,et_userpassword;
    private Button bt_login;

    private LoadingDialog myLoadingDialog;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_username);
        et_userpassword = (EditText) findViewById(R.id.et_userpassword);
        bt_login = (Button) findViewById(R.id.bt_login);
        myLoadingDialog = new LoadingDialog.Builder(this)
                .setMessage("正在登陆：")
                .setCancelable(false)
                .setCancelOutside(false)
                .create();
        loginPresenter = new LoginPresenterImpl(this,new LoginInteractorImpl());
        bt_login.setOnClickListener(this);
    }


    @Override
    public void showProgress() {
        myLoadingDialog.show();
    }

    @Override
    public void hideProgress() {
        myLoadingDialog.dismiss();
    }

    @Override
    public void showUserNameError() {
        et_username.setError(getString(R.string.username_error));
        Toast.makeText(this,"对不起账户名错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserPasswordError() {
        et_userpassword.setError(getString(R.string.password_error));
        Toast.makeText(this,"对不起密码错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        //退出当前登录界面
        finish();
    }

    @Override
    public void onClick(View view) {
        loginPresenter.validateCredentials(et_username.getText().toString(),et_userpassword.getText().toString());
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
