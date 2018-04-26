package com.qiuyi.cn.mvp.main;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qiuyi.cn.mvp.LoginActivity.LoginPresenter;
import com.qiuyi.cn.mvp.LoginActivity.LoginView;
import com.qiuyi.cn.mvp.R;
import com.qiuyi.cn.mvp.adapter.TextAdapter;
import com.qiuyi.cn.myloadingdialog.LoadingDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView,SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private TextAdapter myadapter;

    private ProgressBar progressBar;

    private MainPresenter myPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_fresh);
        recyclerView = (RecyclerView) findViewById(R.id.rl_view);
        progressBar = (ProgressBar) findViewById(R.id.pb_bar);

        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(this);

        myPresenter = new MainPresenterImpl(this,new FindItemInteractorImpl());
    }


    @Override
    public void setRefresh(boolean flag) {
        swipeRefreshLayout.setRefreshing(flag);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }



    @Override
    public void setRecyclerView(List<String> listItems) {
        manager = new LinearLayoutManager(this);
        myadapter = new TextAdapter(this,listItems);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myadapter);

        myPresenter.onItemClick(myadapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        myPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        myPresenter.onRefresh();
    }
}
