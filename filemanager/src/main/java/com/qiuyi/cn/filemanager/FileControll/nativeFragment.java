package com.qiuyi.cn.filemanager.FileControll;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qiuyi.cn.filemanager.ConstantValue;
import com.qiuyi.cn.filemanager.MyFileManager;
import com.qiuyi.cn.filemanager.R;
import com.qiuyi.cn.filemanager.bean1.FileBean;
import com.qiuyi.cn.filemanager.bean1.ImageBean;
import com.qiuyi.cn.filemanager.bean1.MusicBean;
import com.qiuyi.cn.filemanager.bean1.VideoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2018/3/25.
 */
public class nativeFragment extends BaseFragment{

    private RecyclerView native_rl;

    public static final String[] NATIVE_TITLE={"图片","视频","文档","音乐","压缩包","所有文件"};
    public static final int[] NATIVE_SIZE = new int[6];
    public static final int[] NATIVE_IMAGES ={R.drawable.native_img,R.drawable.native_audio,
            R.drawable.native_doc,R.drawable.native_music,R.drawable.native_zar};

    private GridLayoutManager myGridManager;
    private NativiAdapter myAdapter;
    private List<FileType> myFileTypes;


    private float rlposY,rlnowY;
    private float grposY,grnowY;
    private int count = 0;
    private float distance = 0.0f;

    //各类文件
    private MyFileManager myFileManager;
    private List<MusicBean> listMusics;//音乐
    private List<VideoBean> listVideos;//视频
    private List<ImageBean> listImages;//图片
    private List<FileBean> listFiles;//文件
    private List<FileBean> listFileZars;//压缩包

    @Override
    protected void addView(FrameLayout myFrameLayout) {
        View view = View.inflate(mActivity, R.layout.fragment_native,null);

        native_rl = view.findViewById(R.id.native_rl);

        myFrameLayout.addView(view);
    }

