package com.qiuyi.cn.phonesms.Util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * Created by d on 2016/11/10.
 * Method:
 * getSDCardPath() 获取SDCard目录
 * getSDCardTotal() 总容量
 * getSDCardFree() 可用容量
 * createSDDirection()  创建目录
 * deleteSDDirection  删除目录
 * isFileExist 文件是否存在
 * deleteSDFile  删除文件
 * renameSDFile 修改文件或目录
 * copySDFileTo 拷贝单个文件
 * copySDFilesTo 拷贝所有
 * moveSDFileTo 移动单个
 * moveSDFilesTo 移动多个
 * creatDataDirection 建立私有目录
 * deleteDataFile 删除私有文件
 * deleteDataDir 删除私有目录
 * renameDataFile 修改私有文件名
 * copyDataFileTo 私有目录下负责
 * moveDataFileTo 移动私有文件
 * moveDataFilesTo移动私有目录下所有文件
 * deleteFile 删除文件
 * deleteDirection 删除目录
 * copyFileTo 拷贝
 * copyFilesTo
 * moveFileTo 移动
 * moveFilesTo
 *
 */
public class FileHelper {

    private Context mContext;
    private String SDPath;
    private String FileSpace;

    public FileHelper(Context context){
        this.mContext = context;
    }

    //表示SD卡存在并且可以读写
    public boolean isSDCardState(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

    /**获取SDCard文件路径*/
    public String getSDCardPath(){
        if(isSDCardState()){//如果SDCard存在并且可以读写
            SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            return SDPath;
        }else{
            return null;
        }
    }

    /**获取SDCard 总容量大小(MB)*/
    public long getSDCardTotal(){
        if(null != getSDCardPath()&&getSDCardPath().equals("")){
            StatFs statfs = new StatFs(getSDCardPath());
            //获取SDCard的Block总数
            long totalBlocks = statfs.getBlockCount();
            //获取每个block的大小
            long blockSize = statfs.getBlockSize();
            //计算SDCard 总容量大小MB
            long SDtotalSize = totalBlocks*blockSize/1024/1024;
            return SDtotalSize;
        }else{
            return 0;
        }
    }

    /**获取SDCard 可用容量大小(MB)*/
    public long getSDCardFree(){
        if(null != getSDCardPath()&&getSDCardPath().equals("")){
            StatFs statfs = new StatFs(getSDCardPath());
            //获取SDCard的Block可用数
            long availaBlocks = statfs.getAvailableBlocks();
            //获取每个block的大小
            long blockSize = statfs.getBlockSize();
            //计算SDCard 可用容量大小MB
            long SDFreeSize = availaBlocks*blockSize/1024/1024;
            return SDFreeSize;
        }else{
            return 0;
        }
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     *            要创建的目录名
     * @return 创建得到的目录
     */
    public File createSDDirection(String dirName) {
        File dir = new File(SDPath + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除SD卡上的目录
     *
     * @param dirName
     */
    public boolean deleteSDDirection(String dirName) {
        File dir = new File(SDPath + dirName);
        return deleteDirection(dir);
    }


    /**
     * 判断文件是否已经存在
     *
     * @param fileName
     *         要检查的文件名
     * @return boolean, true表示存在，false表示不存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPath + fileName);
        return file.exists();
    }

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean deleteSDFile(String fileName) {
        File file = new File(SDPath + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }



    /**
     * 修改SD卡上的文件或目录名
     * @param oldfileName 文件原来的名字
     * @param newFileName 文件新名字
     * @return
     */
    public boolean renameSDFile(String oldfileName, String newFileName) {
        File oleFile = new File(SDPath + oldfileName);
        File newFile = new File(SDPath + newFileName);
        return oleFile.renameTo(newFile);
    }



    /**
     * 拷贝SD卡上的单个文件
     * @param srcFileName 原路径
     * @param destFileName 目标路径
     * @return
     * @throws IOException
     */
    public boolean copySDFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(SDPath + srcFileName);
        File destFile = new File(SDPath + destFileName);
        return copyFileTo(srcFile, destFile);
    }



    /**
     * 拷贝SD卡上指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public boolean copySDFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(SDPath + srcDirName);
        File destDir = new File(SDPath + destDirName);
        return copyFilesTo(srcDir, destDir);
    }



    /**
     * 移动SD卡上的单个文件
     *
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */

    public boolean moveSDFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(SDPath + srcFileName);
        File destFile = new File(SDPath + destFileName);
        return moveFileTo(srcFile, destFile);
    }



    /**
     * 移动SD卡上的指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */

    public boolean moveSDFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(SDPath + srcDirName);
        File destDir = new File(SDPath + destDirName);
        return moveFilesTo(srcDir, destDir);
    }



    /**
     * 建立私有目录
     *
     * @param dirName
     * @return
     */
    public File creatDataDirection(String dirName) {
        File dir = new File(FileSpace + dirName);
        dir.mkdir();
        return dir;
    }



    /**
     * 删除私有文件
     *
     * @param fileName
     * @return
     */
    public boolean deleteDataFile(String fileName) {
        File file = new File(FileSpace + fileName);
        return deleteFile(file);
    }



    /**
     * 删除私有目录
     *
     * @param dirName
     * @return
     */
    public boolean deleteDataDir(String dirName) {
        File file = new File(FileSpace + dirName);
        return deleteDirection(file);
    }



    /**
     * 更改私有文件名
     *
     * @param oldName
     * @param newName
     * @return
     */

    public boolean renameDataFile(String oldName, String newName) {
        File oldFile = new File(FileSpace + oldName);
        File newFile = new File(FileSpace + newName);
        return oldFile.renameTo(newFile);
    }



    /**
     * 在私有目录下进行文件复制
     *
     * @param srcFileName
     *            ： 包含路径及文件名
     * @param destFileName
     * @return
     * @throws IOException
     */

    public boolean copyDataFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(FileSpace + srcFileName);
        File destFile = new File(FileSpace + destFileName);
        return copyFileTo(srcFile, destFile);
    }



