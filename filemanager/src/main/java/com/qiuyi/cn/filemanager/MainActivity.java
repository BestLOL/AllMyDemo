package com.qiuyi.cn.filemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.qiuyi.cn.filemanager.adapter.FileAdapter;
import com.qiuyi.cn.filemanager.adapter.ImageAdapter;
import com.qiuyi.cn.filemanager.adapter.MusicAdapter;
import com.qiuyi.cn.filemanager.adapter.VideoAdapter;
import com.qiuyi.cn.filemanager.bean.Video;
import com.qiuyi.cn.filemanager.bean1.FileBean;
import com.qiuyi.cn.filemanager.bean1.ImageBean;
import com.qiuyi.cn.filemanager.bean1.MusicBean;
import com.qiuyi.cn.filemanager.bean1.VideoBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyFileManager myFileManager;
    private GridLayoutManager myGridManager;
    private RecyclerView myView;

    private List<ImageBean> listImg;
    private ImageAdapter myAdapter;

    private List<MusicBean> listMusic;
    private MusicAdapter myMusicAdapter;

    private List<VideoBean> listVideo;
    private VideoAdapter myVideoAdapter;

    private List<FileBean> listFile;
    private FileAdapter myFileAdapter;

    private static final String[] ACTION_PERMISSION = {
            //存储权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //读取权限
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        initView();
        initData();
    }

    //数据初始化
    private void initData() {

        //获得所有图片
        myFileManager = MyFileManager.getInstance(this);

/*        listImg =myFileManager.getImages();
        myAdapter = new ImageAdapter(this,listImg,myGridManager);*/

/*        listMusic = myFileManager.getMusics();
        myMusicAdapter = new MusicAdapter(this,listMusic,myGridManager);*/

/*        listVideo = myFileManager.getVideos();
        myVideoAdapter = new VideoAdapter(this,listVideo,myGridManager);*/

        listFile = myFileManager.getFilesByType(ConstantValue.TYPE_ZIP);
        myFileAdapter = new FileAdapter(this,listFile,myGridManager);

        myView.setLayoutManager(myGridManager);
        myView.setAdapter(myFileAdapter);

        myFileAdapter.setOnFileItemClick(new FileAdapter.FileItemClick() {
            @Override
            public void openFile(View view, int position, List<FileBean> allFileBean) {
                FileBean fileBean = allFileBean.get(position);
                if(fileBean.getFiletype()==2){
                    OpenFileUtil.openFileByPath(getApplicationContext(),fileBean.getPath());
                }
            }
        });


/*        myVideoAdapter.setOnVideoItemClick(new VideoAdapter.VideoItemClick() {
            @Override
            public void openVideo(View view, int position, List<VideoBean> allVideoBean) {
                VideoBean videoBean = allVideoBean.get(position);
                if(videoBean.getFiletype()==2){
                    Intent intent = OpenFileUtil.getVideoFileIntent(videoBean.getPath());
                    startActivity(intent);
                }
            }
        });*/


/*        myAdapter.setOnImageItemClick(new ImageAdapter.ImageItemClick() {
            @Override
            public void openImage(View view, int position, List<ImageBean> allImageBean) {
                ImageBean imageBean = allImageBean.get(position);
                if(imageBean.getFiletype()==1){
                    Intent intent = OpenFileUtil.getImageFileIntent(imageBean.getPath());
                    startActivity(intent);
                }
            }
        });*/

/*        myMusicAdapter.setOnMusicItemClick(new MusicAdapter.MusicItemClick() {
            @Override
            public void openMusic(View view, int position, List<MusicBean> allMusicBean) {

                MusicBean musicBean = allMusicBean.get(position);
                if(musicBean.getFiletype()==2){
                    Intent intent = OpenFileUtil.getAudioFileIntent(musicBean.getPath());
                    startActivity(intent);
                }

            }
        });*/

    }

    //View初始化
    private void initView() {
        myView = (RecyclerView) findViewById(R.id.my_recyclerview);

        myGridManager = new GridLayoutManager(this,4);
    }


    //获取权限
    public void getPermission() {
        for(String one:ACTION_PERMISSION){
            if(ActivityCompat.checkSelfPermission(this,one)!= PackageManager.PERMISSION_GRANTED){
                //若之前拒绝过，在这里需要解释
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,one)){
                    Snackbar.make(myView,"please give me the permission",Snackbar.LENGTH_SHORT).show();
                }else{
                    //进行权限申请
                    ActivityCompat.requestPermissions(this,new String[]{one},1);
                }
            }
        }
    }

    //权限申请结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"申请成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"申请失败",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
