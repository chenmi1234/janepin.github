package com.lianpos.db

import android.app.Application
import android.util.Log
import cn.jpush.android.api.JPushInterface

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by wangshuai on 2017/11/14 0014.
 */

class MyApplication : Application() {
    private val TAG = "JPush"
    override fun onCreate() {
        Log.d(TAG, "[ExampleApplication] onCreate")
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)

        JPushInterface.setDebugMode(true)    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this)            // 初始化 JPush
    }
}