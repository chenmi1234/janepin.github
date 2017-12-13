package com.lianpos.devfoucs.contacts.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.lianpos.activity.MainActivity
import com.lianpos.activity.R
import com.lianpos.common.Common
import com.lianpos.firebase.BaseActivity
import com.lianpos.util.CallAPIUtil
import io.realm.Realm
import java.net.URLEncoder

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
    var addToContast: Button? = null
    var addFriendUseId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        init()
        val intent = intent
        val addFriendNameStr = intent.getStringExtra("resultUserName")
        val addFriendPhoneStr = intent.getStringExtra("resultUserPhone")
        val companyNameStr = intent.getStringExtra("resultShopName")
        addFriendUseId = intent.getStringExtra("resultUserId")

        addFriendName!!.setText(addFriendNameStr)
        addFriendPhone!!.setText(addFriendPhoneStr)
        company_name!!.setText(companyNameStr)
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
        addToContast = findViewById(R.id.addToContast) as Button
    }

    /**
     * 初始化点击事件
     */
    private fun initEvent() {
        add_friend_back!!.setOnClickListener(this)
        addToContast!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_friend_back -> finish()
            R.id.addToContast -> {
                // 从本地缓存中获取城市信息
                val sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE)
                val ywUserId = sharedPreferences.getString("result_id", "")
                runAddFrieend(ywUserId, addFriendUseId)
            }
        }
    }

    /**
     * 添加好友
     * post请求后台
     */
    @Throws(InterruptedException::class)
    private fun runAddFrieend(ywUserId: String, userId: String) {
        //处理注册逻辑
        val t1 = Thread(Runnable {
            val jsonObject = JSONObject()
            var json = ""
            try {
                jsonObject.put("yw_user_id", ywUserId)
                jsonObject.put("user_id", userId)
                json = JSONObject.toJSONString(jsonObject)//参数拼接成的String型json
                json = URLEncoder.encode(json, "UTF-8")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val result = CallAPIUtil.ObtainFun(json, Common.addFriendUserUrl)

            if (!result.isEmpty()) {
                val paramJson = JSON.parseObject(result)
                val resultFlag = paramJson.getString("result_flag")
                if ("1" == resultFlag) {
//                    Looper.prepare()
//                    Toast.makeText(this, "请求成功，等待商家确认添加！", Toast.LENGTH_SHORT).show()
//                    Looper.loop()
                    val intent1 = Intent()
                    intent1.setClass(this@AddFriendActivity, MainActivity::class.java)
                    startActivity(intent1)
                }
            }
        })
        t1.start()
        t1.join()
    }

}