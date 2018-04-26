package com.qiuyi.cn.mvpretrofitrxjava.bean;

/**
 * Created by Administrator on 2018/4/26.
 */
public class RegionBean {
    private int id;
    private String name;
    private String weather_id;

    @Override
    public String toString() {
        return "RegionBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weather_id='" + weather_id + '\'' +
                '}';
    }

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

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }
}
