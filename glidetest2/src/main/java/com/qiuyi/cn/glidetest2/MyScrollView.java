package com.qiuyi.cn.glidetest2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/28.
 * 自定义的ScrollView
 */
public class MyScrollView extends ScrollView implements View.OnTouchListener{

    /**
     * 每页要加载的图片数量
     */
    public static final int PAGE_SIZE = 15;
    /**
     * 记录当前已加载到第几页
     */
    private int page;

    //对图片进行管理的工具类
    private ImageLoader imageLoader;

    //记录所有正在下载或者等待下载的任务
    private static Set<LoadImageTask> taskCollection;

    //是否已经加载过layout，这里onLayout中的初始化只需要加载一次
    private boolean loadOnce;
    /**
     * MyScrollView下的直接子布局。
     */
    private static View scrollLayout;

    /**
     * MyScrollView布局的高度。
     */
    private static int scrollViewHeight;
    /**
     * 第一列的布局
     */
    private LinearLayout firstColumn;

    /**
     * 第二列的布局
     */
    private LinearLayout secondColumn;

    /**
     * 第三列的布局
     */
    private LinearLayout thirdColumn;

    /**
     * 当前第一列的高度
     */
    private int firstColumnHeight;

    /**
     * 当前第二列的高度
     */
    private int secondColumnHeight;

    /**
     * 当前第三列的高度
     */
    private int thirdColumnHeight;


    /**
     * 每一列的宽度
     */
    private int columnWidth;
    /**
     * 记录上垂直方向的滚动距离。
     */
    private static int lastScrollY = -1;
    /**
     * 记录所有界面上的图片，用以可以随时控制对图片的释放。
     */
    private List<ImageView> imageViewList = new ArrayList<ImageView>();


