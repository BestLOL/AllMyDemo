package com.qiuyi.cn.filemanager;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.provider.MediaStore;
import android.util.Log;

import com.qiuyi.cn.filemanager.bean1.FileBean;
import com.qiuyi.cn.filemanager.bean1.ImageBean;
import com.qiuyi.cn.filemanager.bean1.MusicBean;
import com.qiuyi.cn.filemanager.bean1.VideoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/21.
 * 获取手机中各类文件
 */
public class MyFileManager {

    private static Context mContext;

    private MyFileManager(){
    }

    private static class MyFileManagerHolder{
        private static final MyFileManager myFileManager = new MyFileManager();
    }

    public static MyFileManager getInstance(Context context){
        mContext = context;
        return MyFileManagerHolder.myFileManager;
    }

    /*
    * 1 获取手机内所有图片
    * */
    public List<ImageBean> getImages(){
        List<ImageBean> listImg = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Images.Media.DATE_TAKEN+" desc");

            if(cursor.moveToFirst()){
                do {
                    //名字
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    //路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //日期
                    Long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                    //大小
                    Long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                    if(size == 0){
                        continue;
                    }
                    //文件类型
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                    //经纬度
                    String LATITUDE = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
                    String LONGITUDE = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
                    String location = null;
                    //根据经纬度查询拍照地点
                    if(LATITUDE != null && LONGITUDE!=null){
                        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(LATITUDE),
                                    Double.parseDouble(LONGITUDE),1);
                            StringBuilder builder = new StringBuilder();
                            if (addresses.size() > 0) {
                                Address address = addresses.get(0);
                                builder.append(address.getAddressLine(0)).append("\n");
                                location = builder.toString();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //标记图片文件
                    ImageBean information = new ImageBean(name, path, date, size, type, location);
                    information.setFiletype(1);

                    listImg.add(information);
                    Log.e("img",information.getName()+"时间"+information.getDate());
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return listImg;
    }


    /*
    * 2 获取手机内所有音乐
    *
    * */
    public List<MusicBean> getMusics(){
        List<MusicBean> listMusic = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DATE_MODIFIED+" desc");
            if(cursor.moveToFirst()){
                do {
                    //歌曲id
                    String music_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    //歌曲名
                    String music_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    //歌曲专辑名
                    String music_album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    //持续时长
                    Long music_duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    //大小
                    Long music_size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    if(music_size == 0){
                        continue;
                    }
                    //修改日期
                    Long music_date = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED));
                    //路径
                    String music_path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    //歌手名
                    String music_artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                    MusicBean music = new MusicBean(music_id,music_name,music_album,music_duration,music_size,music_date,music_path,music_artist);
                    music.setFiletype(2);

                    listMusic.add(music);

                    String date = new SimpleDateFormat("yyyy-MM-dd ").format(new Date(music_date*1000L));
                    Log.e("music",music.getName()+"时间"+date);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return listMusic;
    }


    /*
    * 3 获取手机内所有视频文件
    * */
    public List<VideoBean> getVideos() {

        List<VideoBean> listVideos = new ArrayList<VideoBean>();

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DATE_TAKEN+" desc");
            if(cursor.moveToFirst()){
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                    String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                    if(size == 0){
                        continue;
                    }
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                    long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));//修改时间

                    VideoBean videoBean = new VideoBean(id,name,resolution,path,size,duration,date);
                    videoBean.setFiletype(2);
                    listVideos.add(videoBean);
                    Log.e("video",videoBean.getName());
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return listVideos;
    }


    /**
     * 4 通过文件类型得到相应文件的集合
     * 文档，压缩包，apk安装包
     **/
    public List<FileBean> getFilesByType(int fileType) {
        List<FileBean> listFiles = new ArrayList<FileBean>();
        // 扫描files文件库
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                    null, null, null, MediaStore.Files.FileColumns.DATE_MODIFIED+" desc");

            if(cursor.moveToFirst()){
                do {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));

                    //根据路径获取文件图片
                    if (FileUtils.getFileType(path) == fileType) {
                        if (!FileUtils.isExists(path)) {
                            continue;
                        }
                        Long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));

                        if(size == 0){
                            continue;
                        }

                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
                        Long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));

                        FileBean fileBean = new FileBean(name,path,size,time,FileUtils.getFileIconByPath(path));
                        fileBean.setFiletype(2);
                        listFiles.add(fileBean);


                        Log.e("fileInfo",fileBean.getName()+"时间"+new SimpleDateFormat("yyyy-MM-dd").format(new Date(fileBean.getTime()*1000L)));
                    }
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return listFiles;
    }

}
