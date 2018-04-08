package com.qiuyi.cn.phonesms.Info;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import com.qiuyi.cn.phonesms.Bean.CallBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 通话记录获取与导入
 */
public class CallInfo {
    private Context context;


    public CallInfo(Context context){
        this.context = context;
    }

    //得到通话记录
    public List<CallBean> getCallInfo() {
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         *
         */
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                new String[] { CallLog.Calls.CACHED_NAME// 通话记录的联系人
                        , CallLog.Calls.NUMBER// 通话记录的电话号码
                        , CallLog.Calls.DATE// 通话记录的日期
                        , CallLog.Calls.DURATION// 通话时长
                        , CallLog.Calls.TYPE }// 通话类型
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );

        List<CallBean> callBeanList = new ArrayList<>();
        //获取cursor中的数据
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            /*String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong*1000L));*/

            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
/*            String typeString = "";
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeString = "打入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeString = "打出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeString = "未接";
                    break;
                default:
                    break;
            }*/
            CallBean myCall = new CallBean();
            myCall.setmNumber(number); // 通话号码
            myCall.setmName(name); // 在通讯录中的联系人
            myCall.setmCallLogType(type); // 通话记录的状态 打入,打出,未接
            myCall.setmCallLogDate(dateLong); // 通话记录日期
            myCall.setmCallLogDuration(duration); // 通话记录时长
            callBeanList.add(myCall);
        }
        return callBeanList;
    }


    //将通话记录写入本地
    public void sendToSDCardCall(List<CallBean> listCall,Context context) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ContentValues values = new ContentValues();

        for(CallBean myCall:listCall){
            Log.e("CallMsg",myCall.toString());
            values.clear();
            values.put(CallLog.Calls.CACHED_NAME, myCall.getmName());
            values.put(CallLog.Calls.NUMBER, myCall.getmNumber());
            values.put(CallLog.Calls.TYPE, myCall.getmCallLogType());
            values.put(CallLog.Calls.DATE, myCall.getmCallLogDate());
            values.put(CallLog.Calls.DURATION, myCall.getmCallLogDuration());
            values.put(CallLog.Calls.NEW, "0");// 0已看1未看 ,由于没有获取默认全为已读
            ops.add(ContentProviderOperation
                    .newInsert(CallLog.Calls.CONTENT_URI).withValues(values)
                    .withYieldAllowed(true).build());
        }
        if (ops!=null){
            try {
                //真正实现添加
                context.getContentResolver().applyBatch(CallLog.AUTHORITY,ops);
                Log.e("CallMsg","添加成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
