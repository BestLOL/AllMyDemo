package com.qiuyi.cn.circlebar;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv_appMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_appMsg = (TextView) findViewById(R.id.tv_appMsg);

        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -1);
        Calendar endCal = Calendar.getInstance();

        UsageStatsManager manager=(UsageStatsManager)getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
        /*
        List<UsageStats> stats=manager.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY,beginCal.getTimeInMillis(),endCal.getTimeInMillis());*/

        long time =System.currentTimeMillis()-24*60*60*1000;
        List<UsageStats> list=manager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());

        StringBuilder sb=new StringBuilder();
        for(UsageStats us:list){
            try {
                PackageManager pm=getApplicationContext().getPackageManager();

                ApplicationInfo applicationInfo=pm.getApplicationInfo(us.getPackageName(),PackageManager.GET_META_DATA);

                if((applicationInfo.flags & applicationInfo.FLAG_SYSTEM)<=0){


                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                    String t=format.format(new Date(us.getLastTimeUsed()));

                    String minutes = us.getTotalTimeInForeground()*1.0f/1000/60+"";
                    /*String hour = us.getTotalTimeInForeground()/1000/60/60+"";*/


                    sb.append(pm.getApplicationLabel(applicationInfo)+"\t"
                            +t+"\t"
                            +"使用时间："+minutes+"分钟"+"\t"
                    +us.getClass().getDeclaredField("mLaunchCount").getInt(this));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tv_appMsg.setText(sb.toString());

        //startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    //获取APP信息
    private String getForegroundApp() {

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        long endt = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
        long statt = calendar.getTimeInMillis();//开始时间

        UsageStatsManager usageStatsManager=(UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);

        //获取一个月内的信息
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY,statt,endt);

        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        }

        UsageStats recentStats = null;

        for (UsageStats usageStats : queryUsageStats) {

            if(recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()){
                recentStats = usageStats;
            }
        }

        return recentStats.getPackageName();
    }
}
