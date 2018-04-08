package com.qiuyi.cn.phonesms.Bean;

/**
 * Created by Administrator on 2018/1/30.
 * 短信对象
 */
public class SmsBean {

    private String name;//发送人
    private String phoneNumber;//电话号码
    private String smsbody;//短信内容
    private Long date;//发送时间
    private int type;//类型：发送还是接受

    public SmsBean() {
    }

    public SmsBean(String name, String phoneNumber, String smsbody, Long date, int type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.smsbody = smsbody;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsbody() {
        return smsbody;
    }

    public void setSmsbody(String smsbody) {
        this.smsbody = smsbody;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsBean{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsbody='" + smsbody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
