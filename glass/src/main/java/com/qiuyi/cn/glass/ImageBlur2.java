package com.qiuyi.cn.glass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2018/1/31.
 * 毛玻璃效果二
 * anroid-image-blur
 */
public class ImageBlur2 extends Activity{

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.iv_blur)
    ImageView iv_blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur2);
        ButterKnife.bind(this);

        initData();
    }

    private void initData(){
/*        方法名： BlurUtil.doBlur(...)
        参数：   第一个参数 img1, 是原始的位图图像，
        第二个参数 20 , 是缩放的大小，这个数字约大，虚化程度越高
        第三个参数 10， 虚化质量，这个数字约大，虚化程度越高
        返回值： 返回一个 虚化（模糊）后的位图*/

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.newtimg);
        //缩放并显示
        Bitmap newImg = BlurUtil.doBlur(bmp, 20, 3);
        iv_blur.setImageBitmap(newImg);

        Glide.with(this).load(R.drawable.mypic)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(iv_avatar);
    }
}
