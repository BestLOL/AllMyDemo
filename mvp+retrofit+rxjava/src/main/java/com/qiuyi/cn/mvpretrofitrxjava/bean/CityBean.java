package com.qiuyi.cn.mvpretrofitrxjava.bean;

/**
 * Created by Administrator on 2018/4/26.
 */
public class CityBean {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
