package com.qiuyi.cn.sharedpreferencedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        SharedPreferenceUtiltwo.setObjectMessage(getApplicationContext(),"123",true);
        boolean myboolean = SharedPreferenceUtiltwo.getObjectMessage(getApplicationContext(),"123",false);*/
        SharedPreferenceUtiltwo.setObjectMessage(getApplicationContext(),"123","你好");
        String myString = SharedPreferenceUtiltwo.getObjectMessage(getApplicationContext(),"123","");
/*        SharedPreferenceUtiltwo.setObjectMessage(getApplicationContext(),"123",123);
        int myInt = SharedPreferenceUtiltwo.getObjectMessage(getApplicationContext(),"123",0);*/
        /*Log.e("myName-",myboolean+"-"+myString+"-"+myInt);*/
        Log.e("myName-",myString);
    }
}
