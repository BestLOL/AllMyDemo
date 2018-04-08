package com.qiuyi.cn.mysqlite;

/**
 * Created by Administrator on 2018/3/12.
 * Book的介绍类，与Book一对一
 */
public class Introduce {

    private Integer id;
    //引言
    private String guide;
    //摘要
    private String digest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
