package com.lianpos.devfoucs.contacts.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.lianpos.activity.R
import com.lianpos.common.Common
import com.lianpos.entity.JanePinBean
import com.lianpos.firebase.BaseActivity
import com.lianpos.util.CallAPIUtil
import com.lianpos.util.CheckInforUtils
import com.lianpos.util.WeiboDialogUtils
import io.realm.Realm
import io.realm.RealmResults
import java.net.URLEncoder




/**
 * 检索好友
 * Created by wangshuai on 2017/11/07 .
 */

class AddPhoneNmbActivity : BaseActivity(), View.OnClickListener {

    private var add_friend_back: ImageView? = null
    private var seachFriend: EditText? = null;
    internal lateinit var realm: Realm
    private var mWeiboDialog: Dialog? = null
    internal var guests: RealmResults<JanePinBean>? = null

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
        seachFriend = findViewById(R.id.seachFriend) as EditText
        // 从本地缓存中获取城市信息
        val sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE)
        val ywUserId = sharedPreferences.getString("result_id", "")

        seachFriend!!.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!seachFriend!!.getText().toString().isEmpty()) {
                    if (CheckInforUtils.isMobile(seachFriend!!.getText().toString())) {
                        try {
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this@AddPhoneNmbActivity, "加载中...")
                            runAddPhoneNmb(ywUserId, seachFriend!!.getText().toString())
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    } else {
                        Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show()
                }
            }
            false
        })

    }

    /**
     * 登录方法
     * post请求后台
     */
    @Throws(InterruptedException::class)
    private fun runAddPhoneNmb(ywUserId: String, keyPhone: String) {
        //处理注册逻辑
        val t1 = Thread(Runnable {
            val jsonObject = JSONObject()
            var json = ""
            try {
                jsonObject.put("yw_user_id", ywUserId)
                jsonObject.put("key_phone", keyPhone)
                json = JSONObject.toJSONString(jsonObject)//参数拼接成的String型json
                json = URLEncoder.encode(json, "UTF-8")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val result = CallAPIUtil.ObtainFun(json, Common.querySysUserUrl)

            if (!result.isEmpty()) {
                val paramJson = JSON.parseObject(result)
                val resultFlag = paramJson.getString("result_flag")
                val resultUserName = paramJson.getString("USERNAME")
                val resultUserPhone = paramJson.getString("PHONE")
                val resultShopName = paramJson.getString("NAME")
                if ("1" == resultFlag) {
                    realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    val janePinBean = realm.createObject(JanePinBean::class.java) // Create a new object
                    janePinBean.addFriendName = resultUserName
                    janePinBean.addFriendPhone = resultUserPhone
                    janePinBean.addFriendShopName = resultShopName
                    realm.commitTransaction()
                    intent.setClass(this@AddPhoneNmbActivity, AddFriendActivity::class.java)
                    startActivity(intent)
                }
            }
        })
        t1.start()
        t1.join()
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