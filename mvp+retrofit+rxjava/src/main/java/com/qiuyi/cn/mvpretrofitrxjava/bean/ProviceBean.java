package com.qiuyi.cn.mvpretrofitrxjava.bean;

/**
 * Created by Administrator on 2018/4/25.
 */
public class ProviceBean {
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
        return "ProviceBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
