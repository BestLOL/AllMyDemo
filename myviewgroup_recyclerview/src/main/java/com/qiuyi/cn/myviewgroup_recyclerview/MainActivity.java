package com.qiuyi.cn.myviewgroup_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyViewGroup myGroup;
    private RecyclerView myRlview;
    private GridLayoutManager myManager;
    private NativiAdapter myAdapter;
    private List<FileType> myFileTypes;


    private float rlposY,rlnowY;
    private float grposY,grnowY;
    private int count = 0;
    private float distance = 0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGroup = (MyViewGroup) findViewById(R.id.myGroup);
        myRlview = (RecyclerView) findViewById(R.id.myRlview);

        initData();

        myManager = new GridLayoutManager(this,4);
        myManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
        myRlview.setLayoutManager(myManager);
        myAdapter = new NativiAdapter(this,myFileTypes);
        myRlview.setAdapter(myAdapter);


        myRlview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {

                switch (ev.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rlposY = ev.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rlnowY = ev.getRawY();
                        float dy = rlnowY - rlposY;

                        boolean istop = myRlview.canScrollVertically(-1);
                        GridLayoutManager manager = (GridLayoutManager) myRlview.getLayoutManager();
                        int l1 = manager.findFirstVisibleItemPosition();
                        int l2 = manager.findFirstCompletelyVisibleItemPosition();

                        Log.e("move", "istop："+!istop+"distance："+distance+"srollY"+myGroup.getScrollY()+"posy："+l1+"nowy："+l2);
                        if((!istop && distance>=0 && dy>0)||myGroup.getScrollY()<0){

                            distance += dy;
                            count++;

                            if(count==1){
                                distance -= dy;
                            }

                            myGroup.scrollTo(0, (int) -distance+10);
/*                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myRlview.getLayoutParams();

                            myRlview.setLayoutParams(params);*/
                            /*myRlview.scrollTo(0, (int) -distance);*/
                        }

                        rlposY = rlnowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        myGroup.scrollTo(0, 0);
                        count=0;
                        distance = 0.0f;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }


    private void initData() {
        myFileTypes = new ArrayList<>();

        for(int i=0;i<6;i++){
            FileType type1 = new FileType();
            type1.setShowType(0);
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
