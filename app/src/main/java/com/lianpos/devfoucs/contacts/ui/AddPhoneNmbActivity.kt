package com.lianpos.devfoucs.contacts.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.lianpos.activity.R
import com.lianpos.firebase.BaseActivity

/**
 * 检索好友
 * Created by wangshuai on 2017/11/07 .
 */

class AddPhoneNmbActivity : BaseActivity(), View.OnClickListener {

    private var add_friend_back: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phone_number)
        init()
    }

    private fun init() {
        // 初始化控件
        initActivity()
        // 初始化点击事件
        initEvent()
    }

    /**
     * 初始化控件
     */
    private fun initActivity() {
        add_friend_back = findViewById(R.id.add_friend_back) as ImageView
    }

    /**
     * 初始化点击事件
     */
    private fun initEvent() {
        add_friend_back!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_friend_back -> finish()
        }
    }

}