    @Override
    protected void initData() {

        initAddData();

        myGridManager = new GridLayoutManager(mActivity,4);

        myGridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position < 6 ){
                    return 2;
                }
                if(position == 6){
                    return 4;
                }
                return 1;
            }
        });

        native_rl.setLayoutManager(myGridManager);

        myAdapter = new NativiAdapter(mActivity,myFileTypes);
        native_rl.setAdapter(myAdapter);


        new Thread(new Runnable() {
            @Override
            public void run() {
                getFileList();
            }
        }).start();

        //点击
        myAdapter.setOnItemClick(new NativiAdapter.myItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                FileType fileType = myFileTypes.get(position);
                if(fileType.getShowType()==0){
                    Intent intent = new Intent(mActivity, FileShowActivity.class);
                    if(position==0){
                        intent.putExtra("type",0);
                        intent.putExtra("listFile", (Serializable)listImages);
                    }else if(position == 1){
                        intent.putExtra("type",1);
                        intent.putExtra("listFile", (Serializable)listVideos);
                    }else if(position == 2){
                        intent.putExtra("type",2);
                        intent.putExtra("listFile", (Serializable)listFiles);
                    }else if(position == 3){
                        intent.putExtra("type",3);
                        intent.putExtra("listFile", (Serializable)listMusics);
                    }else if(position == 4){
                        intent.putExtra("type",4);
                        intent.putExtra("listFile", (Serializable)listFileZars);
                    }else{
                        intent = null;
                    }

                    if(intent!=null){
                        mActivity.startActivity(intent);
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        native_rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {

                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rlposY = ev.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rlnowY = ev.getRawY();
                        float dy = rlnowY - rlposY;

                        boolean istop = native_rl.canScrollVertically(-1);
                        GridLayoutManager manager = (GridLayoutManager) native_rl.getLayoutManager();
                        int l1 = manager.findFirstVisibleItemPosition();
                        int l2 = manager.findFirstCompletelyVisibleItemPosition();

                        Log.e("move", "istop："+!istop+"distance："+distance+"srollY"+myViewGroup.getScrollY()+"posy："+l1+"nowy："+l2);
                        if((!istop && distance>=0 && dy>0)||myViewGroup.getScrollY()<0){

                            distance += dy;
                            count++;

                            if(count==1){
                                distance -= dy;
                            }

                            myViewGroup.scrollTo(0, (int) -distance+10);
                            setLockDisplay(distance,myViewGroup.mRefreshHeight,myViewGroup.mSecretHeight);
                        }

                        rlposY = rlnowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        myViewGroup.scrollTo(0, 0);
                        setViewDispaly(distance,myViewGroup.mRefreshHeight,myViewGroup.mSecretHeight);
                        count=0;
                        distance = 0.0f;
                        //下拉距离，使用回调

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    //得到所有需要的文件
    private void getFileList() {

        //使用线程并发得到需要的文件
        myFileManager = MyFileManager.getInstance(mActivity);

        listImages = new ArrayList<>();
        listMusics = new ArrayList<>();
        listVideos = new ArrayList<>();
        listFiles = new ArrayList<>();
        listFileZars = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<List<ImageBean>> myImgCallable = new Callable<List<ImageBean>>() {
            @Override
            public List<ImageBean> call() throws Exception {
                return myFileManager.getImages();
            }
        };
        Callable<List<MusicBean>> myMucCallable = new Callable<List<MusicBean>>() {
            @Override
            public List<MusicBean> call() throws Exception {
                return myFileManager.getMusics();
            }
        };
        Callable<List<VideoBean>> myVidCallable = new Callable<List<VideoBean>>() {
            @Override
            public List<VideoBean> call() throws Exception {
                return myFileManager.getVideos();
            }
        };
        Callable<List<FileBean>> myFileCallable = new Callable<List<FileBean>>() {
            @Override
            public List<FileBean> call() throws Exception {
                return myFileManager.getFilesByType(ConstantValue.TYPE_DOC);
            }
        };
        Callable<List<FileBean>> myFileZarCallable = new Callable<List<FileBean>>() {
            @Override
            public List<FileBean> call() throws Exception {
                return myFileManager.getFilesByType(ConstantValue.TYPE_ZIP);
            }
        };

        FutureTask<List<ImageBean>> myImgTask = new FutureTask<List<ImageBean>>(myImgCallable);
        FutureTask<List<MusicBean>> myMucTask = new FutureTask<List<MusicBean>>(myMucCallable);
        FutureTask<List<VideoBean>> myVioTask = new FutureTask<List<VideoBean>>(myVidCallable);
        FutureTask<List<FileBean>> myFileTask = new FutureTask<List<FileBean>>(myFileCallable);
        FutureTask<List<FileBean>> myFileZarTask = new FutureTask<List<FileBean>>(myFileZarCallable);

        executorService.submit(myImgTask);
        executorService.submit(myMucTask);
        executorService.submit(myVioTask);
        executorService.submit(myFileTask);
        executorService.submit(myFileZarTask);

        try {
            listImages = myImgTask.get();
            listMusics = myMucTask.get();
            listVideos = myVioTask.get();
            listFiles = myFileTask.get();
            listFileZars = myFileZarTask.get();

            NATIVE_SIZE[0] = listImages.size();
            NATIVE_SIZE[1] = listMusics.size();
            NATIVE_SIZE[2] = listVideos.size();
            NATIVE_SIZE[3] = listFiles.size();
            NATIVE_SIZE[4] = listFileZars.size();
            NATIVE_SIZE[5] = (listImages.size()+listMusics.size()+listVideos.size()
                    + listFiles.size()+listFileZars.size());


            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myAdapter.changeList();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(executorService!=null){
                executorService.shutdown();
            }
        }


    }


    //构造数据
    private void initAddData() {
        myFileTypes = new ArrayList<>();

        for(int i=0;i<6;i++){
            FileType type1 = new FileType();
            type1.setShowType(0);
            if(i!=5){
                type1.setImage(NATIVE_IMAGES[i]);
            }
            type1.setName(NATIVE_TITLE[i]);
            type1.setSize(NATIVE_SIZE[i]);

            myFileTypes.add(type1);
        }

        FileType type2 = new FileType();
        type2.setShowType(1);
        myFileTypes.add(type2);

        for(int i = 0;i<2;i++){
            FileType type3 = new FileType();
            type3.setShowType(2);
            myFileTypes.add(type3);
        }
    }
}
