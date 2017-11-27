package com.lianpos.db

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by wangshuai on 2017/11/14 0014.
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}