    /**
     * 复制私有目录里指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */

    public boolean copyDataFilesTo(String srcDirName, String destDirName)

            throws IOException {
        File srcDir = new File(FileSpace + srcDirName);
        File destDir = new File(FileSpace + destDirName);
        return copyFilesTo(srcDir, destDir);
    }



    /**
     * 移动私有目录下的单个文件
     *
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */

    public boolean moveDataFileTo(String srcFileName, String destFileName)

            throws IOException {
        File srcFile = new File(FileSpace + srcFileName);
        File destFile = new File(FileSpace + destFileName);
        return moveFileTo(srcFile, destFile);
    }



    /**
     * 移动私有目录下的指定目录下的所有文件
     *
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */

    public boolean moveDataFilesTo(String srcDirName, String destDirName)

            throws IOException {
        File srcDir = new File(FileSpace + srcDirName);
        File destDir = new File(FileSpace + destDirName);
        return moveFilesTo(srcDir, destDir);
    }


    /**
     * 删除一个文件
     *
     * @param file
     * @return
     */

    public boolean deleteFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     */

    public boolean deleteDirection(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDirection(file);// 递归
            }
        }
        dir.delete();
        return true;
    }


    /**
     * 拷贝文件
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @return
     * @throws IOException
     */
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {

        if (srcFile.isDirectory() || destFile.isDirectory())
            return false;// 判断是否是文件
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */
    public boolean copyFilesTo(File srcDir, File destDir) throws IOException {

        if (!srcDir.isDirectory() || !destDir.isDirectory())
            return false;// 判断是否是目录
        if (!destDir.exists())
            return false;// 判断目标目录是否存在
        File[] srcFiles = srcDir.listFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory()) {
                File theDestDir = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }
        }
        return true;
    }

    /**
     * 移动一个文件
     *
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */

    public boolean moveFileTo(File srcFile, File destFile) throws IOException {

        boolean is_copy = copyFileTo(srcFile, destFile);

        if (!is_copy)
            return false;
        deleteFile(srcFile);
        return true;
    }

    /**
     * 移动目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */

    public boolean moveFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }

        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
                deleteFile(srcDirFiles[i]);
            } else if (srcDirFiles[i].isDirectory()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
                deleteDirection(srcDirFiles[i]);
            }
        }
        return true;
    }
}
