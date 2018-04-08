package com.qiuyi.cn.mysqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11.
 * LitePal操纵数据库
 */
public class LitePalTest extends Activity{

    private Button myCreate,myUpdate,myDelete,myAdd,myQuery1,myQuery2,myQuery3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //初始化UI
        initView();

        //创建数据表
        createTable();

        //添加数据
        addData();

        //修改数据
        updateData();

        //删除数据
        deleteData();

        //查询数据
        retrieveData1();
        retrieveData2();
        retrieveData3();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        myCreate = findViewById(R.id.bt_create);
        myAdd = findViewById(R.id.bt_add);
        myUpdate = findViewById(R.id.bt_update);
        myDelete = findViewById(R.id.bt_delete);
        myQuery1 = findViewById(R.id.bt_query1);
        myQuery2 = findViewById(R.id.bt_query2);
        myQuery3 = findViewById(R.id.bt_query3);
    }

    /**
     * 创建数据库+表
     */
    private void createTable(){
        myCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建数据库+表
                Connector.getDatabase();
            }
        });
    }

    /**
     * 添加数据
     */
    private void addData(){
        myAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book myBook1 = new Book();
                myBook1.setAuthor("张三");
                myBook1.setName("神奇的阿三");
                myBook1.setPages(123);
                myBook1.setPrice(433.12);
                myBook1.save();

                Book myBook2 = new Book();
                myBook2.setAuthor("李四");
                myBook2.setName("神奇的李四");
                myBook2.setPages(321);
                myBook2.setPrice(123.22);
                myBook2.save();
            }
        });
    }

    /**
     * 修改数据
     */
    private void updateData(){
        myUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book myBook = new Book();
                myBook.setName("神奇的书");
                myBook.setAuthor("王五");
                myBook.setPrice(132.12);
                myBook.updateAll("name=? and author=? and price=?","神奇的李四","李四","123.22");
            }
        });
    }

    /**
     * 删除数据
     */
    private void deleteData(){
        myDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class,"price < ? and name = ?","200","神奇的书");
            }
        });
    }

    /**
     * 查询数据1
     */
    private void retrieveData1(){
        myQuery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> myBooks = DataSupport.findAll(Book.class);
                for(Book myBook:myBooks){
                    Log.d("BookInfo", "author"+myBook.getAuthor());
                    Log.d("BookInfo", "name"+myBook.getName());
                    Log.d("BookInfo", "pages"+myBook.getPages());
                    Log.d("BookInfo", "price"+myBook.getPrice());
                }
            }
        });
    }
    /**
     * 查询数据2
     */
    private void retrieveData2(){
        myQuery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> myBooks = DataSupport.select("name","author","price")
                           .where("pages < ?","200")
                           .limit(2)
                           .offset(1)
                           .order("price asc")
                           .find(Book.class);
                for (Book myBook:myBooks){
                    Log.d("BookInfo", "author"+myBook.getAuthor());
                    Log.d("BookInfo", "name"+myBook.getName());
                    Log.d("BookInfo", "price"+myBook.getPrice());
                }
            }
        });
    }
    /**
     * 查询数据3
     */
    private void retrieveData3(){
        myQuery3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = DataSupport.findBySQL("select * from Book where pages < ? and price>?","200","100");
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        Integer pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        Double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("BookInfo", "name"+name);
                        Log.d("BookInfo", "author"+author);
                        Log.d("BookInfo", "pages"+pages);
                        Log.d("BookInfo", "prices"+prices);
                    }while(cursor.moveToNext());
                }
            }
        });
    }
}
