package com.qiuyi.cn.phonebkrt;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.qiuyi.cn.phonebkrt.phoneContacts.ContactBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 * 我的文件操作类
 */
public class MyFileHelper{

    private Context context;

    public MyFileHelper(Context context) {
        this.context = context;
    }

    //是否有外部存储卡
    public boolean isSDCardState(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

    //获取本地存储联系人文件
    public File getSDCardFile(String fileName){
        File myFileToRead = null;
        if(isSDCardState()){
            //得到root目录
            File parent_path = Environment.getExternalStorageDirectory();
            //在root目录下创建myFile文件夹
            File myFile = new File(parent_path.getAbsoluteFile(),"myFile");
            myFile.mkdirs();
            //在myFile文件夹下创建文件
            File file = new File(myFile.getAbsoluteFile(),fileName);
            myFileToRead = file;
        }
        return myFileToRead;
    }

    //从本地存储的联系人文件中返回联系人信息
    public List<ContactBean> getPhoneFile() {
        File file = getSDCardFile(PhoneActivity.PHONE_FILE);
        String myPhoneStr = readFileToSDCard(file);
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


    //读文件
    public String readFileToSDCard(File file){
        if(isSDCardState()){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len;
                StringBuilder sb = new StringBuilder();
                while((len = fis.read(buf)) != -1){
                    sb.append(new String(buf,0,len));
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    //写文件
    public void writeFileToSDCard(String content,String fileName){
        if(isSDCardState()){
            //得到root目录
            File parent_path = Environment.getExternalStorageDirectory();
            //在root目录下创建myFile文件夹
            File myFile = new File(parent_path.getAbsoluteFile(),"myFile");
            myFile.mkdirs();

            //在myFile文件夹下创建文件
            File file = new File(myFile.getAbsoluteFile(),fileName);
            Log.e("contactData",file.getAbsolutePath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
