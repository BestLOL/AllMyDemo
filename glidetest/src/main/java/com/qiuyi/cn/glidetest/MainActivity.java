package com.qiuyi.cn.glidetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;

//郭霖大神Glide1-8介绍
//Glide简单测试

/**
 *
 * 1 将Glide中的HttpUrlConnect下载图片替换成了OkHttp3
 * 2 然后在OkHttp3中添加了拦截器实现了加载图片的进度条显示
 *
 */
public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("加载中");
    }

    public void loadImage(View view){
        final String url = "http://f.hiphotos.baidu.com/image/pic/item/503d269759ee3d6db032f61b48166d224e4ade6e.jpg";
        //Gilde加载图片会自动帮助我们去防止图片内存浪费
        //实际上就是使用了图片的压缩与缓存技术来实现防止内存的浪费和快速加载图片

        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                progressDialog.setProgress(progress);
            }
        });


        Glide.with(this)
                .load(url)
                //.asBitmap()加载静态图片
                //.asGif()加载Gif动图
                .placeholder(R.drawable.newtimg1)//占位符
                .error(R.drawable.newtimg2)//错误占位符
                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用硬盘缓存（这里是为了让占位符显示出来）
                //.skipMemoryCache(true)禁用内存缓存
                //.override(100,100)加载图片固定尺寸
                //.dontTransform()不进行图片变换
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .into(new GlideDrawableImageViewTarget(imageView){
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressDialog.show();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressDialog.dismiss();
                        ProgressInterceptor.removeListener(url);
                    }
                });
    }

    public void gotoPhotoWall(View view){
        Intent intent = new Intent(this,PhotoWallTest.class);
        startActivity(intent);
    }
}
