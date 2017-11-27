package com.lianpos.devfoucs.contacts.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.lianpos.activity.R
import com.lianpos.devfoucs.reportform.activity.EditerAreaActivity
import com.lianpos.firebase.BaseActivity

/**
 * 添加好友
 * Created by wangshuai on 2017/11/07 .
 */

class AddFriendActivity : BaseActivity(), View.OnClickListener {

    private var add_friend_back: ImageView? = null
    internal var num1: String? = ""
    internal var request = ""
    private var company_name: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        init()
        val intert = intent
        num1 = intert.getStringExtra("page")
        request = intert.getStringExtra("codedContent")
        if (num1 != null) {
            if (num1 == "2") {
                company_name!!.text = request
            }
        }
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
        company_name = findViewById(R.id.company_name) as TextView
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