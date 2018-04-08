package com.qiuyi.cn.glass;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 毛玻璃效果一
 * glide-transformations + gilde
 */
public class MainActivity extends Activity {

    private ImageView blurImageView;
    private ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initData();
    }


    private void findViews(){
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) findViewById(R.id.iv_avatar);
    }

    private void initData(){
        Glide.with(this).load(R.drawable.newtimg)
                .bitmapTransform(new BlurTransformation(this, 25),new CenterCrop(this))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.mypic)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView);
    }
}
