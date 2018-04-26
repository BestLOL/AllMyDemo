package com.qiuyi.cn.recyclerviewcheckbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<String> listStrings;
    private List<String> listShow;

    private RecyclerView myRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button cb_select,bt_select,bt_delete,bt_cancel;

    private MyAdapter myAdapyer;

    boolean isSelectAll = true;

    private int position = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initData() {

        listStrings = new ArrayList<>();
        listShow = new ArrayList<>();
        for(int i=0;i<100;i++){
            listStrings.add(i,"Item"+i);
        }

        listShow.addAll(listStrings.subList(0,30));

        myAdapyer = new MyAdapter(this,listShow);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(myAdapyer);

        myAdapyer.setItemOnClickListener(new MyAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                boolean isShowBox = myAdapyer.isShowCheckBox();
                if(isShowBox){
                    //正在显示checkbox
                    boolean[] flag = myAdapyer.getFlag();
                    flag[position] = !flag[position];
                    myAdapyer.setFlag(flag);
                    myAdapyer.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),listStrings.get(position),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //显示并选中当前
                myAdapyer.setShowCheckBox(true);
                boolean[] flag = myAdapyer.getFlag();
                flag[position] = true;
                myAdapyer.setFlag(flag);

                myAdapyer.notifyDataSetChanged();
            }
        });

        //滑动监听
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(-1)){
                    //滑动到顶端
                }else if(!recyclerView.canScrollVertically(1)){
                    //滑动到底端
                    int lastposition = position+10;
                    if(lastposition>listStrings.size()){
                        lastposition = listStrings.size();
                    }
                    listShow.addAll(listStrings.subList(position,lastposition));
                    myAdapyer.ReFresh();
                    position = lastposition;

/*                    for(int i=30;i<50;i++){
                        listStrings.add(i,"Item"+i);
                        myAdapyer.ReFresh();
                    }*/
                }
            }
        });
    }

    private void initView() {
        cb_select = (Button) findViewById(R.id.cb_select);
        bt_select = (Button) findViewById(R.id.bt_select);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        myRecyclerView = (RecyclerView) findViewById(R.id.rl_myrl);

        linearLayoutManager = new LinearLayoutManager(this);

        cb_select.setOnClickListener(this);
        bt_select.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cb_select:
                //全选
                if(isSelectAll){
                    myAdapyer.selectAll();
                    isSelectAll = false;
                    cb_select.setText("取消");
                }else{
                    myAdapyer.noSelect();
                    isSelectAll = true;
                    cb_select.setText("全选");
                }
                myAdapyer.notifyDataSetChanged();
                break;
            case R.id.bt_select:
                //选择，显示一下有哪几个选择了
                boolean[] flag = myAdapyer.getFlag();
                for(int i = flag.length-1;i>=0;i--){
                    if(flag[i]){
                        Log.e("select", "选中："+i);
                    }
                }
                break;
            case R.id.bt_delete:
                //删除，删除选中的选项
                boolean[] flagNow = myAdapyer.getFlag();
                for(int i = flagNow.length-1;i>=0;i--){
                    if(flagNow[i]){
                        Log.e("select", "选中："+i);
                        listStrings.remove(i);
                        myAdapyer.ReFresh();
                    }
                }
                break;
            case R.id.bt_cancel:
                myAdapyer.setShowCheckBox(false);
                myAdapyer.ReFresh();
                break;
        }
    }
}
