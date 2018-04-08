package com.qiuyi.cn.glidetest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * Created by Administrator on 2018/2/28.
 * 照片墙，使用缓存技术，防止OOM（OutOfMemory）
 */
public class PhotoWallTest extends Activity{

    //图片展示的GridView
    private GridView mPhotoWall;
    //GridView适配器
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        mPhotoWall = findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this,0,Images.imageThumbUrls,mPhotoWall);

        mPhotoWall.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //退出程序时结束所有下载任务
        adapter.cancelAllTasks();
    }
}
