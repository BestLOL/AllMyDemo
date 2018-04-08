package com.qiuyi.cn.phonesms.Info;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.qiuyi.cn.phonesms.Bean.SmsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 * 短信的备份与写入本地
 */
public class SmsInfo {

    private Context context;

    public SmsInfo(Context context) {
        this.context = context;
    }

    //获取所有短信消息
    public List<SmsBean> getSmsInfo() {
        String SMS_URI_ALL  = "content://sms/";
        String[] projection = new String[]{"_id", "address", "person",
                "body", "date", "type"};
        Uri uri = Uri.parse(SMS_URI_ALL);
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, "date desc");

        List<SmsBean> smsBeanList = new ArrayList<>();
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("person"));//发送人
            name = notnullMsg(name);

            String phoneNumber = cursor.getString(cursor.getColumnIndex("address"));//号码
            phoneNumber = notnullMsg(phoneNumber);

            String smsBody = cursor.getString(cursor.getColumnIndex("body"));//内容
            smsBody = notnullMsg(smsBody);

            long dateLong = cursor.getLong(cursor.getColumnIndex("date"));

            int type = cursor.getInt(cursor.getColumnIndex("type"));
/*            String typeString = "";
            if(type==1){
                typeString = "接收";
            }else if(type==2){
                typeString = "发送";
            }*/
            SmsBean mySmsMsg = new SmsBean();
            mySmsMsg.setName(name);
            mySmsMsg.setPhoneNumber(phoneNumber);
            mySmsMsg.setSmsbody(smsBody);
            mySmsMsg.setDate(dateLong);
            mySmsMsg.setType(type);
            smsBeanList.add(mySmsMsg);
        }
        return smsBeanList;
/*            if (cursor.moveToFirst()) {
                int nameColumn = cursor.getColumnIndex("person"); //发送人
                int phoneNumberColumn = cursor.getColumnIndex("address");  //号码
                int smsbodyColumn = cursor.getColumnIndex("body");  //内容
                int dateColumn = cursor.getColumnIndex("date");  //时间
                int typeColumn = cursor.getColumnIndex("type");  //接收还是发送

                do{
                    String name = cursor.getString(nameColumn);
                    String phoneNumber = cursor.getString(phoneNumberColumn);
                    String smsbody = cursor.getString(smsbodyColumn);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
                    String date = dateFormat.format(d);
                    int typeId = cursor.getInt(typeColumn);
                    String type;
                    if(typeId == 1){
                        type = "接收";
                    } else if(typeId == 2){
                        type = "发送";
                    } else {
                        type = "";
                    }
                    String smsmsg = name+":"+phoneNumber+":"+smsbody+":"+date+":"+type+"\n";
                    con.add(smsmsg);
                    if(smsbody == null) smsbody = "";
                }while(cursor.moveToNext());
            } else {
                smsBuilder.append("no result!");
            }

            smsBuilder.append("getSmsInPhone has executed!");
        } catch(SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }
        return smsBuilder.toString();*/
    }


    //将短信记录写入本地
    public void sendToSDCardSms(List<SmsBean> listSms) {
        String SMS_URI_ALL  = "content://sms";
        Uri uri = Uri.parse(SMS_URI_ALL);
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        for(SmsBean mySms:listSms){
            values.clear();
            values.put("person", mySms.getName());
            values.put("address", mySms.getPhoneNumber());
            values.put("type", mySms.getType());
            values.put("date", mySms.getDate());
            values.put("body", mySms.getSmsbody());
            resolver.insert(uri,values);
        }
        Log.e("SMSMsg","短信插入成功");
    }


    //判断是否为空，为空返回""
    public String notnullMsg(String str){
        if(str == null){
            str = "";
        }
        return str;
    }


    //将默认的SMS app还原
/*    private void removeDefaultSms() {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultSmsApp);
        startActivity(intent);
    }
    private String defaultSmsApp;
    //将本APP设为默认的SMS app,添加完短信后再调回来
    private void setDefaultSms(Context context) {
        //获取默认Sms APP的包名并保存
        defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(context);
        //将自己的APP改为默认SMS APP应用
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
        startActivity(intent);
    }*/
}
