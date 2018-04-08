package com.qiuyi.cn.batteryedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Password.OnFinishListener{

    private Password password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
       password = (Password) findViewById(R.id.password_view);
       initListener();
    }

    private void initListener() {
        password.setOnFinishListener(this);
    }


    @Override
    public void setOnPasswordFinished() {
        if(password.getPasswordLength() == password.getOriginText().length()){
            Log.e("mima", "密码"+password.getOriginText());

        }
    }
}
