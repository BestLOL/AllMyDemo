package com.qiuyi.cn.filemanager.FileControll;

/**
 * Created by Administrator on 2018/3/15.
 * 文件大类
 */
public class FileType {


    private int image;
    private String name;
    private int size;

    private int showType;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
