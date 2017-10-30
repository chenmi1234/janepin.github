package com.lianpos.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 跳转页面共通
 * Created by wangshuai on 2017/10/30 0030.
 */

@SuppressLint("Registered")
public class JumpUtil extends Activity{
    // 不带参数的页面跳转
    public void jumpFun(Context packageContext, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    // 带参数的页面跳转
    public void jumpDataFun(Context packageContext, Class<?> cls, String name, String value) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(name, value);
        intent.putExtras(bundle);
        intent.setClass(packageContext, cls);
        packageContext.startActivity(intent);
    }

    // 被跳转页面的数据接收
    public String receiveDataFun(String name) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String receiveData = bundle.getString(name);
        return receiveData;
    }
}
