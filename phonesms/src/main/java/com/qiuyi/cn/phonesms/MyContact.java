package com.qiuyi.cn.phonesms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.provider.Telephony;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiuyi.cn.phonesms.Bean.CallBean;
import com.qiuyi.cn.phonesms.Bean.ContactBean;
import com.qiuyi.cn.phonesms.Bean.SmsBean;
import com.qiuyi.cn.phonesms.Info.CallInfo;
import com.qiuyi.cn.phonesms.Info.PhoneInfo;
import com.qiuyi.cn.phonesms.Info.SmsInfo;
import com.qiuyi.cn.phonesms.Util.MyFileHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/1/29.
 * 获取联系人所有资料，并存入文件夹中
 */
public class MyContact extends Activity{

    private static final String TAG = "MainActivity";
    private static final String PHONE_FILE = "Contacts.txt";
    private static final String CALL_FILE = "CallMsg.txt";
    private static final String SMS_FILE = "SmsMsg.txt";

    //手机联系人获取与导入方法
    private PhoneInfo phoneInfo;
    //通话记录获取与导入方法
    private CallInfo callInfo;
    //手机短信获取与导入方法
    private SmsInfo smsInfo;
    //文件操作方法
    private MyFileHelper myFileHelper;
    //线程池
    private ExecutorService executorService;
    private ProgressDialog dialog_wait;

    private Button getPhone;
    private Button sendPhone;
    private Button getCallLog;
    private Button sendCallLog;
    private Button getSms;
    private Button sendSms;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initOnClick();
    }

    //初始化线程与UI
    private void init() {
        //等待框
        dialog_wait = new ProgressDialog(this);
        dialog_wait.setCancelable(false);
        dialog_wait.setCanceledOnTouchOutside(false);
        //线程
        executorService = Executors.newCachedThreadPool();//30大小的线程池
    }

    private void initOnClick() {
        //文件的存取
        myFileHelper = new MyFileHelper(getApplicationContext());
        //联系人的存取
        phoneInfo = new PhoneInfo(getApplicationContext());
        //通话记录的存取
        callInfo = new CallInfo(getApplicationContext());
        //短信记录的存取
        smsInfo = new SmsInfo(getApplicationContext());

        getPhone = (Button) findViewById(R.id.getPhone);
        sendPhone = (Button) findViewById(R.id.sendPhone);
        getCallLog = (Button) findViewById(R.id.getCallLog);
        sendCallLog = (Button) findViewById(R.id.sendCallLog);
        getSms = (Button) findViewById(R.id.getSms);
        sendSms = (Button) findViewById(R.id.sendSms);

        //获取手机联系人所有信息，写到文件夹中存储
        getPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String contacts = phoneInfo.getContactInfo();
                    //将JSON数据写入本地文件
                    myFileHelper.writeFileToSDCard(contacts,PHONE_FILE);
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
                List<ContactBean> listBean = getPhoneFile();

                for(ContactBean myBean:listBean){
                    phoneInfo.init(myBean);
                }
                if(listBean!=null){
                    phoneInfo.sendToSDCardPhone(listBean, getApplicationContext());
                }
            }
        });
        //获取手机通话记录
        getCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CallBean> listCall = callInfo.getCallInfo();
                if(listCall!=null){
                    Gson gson = new Gson();
                    String callMsg = gson.toJson(listCall);
                    Log.e("CallMsg",callMsg);
                    myFileHelper.writeFileToSDCard(callMsg,CALL_FILE);
                }
            }
        });
        //将文件夹中的手机通话记录数据添加到手机中
        sendCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CallBean> listCall = getCallFile();
                if(listCall!=null){
                    callInfo.sendToSDCardCall(listCall,getApplicationContext());
                }
            }
        });

        //获取手机短信
        getSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SmsBean> smsList = smsInfo.getSmsInfo();
                if(smsList!=null){
                    Gson gson = new Gson();
                    String smsMsg = gson.toJson(smsList);
                    Log.e("SMSMsg",smsMsg);
                    myFileHelper.writeFileToSDCard(smsMsg,SMS_FILE);
                }
            }
        });
        //将文件夹中的手机短信记录数据添加到手机中
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<SmsBean> listSms = getSmsFile();
                if(listSms!=null){
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            smsInfo.sendToSDCardSms(listSms);
                        }
                    });
                }
            }
        });
    }




    //获取本地存储的短信记录文件
    private List<SmsBean> getSmsFile() {
        File file = myFileHelper.getSDCardFile(SMS_FILE);
        String myCallStr = myFileHelper.readFileToSDCard(file);
        if(myCallStr!=null){
            Gson gson = new Gson();
            List<SmsBean> mySmsList = gson.fromJson(myCallStr,new TypeToken<List<SmsBean>>(){}.getType());
            return mySmsList;
        }
        return null;
    }


    //获取本地存储的通话记录文件
    private List<CallBean> getCallFile(){
        File file = myFileHelper.getSDCardFile(CALL_FILE);
        String myCallStr = myFileHelper.readFileToSDCard(file);
        if(myCallStr!=null){
            Gson gson = new Gson();
            List<CallBean> myCallList = gson.fromJson(myCallStr,new TypeToken<List<CallBean>>(){}.getType());
            return myCallList;
        }
        return null;
    }

    //获取本地存储的联系人文件
    private List<ContactBean> getPhoneFile() {
        File file = myFileHelper.getSDCardFile(PHONE_FILE);
        String myPhoneStr = myFileHelper.readFileToSDCard(file);
        if(myPhoneStr!=null){
            try {
                JSONObject jsonObject = new JSONObject(myPhoneStr);
                Gson gson = new Gson();
                List<ContactBean> myList = new ArrayList<>();
                for(int i=0;i<jsonObject.length();i++){
                    JSONObject object = (JSONObject) jsonObject.get("contact"+i);
                    ContactBean contactBean = gson.fromJson(String.valueOf(object),ContactBean.class);
                    myList.add(contactBean);
                }
                return myList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }




}
