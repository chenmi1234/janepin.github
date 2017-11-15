package com.lianpos.firebase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lianpos.util.NetBroadcastReceiver;
import com.lianpos.util.NetUtil;

/**
 * 继承Activity
 * Created by wangshuai on 2017/10/27 .
 */

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetEvevt {

    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        evevt = this;
        inspectNet();
    }
    /**
     * 初始化时判断有没有网络
     */
    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
        if (netMobile == NetUtil.NETWORK_WIFI) {
            System.out.println("inspectNet：连接wifi");
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            System.out.println("inspectNet:连接移动数据");
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            System.out.println("inspectNet:当前没有网络");
        }
        return isNetConnect();


    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            return false;
        }else{
            return false;
        }
    }
}