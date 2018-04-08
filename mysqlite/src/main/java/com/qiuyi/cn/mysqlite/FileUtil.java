package com.qiuyi.cn.mysqlite;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2018/3/8.
 * android文件存储
 */
public class FileUtil {

    //android文件存储
    public void save1(File file, String dataMsg){
        BufferedWriter writer = null;
        try {
            /*
            * true表示追加内容
            * false表示覆盖内容
            * */
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            writer.newLine();
            writer.write(dataMsg);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //openFileOutput是Activity提供的方法,如果要使用下面方法就放到Activity中，然后打开注释
    public void save2(File file,String dataMsg){
        BufferedWriter writer = null;
        FileOutputStream out = null;
        try {
            /*
            * Context.MODE_APPEND 表示追加内容
            * Context.MODE_PRIVATE 表示覆盖内容
            * */
            //writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(file.getName(), Context.MODE_PRIVATE)));
            writer.newLine();
            writer.write(dataMsg);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save3(File file,String dataMsg){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file,true);
            writer.write("\r\n");//换行
            writer.write(dataMsg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //android读取文件
    public void readFile1(File file){
        BufferedReader read = null;
        StringBuilder myContent = new StringBuilder();
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            //同理openFileInput方法也是Avtivity的
            //read = new BufferedReader(new InputStreamReader(openFileInput(file.getName()),"utf-8"));

            String line = "";
            while((line = read.readLine())!=null){
                myContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(read!=null){
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void readFile2(File file){
        FileInputStream in = null;
        StringBuilder myContent = new StringBuilder();
        try {
            in = new FileInputStream(file);
            byte[] mybyte = new byte[1024];
            int length;
            while ((length = in.read(mybyte))!=-1){
                myContent.append(new String(mybyte,0,length));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
