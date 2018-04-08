package com.qiuyi.cn.phonesms.Bean;

/**
 * Created by Administrator on 2018/1/30.
 * 手机通话记录实体类
 */
public class CallBean {
    private String mNumber; // 号码
    private String mName; // 在通讯录中的联系人
    private int mCallLogType; // 通话记录的状态 打入，打出，未接
    private Long mCallLogDate; // 通话记录日期
    private int mCallLogDuration; // 通话记录时长

    public CallBean() {
    }

    public CallBean(String mNumber, String mName, int mCallLogType, Long mCallLogDate, int mCallLogDuration) {
        this.mNumber = mNumber;
        this.mName = mName;
        this.mCallLogType = mCallLogType;
        this.mCallLogDate = mCallLogDate;
        this.mCallLogDuration = mCallLogDuration;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmCallLogType() {
        return mCallLogType;
    }

    public void setmCallLogType(int mCallLogType) {
        this.mCallLogType = mCallLogType;
    }

    public Long getmCallLogDate() {
        return mCallLogDate;
    }

    public void setmCallLogDate(Long mCallLogDate) {
        this.mCallLogDate = mCallLogDate;
    }

    public int getmCallLogDuration() {
        return mCallLogDuration;
    }

    public void setmCallLogDuration(int mCallLogDuration) {
        this.mCallLogDuration = mCallLogDuration;
    }

    @Override
    public String toString() {
        return "CallBean{" +
                "mNumber='" + mNumber + '\'' +
                ", mName='" + mName + '\'' +
                ", mCallLogType=" + mCallLogType +
                ", mCallLogDate=" + mCallLogDate +
                ", mCallLogDuration=" + mCallLogDuration +
                '}';
    }
}
