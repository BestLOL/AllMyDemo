package com.qiuyi.cn.glass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/31.
 * 选择五种不同的模糊效果
 */
public class SelectGlass extends Activity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.radioG)
    RadioGroup group;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.radio5)
    RadioButton radio5;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_main);
        ButterKnife.bind(this);


        group.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id){
            case R.id.radio1:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.radio2:
                intent = new Intent(this,ImageBlur2.class);
                startActivity(intent);
                break;
            case R.id.radio3:
                intent = new Intent(this,ImageBlur3.class);
                startActivity(intent);
                break;
            case R.id.radio4:
                intent = new Intent(this,ImageBlur4.class);
                startActivity(intent);
                break;
            case R.id.radio5:
                intent = new Intent(this,ImageBlur5.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
