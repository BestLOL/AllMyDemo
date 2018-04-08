package com.qiuyi.cn.phonesms;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* 获取通话记录短信记录
* */
public class MainActivity extends Activity{

    private Button getPhone;
    private Button getSms;
    private Button sendPhone;
    private Button sendSms;

    private ListView listView;
    private ArrayAdapter<String> adapter;
/*    private List<String> myitem = new ArrayList<>();*/
    private List<String> myPhoneitem = new ArrayList<>();
    private List<String> mySmsitem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initClickEvent();
    }


    //初始化界面
    private void initView() {
        sendPhone = (Button) findViewById(R.id.sendPhone);
        sendSms = (Button) findViewById(R.id.sendSms);
        getPhone = (Button) findViewById(R.id.getPhone);
        getSms = (Button) findViewById(R.id.getSms);

        listView = (ListView) findViewById(R.id.listView);
    }

    //初始化点击事件
    private void initClickEvent() {
        getPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPhoneitem.clear();
                List<String> myPhone = getPhoneMsg();
                showPhone(myPhone);
                savePhoneToFile(myPhone);
            }
        });
        sendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPhoneMsg();
            }
        });
    }

    //展示
    private void showPhone(List<String> myPhone) {
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myPhone);
        listView.setAdapter(adapter);
    }


    //获取手机联系人
    private List<String> getPhoneMsg() {
        ContentResolver cr = getContentResolver();
        //查询id,name
        Cursor cursor = null;
        //查询电话phone
        Cursor phoneCursor = null;
        //查询邮箱email
        Cursor emailCursor = null;

        try {
            //获取手机联系人
            cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            while(cursor.moveToNext()){
                StringBuilder sb = new StringBuilder();
                //获取联系人id
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获取联系人的姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                sb.append("contactID="+id+"\n").append("name="+name+"\n");
                //获取联系人的电话,一个联系人的电话可能有很多
                phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);
                while (phoneCursor.moveToNext()){
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(phoneCursor.isLast()){
                        sb.append("phone="+phoneNumber);
                    }else{
                        sb.append("phone="+phoneNumber+"\n");
                    }
                }

                //获取联系人email
                emailCursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+id,null,null);
                while (emailCursor.moveToNext()){
                    String emailAddress = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    if(emailCursor.isLast()){
                        sb.append("email="+emailAddress);
                    }else{
                        sb.append("email="+emailAddress+"\n");
                    }
                }

                myPhoneitem.add(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(phoneCursor!=null){
                phoneCursor.close();
            }
            if(emailCursor!=null){
                emailCursor.close();
            }
        }
        //返回得到的信息
        return myPhoneitem;
    }


    //保存信息到文件中
    private void savePhoneToFile(List<String> phoneMsg) {
    }

    //批量导入联系人
    private void sendPhoneMsg() {
    }


}
