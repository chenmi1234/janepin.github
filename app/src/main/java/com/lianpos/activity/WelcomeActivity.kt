package com.lianpos.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window

/**
 * 欢迎页
 * Created by wangshuai on 2017/11/6.
 */

class WelcomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_welcome)
        val handler = Handler()
        //当计时结束,跳转至主界面
        handler.postDelayed({
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            this@WelcomeActivity.finish()
        }, 2000)
    }
}

