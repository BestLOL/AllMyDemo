package com.qiuyi.cn.batteryedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CpuUsageInfo;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/20.
 */
public class BatteryView extends View{

    //电量
    private Integer power = 100;
    //方向
    private int orientation;
    //颜色
    private int color;

    //控件高度
    private int height;
    //控件宽度
    private int width;

    public BatteryView(Context context) {
        super(context);
        new BatteryView(context,null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //找到自定义的属性文件
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Battery);

        //横向
        orientation = typedArray.getInteger(R.styleable.Battery_batteryOrientation,0);
        //白色
        color = typedArray.getColor(R.styleable.Battery_batteryColor,0xfff);
        //电池电量
        power = typedArray.getInteger(R.styleable.Battery_batteryPower,100);

        typedArray.recycle();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new BatteryView(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(orientation==0){
            //绘制水平方向电池

        }else if(orientation ==1){
            //绘制竖直方向电池
            drawVertivalBattery(canvas);
        }

    }

    //绘制竖直方向电池
    private void drawVertivalBattery(Canvas canvas){

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND); //结合处为圆角
        paint.setStrokeCap(Paint.Cap.ROUND); // 设置转弯处为圆角
        paint.setColor(color);

        //设置画笔样式
        paint.setStyle(Paint.Style.STROKE);//空心
        float strokeWidth = height/50.0f;
        paint.setStrokeWidth(strokeWidth);//设置画笔宽度,根据宽度来设置

        //画矩形
        float headheight = height/20.f;

        RectF r1 = new RectF(strokeWidth,headheight+strokeWidth,width-strokeWidth,height-strokeWidth);
        //设置外边框颜色
        paint.setColor(Color.BLACK);
        canvas.drawRect(r1,paint);


        //绘制电池内矩形电量
        paint.setStyle(Paint.Style.FILL);
        float myheight = (100-power)/100.f * (height-strokeWidth-headheight);
        RectF r2 = new RectF(strokeWidth,myheight+strokeWidth+headheight,width-strokeWidth,height-strokeWidth);
        if(power < 30){
            paint.setColor(Color.RED);
        }
        if(power>=30 && power<50){
            paint.setColor(Color.BLUE);
        }
        if(power>=50){
            paint.setColor(Color.GREEN);
        }
        canvas.drawRect(r2,paint);

        //画电池头
        RectF headr3 = new RectF(width/4.0f,0,width*0.75f,headheight+strokeWidth);
        paint.setColor(Color.BLACK);
        canvas.drawRect(headr3,paint);
    }


    public void setPower(int power){
        //刷新界面
        invalidate();
    }

   //绘制水平方向电池

}
