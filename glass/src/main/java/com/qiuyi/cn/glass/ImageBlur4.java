package com.qiuyi.cn.glass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.blur.StackBlur;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2018/1/31.
 * 毛玻璃效果三
 *
 */
public class ImageBlur4 extends Activity{

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.iv_blur)
    ImageView iv_blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur4);
        ButterKnife.bind(this);

        initData();
    }

    private void initData(){
        /*实现方法有三种，第一个是Java实现的，第二个和第三个是调用C语言实现的 ，
        具体的区别也就是代码执行的耗时操作时间，从图片中可以看出Java使用时间远大于c运行的时间。
        可以通过改变radius的值来改变模糊度，值越大，模糊度越大，radius<=0时则图片不显示；
        一般radius的值以20左右为佳！*/

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.newtimg1);
        // Bitmap JNI NativeBitmap
        Bitmap newBitmap = StackBlur.blurNatively(bmp, (int) 25, false);//高斯模糊
        iv_blur.setImageBitmap(newBitmap);

        Glide.with(this).load(R.drawable.mypic)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(iv_avatar);
    }
}
