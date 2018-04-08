package com.qiuyi.cn.filemanager.FileControll;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qiuyi.cn.filemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/25.
 *
 * 最近模块
 */
public class recentlyFragment extends BaseFragment{

    private RecyclerView recently_rl;
    private List<FileInfo> listFiles;
    private RecentlyAdapter myadapter;
    private GridLayoutManager myGridManager;

    private float rlposY,rlnowY;
    private float grposY,grnowY;
    private int count = 0;
    private float distance = 0.0f;


    @Override
    protected void addView(FrameLayout myFrameLayout) {
        View view = View.inflate(mActivity, R.layout.fragment_recently,null);
        recently_rl = view.findViewById(R.id.recently_rl);
        myFrameLayout.addView(view);
    }

    @Override
    protected void initData() {
        //设置文件
        setFileInfoData();

        myGridManager = new GridLayoutManager(mActivity,4);
        //这里需要另外处理一下文件数量问题
        resolveManager();

        recently_rl.setLayoutManager(myGridManager);
        myadapter = new RecentlyAdapter(mActivity,listFiles);
        recently_rl.setAdapter(myadapter);


        recently_rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {

                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rlposY = ev.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rlnowY = ev.getRawY();
                        float dy = rlnowY - rlposY;

                        boolean istop = recently_rl.canScrollVertically(-1);
                        GridLayoutManager manager = (GridLayoutManager) recently_rl.getLayoutManager();
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



    /**
     * FileInfoList文件的设置
     */
    private void setFileInfoData() {

        listFiles = new ArrayList<>();

        //标题栏
        FileInfo infoTitle = new FileInfo();
        infoTitle.setType(0);
/*        infoTitle.setTitle("qq接收的图片");
        infoTitle.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));*/
        listFiles.add(infoTitle);

        //native_img
        for(int i=0;i<3;i++){
            FileInfo infoImg = new FileInfo();
            infoImg.setType(1);
            listFiles.add(infoImg);
        }

        FileInfo infoFile = new FileInfo();
        infoFile.setType(2);
        listFiles.add(infoFile);

        //间隔
        FileInfo infoLine = new FileInfo();
        infoLine.setType(3);
        listFiles.add(infoLine);
    }

    /**
     * GradView显示设置
     * 标题栏
     * native_img
     * 文件
     * 空行
     */
    private void resolveManager(){
        myGridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if(position == 0 || position ==4 || position==5){
                    return 4;
                }
                return 1;
            }
        });
    }
}
