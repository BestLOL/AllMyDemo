package com.qiuyi.cn.gesture;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;


//手势操作小demo
public class MainActivity extends Activity {

    private GestureDetector detector;
    ViewFlipper flipper;
    Animation[] animation = new Animation[4];
    final int FLIP_DISTANCE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new GestureDetector(this,listener);

        flipper = findViewById(R.id.filpper);
        flipper.addView(addAutoView(R.drawable.one));
        flipper.addView(addAutoView(R.drawable.two));
        flipper.addView(addAutoView(R.drawable.three));
        flipper.addView(addAutoView(R.drawable.four));
        flipper.addView(addAutoView(R.drawable.five));

        //初始化动画数组
        animation[0] = AnimationUtils.loadAnimation(this,R.anim.left_in);
        animation[1] = AnimationUtils.loadAnimation(this,R.anim.left_out);
        animation[2] = AnimationUtils.loadAnimation(this,R.anim.right_in);
        animation[3] = AnimationUtils.loadAnimation(this,R.anim.right_out);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将Activity碰到的事件，交给GestureDetector处理
        return detector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener listener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent motionEvent) {}
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
            //从右向左滑
            if(e1.getX() - e2.getX() > FLIP_DISTANCE){
                flipper.setAnimation(animation[0]);
                flipper.setAnimation(animation[1]);
                flipper.showPrevious();
                return true;
            }
            //从左向右滑
            if(e2.getX() - e1.getX() > FLIP_DISTANCE){
                flipper.setAnimation(animation[2]);
                flipper.setAnimation(animation[3]);
                flipper.showNext();
                return true;
            }
            return false;
        }
    };

    private View addAutoView(int resId){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }
}
