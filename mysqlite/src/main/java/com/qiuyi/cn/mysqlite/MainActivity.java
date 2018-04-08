package com.qiuyi.cn.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    /*
    * 第一个参数为Context
    * 第二个参数为数据库名
    * 第三个参数为：允许我们在查询数据的时候返回一个自定义的Cursor，一般传入null
    * 第四个参数：当前数据库的版本号,版本号再需要添加表的时候有效
    * 修改版本参数，实现数据库的更新
    * */
    private MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this,"BookStore.db",null,2);
    private Button myCreateBt,myAddBt,myUpdateBt,myDelete,myQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化UI
        initView();

        //创建数据库+数据表
        createDataBases();

        //添加数据
        addData();

        //改变数据
        updateData();

        //删除数据
        deleteData();

        //查询数据
        queryData();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        myCreateBt = (Button) findViewById(R.id.databases);
        myAddBt = (Button) findViewById(R.id.bt_add);
        myUpdateBt = (Button) findViewById(R.id.bt_update);
        myDelete = (Button) findViewById(R.id.bt_delete);
        myQuery = (Button) findViewById(R.id.bt_query);
    }

    /**
     * 创建数据库+表
     */
    private void createDataBases() {
        myCreateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 这两个方法都可以创建或打开一个现有的数据库（存在就直接打开，否则创建一个新的数据库）
                 * 并返回一个可对数据库进行读写操作的对象
                 */
                //myDataBaseHelper.getReadableDatabase();
                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
            }
        });
    }

    /**
     * 添加记录
     */
    private void addData(){
        myAddBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();

                ContentValues values1 = new ContentValues();
                values1.put("auto","神奇的阿三");
                values1.put("price",520.20);
                values1.put("exist",true);
                values1.put("number",312);
                ContentValues values2 = new ContentValues();
                values2.put("auto","神奇的阿四");
                values2.put("price",1314.13);
                values2.put("exist",true);
                values2.put("number",123);
                ContentValues values3 = new ContentValues();
                values3.put("auto","神奇的阿五");
                values3.put("price",131.13);
                values3.put("exist",true);
                values3.put("number",113);
                /*
                * 第一个参数是要插入的数据表
                * 第二参数，表示若表中有的数据没有赋值，且在表中可为null,那么就将自动赋值，一般传null
                * 第三参数，要传的值
                * */
                db.insert("Book",null,values1);
                db.insert("Book",null,values2);
                db.insert("Book",null,values3);
            }
        });
    }


    /**
     * 修改数据库数据
     */
    private void updateData(){
        myUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("price",987.98);
                values.put("auto","我是谁");

                db.update("Book",values,"auto = ?",new String[]{"神奇的阿三"});
            }
        });
    }

    /**
     * 删除数据库数据
     */
    private void deleteData(){
        myDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();

                db.delete("Book","price < ?",new String[]{"200"});
            }
        });
    }

    /**
     * 查询数据库数据
     */
    private void queryData(){
        myQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",new String[]{"auto","price"},"auto=?",new String[]{"我是谁"},null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String auto = cursor.getString(cursor.getColumnIndex("auto"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("mysqlite", auto+"-"+price);
                    }while (cursor.moveToNext());
                }
            }
        });

    }
}
