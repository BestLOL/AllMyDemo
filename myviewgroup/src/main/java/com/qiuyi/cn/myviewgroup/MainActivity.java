package com.qiuyi.cn.myviewgroup;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//侧滑栏1
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.drawerLayout_listView);
        textView = (TextView) findViewById(R.id.drawerLayout_fragment_tv);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        final List<String> list = new ArrayList<String>();
        list.add("网易");
        list.add("腾讯");
        list.add("阿里");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(list.get(i));
                //隐藏侧滑栏
                showDrawerLayout();
            }
        });

        drawerLayout.openDrawer(Gravity.LEFT);//侧滑打开
    }


    //侧滑栏显示隐藏控制
    private void showDrawerLayout() {
        if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.openDrawer(Gravity.LEFT);
        }else{
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }


}
