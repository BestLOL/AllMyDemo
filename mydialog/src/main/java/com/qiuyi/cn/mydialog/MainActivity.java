package com.qiuyi.cn.mydialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;


//一些自己写的dialog样式以及popWindow样式
public class MainActivity extends Activity implements View.OnClickListener{

    Button button1,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                //弹出提示框
                new CommomDialog(this, R.style.dialog, "确定删除此信息", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            Toast.makeText(getApplicationContext(),"点击确定",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }).setTitle("提示").show();
                break;
            case R.id.button2:
                new MenuPopupWindow(this, new MenuPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position) {

                    }
                }).showAsDropDown(button2,-200,40);
                break;
            case R.id.button3:
                new ShareDialog(MainActivity.this, R.style.dialog, new ShareDialog.OnItemClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int position) {
                        dialog.dismiss();
                        switch (position){
                            case 1:
                                Utils.toast(MainActivity.this,"微信好友");
                                break;
                            case 2:
                                Utils.toast(MainActivity.this,"朋友圈");
                                break;
                            case 3:
                                Utils.toast(MainActivity.this,"QQ");
                                break;
                            case 4:
                                Utils.toast(MainActivity.this,"微博");
                                break;
                        }
                    }
                }).show();

                break;
            case R.id.button4:
                new MyPopupWindow(this, new MyPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position) {
                        popupWindow.dismiss();
                    }
                }).showAtLocation(button4, Gravity.CENTER,0,0);
                break;

            default:
                break;
        }
    }
}
