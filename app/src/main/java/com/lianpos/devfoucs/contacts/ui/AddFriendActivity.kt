package com.lianpos.devfoucs.contacts.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lianpos.activity.R
import com.lianpos.entity.JanePinBean
import com.lianpos.firebase.BaseActivity
import io.realm.Realm

/**
 * 添加好友
 * Created by wangshuai on 2017/11/07 .
 */

class AddFriendActivity : BaseActivity(), View.OnClickListener {

    private var add_friend_back: ImageView? = null
    private var company_name: TextView? = null
    internal lateinit var realm: Realm
    private var addFriendName: TextView? = null
    private var addFriendPhone: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        init()
        realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        var guests = realm.where(JanePinBean::class.java).equalTo("id", 0).findAll()
        realm.commitTransaction()
//        var addFriendNameStr = ""
//        var addFriendPhoneStr = ""
//        var companyNameStr = ""
//        for (guest in guests) {
//            addFriendNameStr = guest.addFriendName
//            addFriendPhoneStr = guest.addFriendPhone
//            companyNameStr = guest.addFriendShopName
//        }

//        addFriendName!!.setText(addFriendNameStr)
//        addFriendPhone!!.setText(addFriendPhoneStr)
//        company_name!!.setText(companyNameStr)
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
        addFriendName = findViewById(R.id.addFriendName) as TextView
        addFriendPhone = findViewById(R.id.addFriendPhone) as TextView
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