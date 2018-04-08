package com.qiuyi.cn.phonebkrt.Permission;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2018/4/1.
 * 获取基本权限
 */
public interface GetPermission {

    //得到基础权限
    void getPermission(Activity activity, String[] permissions);

}
