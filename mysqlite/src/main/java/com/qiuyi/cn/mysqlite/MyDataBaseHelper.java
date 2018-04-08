package com.qiuyi.cn.mysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/9.
 *
 * 数据库存储模块
 */
public class MyDataBaseHelper extends SQLiteOpenHelper{

    private Context myContext;

    /*
    * integer 表示整型
    * real 表示浮点型
    * text 表示文本类型
    * blob 表示二进制类型
    * */
    private String CREATE_BOOK = "create table Book(" +
            "id integer primary key autoincrement," +
            "auto text," +
            "price real," +
            "exist blob," +
            "number integer)";

    private String CREATE_CATEGOTY = "create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        /**
         * 在原来有一张表的情况下，再次创建表，此时直接执行是不会创建成功的
         * 因为有一张表的情况下，就不会再次触发onCreate方法
         * 此时就需要调用onUpgrade来帮忙
         */
        sqLiteDatabase.execSQL(CREATE_CATEGOTY);
        Toast.makeText(myContext,"数据库，数据表创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists Book");
        sqLiteDatabase.execSQL("drop table if exists Category");
        onCreate(sqLiteDatabase);
    }
}