    //构造函数
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化对象
        this.imageLoader = ImageLoader.getInstance();
        taskCollection = new HashSet<LoadImageTask>();
        //触摸监听事件
        setOnTouchListener(this);
    }

    //ScrollView的一些初始化操作
    /**
     * 进行一些关键性的初始化操作，获取MyScrollView的高度，
     * 以及得到第一列的宽度值，并在这里开始加载第一页的图片
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //第一次改变并且是第一次加载
        if(changed && !loadOnce){
            //得到MyScrollView的高度
            scrollViewHeight = getHeight();
            //MyScrollView下的直接子布局
            scrollLayout = getChildAt(0);

            //三列布局
            firstColumn = findViewById(R.id.first_column);
            secondColumn = findViewById(R.id.second_column);
            thirdColumn = findViewById(R.id.third_column);

            //每一列的宽度
            columnWidth = firstColumn.getWidth();

            loadOnce = true;

            //加载图片，按照：网络->本地缓存（SD卡中）->内存缓存（LruCache）
            loadMoreImages();
        }
    }


    /**
     * 在Handler中进行图片可见性检查的判断，以及加载更多的图片
     */
    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyScrollView myScrollView = (MyScrollView) msg.obj;
            //得到手指离开界面后的滚动距离
            int scrollY = myScrollView.getScrollY();
            //如果当前的滚动位置和上次相同，表示已停止滚动
            if(scrollY == lastScrollY){
                //当滚动到最底部，并且当前没有正在下载的任务时，开始加载下一页图片
                if(scrollViewHeight+scrollY >= scrollLayout.getHeight() && taskCollection.isEmpty()){
                    //加载下一页图片
                    myScrollView.loadMoreImages();
                }
                //检查当前已经加载完毕的图片
                myScrollView.checkVisibility();
            }else{
                lastScrollY = scrollY;
                Message message = new Message();
                message.obj = myScrollView;
                //5毫秒后再次对滚动位置进行判断
                handler.sendMessage(message);
            }
        }
    };
    //触摸监听
    /**
     * 监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //手指离开屏幕
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            Message message = new Message();
            message.obj = this;
            handler.sendMessage(message);
        }
        return false;


    }


    /**
     * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载
     */
    public void loadMoreImages(){
        if(hasSDCard()){
            //有SDCard
            int startIndex = page * PAGE_SIZE;
            int endIndex = (page+1) * PAGE_SIZE;
            if(startIndex < Images.imageUrls.length){
                //还有图片没有加载完
                Toast.makeText(getContext(),"正在加载...",Toast.LENGTH_SHORT).show();
                if(endIndex > Images.imageUrls.length){
                    //确定结束加载位置
                    endIndex = Images.imageUrls.length;
                }
                for (int i=startIndex;i<endIndex;i++){

                    //1 初始化加载图片，2 滑动加载图片
                    LoadImageTask task = new LoadImageTask();
                    taskCollection.add(task);
                    task.execute(Images.imageUrls[i]);
                }
                page++;
            }else{
                //已经滑动到最下面
                Toast.makeText(getContext(),"已经没有更多图片",Toast.LENGTH_SHORT).show();
            }
        }else{
          Toast.makeText(getContext(),"未发现SD卡",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 遍历ImageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图
     */
    public void checkVisibility(){
        for(int i=0;i<imageViewList.size();i++){
            ImageView imageView = imageViewList.get(i);
            //这个应该是当前imageView的上边缘位置
            int borderTop = (Integer) imageView.getTag(R.string.border_top);
            //这个应该是当前imageView的下边缘位置
            int borderBottom = (Integer) imageView.getTag(R.string.border_bottom);

            if(borderBottom > getScrollY() && borderTop < getScrollY()+scrollViewHeight){
                //如果imageView的下边缘大于屏幕可见区域的上边缘，且imageView的上边缘小于屏幕课件区域的下边缘
                //也就是在可视区域内
                String imageUrl = (String) imageView.getTag(R.string.image_url);
                //从内存缓存中获取图片
                Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
                if(bitmap !=null){
                    imageView.setImageBitmap(bitmap);
                }else{
                    //内存缓存中没有，就去异步下载
                    //滑动检查是否可见，对于可见ImageView就去加载图片，不可见的隐藏
                    LoadImageTask task = new LoadImageTask(imageView);
                    task.execute(imageUrl);
                }
            }
        }
    }

    /**
     * 判断手机是否有SD卡
     * @return 有SD卡返回true，没有返回false
     */
    private boolean hasSDCard(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 异步下载图片任务
     */
    class LoadImageTask extends AsyncTask<String,Void,Bitmap>{

        /**
         * 图片的URL地址
         */
        private String mImageUrl;

        /**
         * 可重复使用的ImageView
         */
        private ImageView mImageView;

        public LoadImageTask(){}

        /**
         * 将可重复使用的ImageView传入
         * @param mImageView
         */
        public LoadImageTask(ImageView mImageView){
            this.mImageView = mImageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            //这里就是上面task.execute(imageUrl)中传进来的url值
            mImageUrl = params[0];
            Bitmap imageBitmap = imageLoader.getBitmapFromMemoryCache(mImageUrl);
            if(imageBitmap==null){
                //这里去下载图片
                imageBitmap = loadImage(mImageUrl);
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //这个方法就是接受doInBackground传过来到的bitmap
            if(bitmap!=null) {
                //获取实际图片的宽与屏幕列宽
                double ratio = bitmap.getWidth() / (columnWidth*1.0);
                //获取与列宽对应的高度
                int scaledHeight = (int) (bitmap.getHeight()/ratio);

                addImage(bitmap,columnWidth,scaledHeight);
            }
            taskCollection.add(this);
        }

        /**
         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡中获取，否则就从网络上下载
         * @param imageUrl 图片的URL地址
         * @return 加载到内存的图片
         */
        private Bitmap loadImage(String imageUrl){
            File imageFile = new File(getImagePath(imageUrl));
            if(!imageFile.exists()){
                //下载图片到SDCard路径下
                downloadImage(imageUrl);
            }
            if(imageUrl != null){
                //从SD卡中获取图片并压缩
                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(imageFile.getPath(),columnWidth);
                if(bitmap !=null){
                    //将已经下载好的图片保存到缓存中
                    imageLoader.addBitmapToMemoryCache(imageUrl,bitmap);
                    return bitmap;
                }
            }
            return null;
        }

        /**
         * 向ImageView中添加图片
         * @param bitmap 要添加的图片
         * @param imageWidth 图片宽度
         * @param imageHeight 图片长度
         */
        private void addImage(Bitmap bitmap,int imageWidth,int imageHeight){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth,imageHeight);
            if(mImageView!=null){
                mImageView.setImageBitmap(bitmap);
            }else{
                //在加载图片的时候使用，就是初始化加载图片和加载第二页图片
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);

                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imageView.setPadding(5,5,5,5);
                imageView.setTag(R.string.image_url,mImageUrl);

                //寻找到在哪里插入这个ImageView
                findColumnToAdd(imageView,imageHeight).addView(imageView);

                imageViewList.add(imageView);
            }
        }


        /**
         * 找到在哪一列添加ImageView
         * 原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加ImageView的图片
         *
         * @param imageView 插入的ImageView
         * @param imageHeight ImageView需要的高度
         * @return 应该填图片的那一列
         */
        private LinearLayout findColumnToAdd(ImageView imageView,int imageHeight){
            if(firstColumnHeight <= secondColumnHeight){
                //第一列比第二列高度低
                if(firstColumnHeight <= thirdColumnHeight){
                    //第一列比第三列低
                    imageView.setTag(R.string.border_top,firstColumnHeight);
                    firstColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom,firstColumnHeight);
                    return firstColumn;
                }
                //第三列最低
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }else{
                if(secondColumnHeight <= thirdColumnHeight){
                    //第二列最低
                    imageView.setTag(R.string.border_top,secondColumnHeight);
                    secondColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom,secondColumnHeight);
                    return secondColumn;
                }
                //第三列最低
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }
        }

        /**
         * 将图片下载到SD卡缓存起来
         * @param imageUrl 图片的URL地址
         */
        private void downloadImage(String imageUrl){
            HttpURLConnection con = null;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            FileOutputStream fos = null;
            File imageFile = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();

                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(15 * 1000);
                con.setDoInput(true);
                con.setDoOutput(true);

                //con.getInputStream就已经下载完毕了
                bis = new BufferedInputStream(con.getInputStream());
                //接下来就是保存到本地文件中，得到图片想要保存的本地路径
                imageFile = new File(getImagePath(imageUrl));
                //获取写入流写到文件中
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                byte[] b = new byte[1024];
                int length;
                while ((length = bis.read(b))!=-1){
                    bos.write(b,0,length);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try{
                    if(bis!=null){
                        bis.close();
                    }
                    if(bos!=null){
                        bos.close();
                    }
                    if(con!=null){
                        con.disconnect();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (imageFile !=null){
                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
                        imageFile.getPath(),columnWidth);
                if(bitmap != null){
                    imageLoader.addBitmapToMemoryCache(imageUrl,bitmap);
                }
            }
        }

        /**
         * 获取图片的本地存储路径
         * @param imageUrl 图片的URL地址
         * @return
         */
        private String getImagePath(String imageUrl){
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            //图片名字
            String imageName = imageUrl.substring(lastSlashIndex+1);
            //本地SDCard存储路径
            String imageDir = Environment.getExternalStorageDirectory().getParent()+"/PhotoWallFalls/";

            File file = new File(imageDir);
            if(!file.exists()){
                //创建文件夹
                file.mkdir();
            }
            String imagePath = imageDir+imageName;
            return imagePath;
        }

    }
}
