package com.qiuyi.cn.phonebkrt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qiuyi.cn.phonebkrt.Permission.ConstantPermission;
import com.qiuyi.cn.phonebkrt.Permission.GetPermission;
import com.qiuyi.cn.phonebkrt.Permission.PermissionUtil;
import com.qiuyi.cn.phonebkrt.phoneContacts.ContactBean;
import com.qiuyi.cn.phonebkrt.phoneContacts.PhoneInfo;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhoneActivity extends Activity{

    public static final String PHONE_FILE = "Contacts.txt";

    private GetPermission getPermission = PermissionUtil.getInstance();

    //获取手机联系人，导入手机联系人
    private Button getPhone;
    private Button sendPhone;

    //线程池
    private ExecutorService executorService;
    private ProgressDialog dialog_wait;

    //手机联系人获取与导入方法
    private PhoneInfo phoneInfo;
    //文件操作方法
    private MyFileHelper myFileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        //申请权限
        getPermission.getPermission(this, ConstantPermission.ACTION_PERMISSION);

        //初始化等待框+线程池+UI+基本方法
        init();

        //注册点击事件
        initClick();

    }


    //初始化线程与UI
    private void init() {
        //UI
        getPhone = (Button) findViewById(R.id.getPhone);
        sendPhone = (Button) findViewById(R.id.sendPhone);

        //等待框
        dialog_wait = new ProgressDialog(this);
        dialog_wait.setCancelable(false);
        dialog_wait.setCanceledOnTouchOutside(false);
        //线程
        executorService = Executors.newCachedThreadPool();//30大小的线程池


        //文件的存取
        myFileHelper = new MyFileHelper(getApplicationContext());
        //联系人的存取
        phoneInfo = new PhoneInfo(getApplicationContext());
    }

    //点击事件
    private void initClick() {

        //获取手机联系人所有信息，写到文件夹中存储
        getPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String contacts = phoneInfo.getContactInfo();
                    //将JSON数据写入本地文件
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            myFileHelper.writeFileToSDCard(contacts,PHONE_FILE);
                        }
                    });
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        //将文件夹中的数据添加到手机通讯录中
        sendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<ContactBean> listBean = myFileHelper.getPhoneFile();

                if(listBean!=null){
                    for(ContactBean myBean:listBean){
                        if(myBean==null){
                            continue;
                        }
                        phoneInfo.init(myBean);
                    }
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            phoneInfo.sendToSDCardPhone(listBean, getApplicationContext());
                        }
                    });

                }
            }
        });
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ConstantPermission.PERMISSION_GET: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    //申请失败，可以继续向用户解释。
                    Toast.makeText(this, "申请失败", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
