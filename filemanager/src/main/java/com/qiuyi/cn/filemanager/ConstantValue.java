package com.qiuyi.cn.filemanager;

/**
 * Created by Administrator on 2018/3/22.
 */
public class ConstantValue {

    public static final String QQ_IMAGE_KEY = ".*[Qq][Qq][_][Ii][m][a][g][e][s].*";//QQ
    public static final String WECHART_IMAGE_KEY = ".*[Ww][e][i][Xx][i][n].*";//微信
    public static final String SCREENSHOT_KEY = ".*[Ss][c][r][e][e][n].*";//截图
    public static final String CAMERA_KEY = ".*[D][C][I][M][/][C][a][m][e][r][a].*";//相机
    public static final String GIF_KEY = ".*\\.[g][i][f]";//GIF

    /**文档类型*/
    public static final int TYPE_DOC = 0;
    /**apk类型*/
    public static final int TYPE_APK = 1;
    /**压缩包类型*/
    public static final int TYPE_ZIP = 2;
    public static final int Type_MP3 = 3;



    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }
}
