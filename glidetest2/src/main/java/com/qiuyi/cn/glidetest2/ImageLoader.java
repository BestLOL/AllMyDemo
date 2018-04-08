package com.qiuyi.cn.glidetest2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by Administrator on 2018/2/28.
 * 对图片进行管理的类
 * 这里我们将ImageLoader类设成单例，并在构造函数中初始化了LruCache类，
 * 把它的最大缓存容量设为最大可用内存的1/8。然后又提供了其它几个方法可以操作LruCache，以及对图片进行压缩和读取。，
 */
public class ImageLoader {

    /**
     * 图片内存缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时将最少最近使用的图片移除掉
     */
    private static LruCache<String,Bitmap> mMemoryCache;

    /**
     * ImageLoader实例
     */
    private static ImageLoader mImageLoader;

    private ImageLoader(){
        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        //设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //重写此方法来衡量每张图片的大小，默认返回图片数量
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * 获取ImageLoader实例
     *
     * @return ImageLoader实例
     */
    public static ImageLoader getInstance(){
        if(mImageLoader == null){
            mImageLoader = new ImageLoader();
        }
        return mImageLoader;
    }

    /**
     * 将一张图片存储到LruCache中
     * @param key LruCache的键，这里传入图片的URL地址
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap图片
     */
    public void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemoryCache(key)==null){
            mMemoryCache.put(key,bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null
     * @param key LruCache的键，这里传入图片的URL地址
     * @return 对应的Bitmap或者null
     */
    public Bitmap getBitmapFromMemoryCache(String key){
       return mMemoryCache.get(key);
    }

    /**
     * 实现图片的压缩
     * @param options 原图片的一些基本参数
     * @param reqWidth 需要的宽度
     * @return 缩放比例
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth){
        //源图片的宽度
        final int width = options.outWidth;
        //想要进行的压缩比例
        int inSampleSize = 1;

        if(width > reqWidth){
            //计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width/(float)reqWidth);
            inSampleSize = widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 图片压缩方法
     * @param pathName 图片路径
     * @param reqWidth 真实宽度
     * @return 压缩完成的图片
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName,int reqWidth){
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName,options);

        //调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options,reqWidth);

        //使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(pathName,options);
    }
}
