package com.lianpos.devfoucs.shoppingcart.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch

import com.lianpos.activity.R
import com.lianpos.entity.JanePinBean
import com.lianpos.firebase.BaseActivity
import com.lianpos.scancodeidentify.zbar.ZbarActivity
import com.lianpos.util.MyToggle
import com.lianpos.util.MyToggle.OnToggleStateListener

import butterknife.ButterKnife
import io.realm.Realm
import io.realm.RealmResults

/**
 * 新增商品
 * Created by wangshuai on 2017/11/09 .
 */

class IncreaseCommodityActivity : BaseActivity(), View.OnClickListener {
    //返回按钮
    private var commodity_back: ImageView? = null
    //开关工具类
    private var suitoggle: MyToggle? = null
    //套餐计算
    private var suitNum: LinearLayout? = null
    //套餐单位
    private var suitDanwei: RelativeLayout? = null
    //开关工具类
    private var splitToggle: MyToggle? = null
    //套餐计算
    private var splitNum: LinearLayout? = null
    //套餐单位
    private var splitDanwei: RelativeLayout? = null
    //扫描商品条形码
    private var scanningBar: ImageView? = null
    //组装拆分扫描条形码按钮
    private var shopChaifen: ImageView? = null
    internal var num1 = ""
    internal var request = ""
    internal var num2: String? = ""
    internal var request1 = ""
    //添加商品商品条码赋值
    private var shopEdittext: EditText? = null
    //组装拆分商品条码赋值
    private var chaifenEdittext: EditText? = null
    private var danweiChoose: RelativeLayout? = null
    private var guigeChoose: RelativeLayout? = null
    private var pinpaiChoose: RelativeLayout? = null
    private var addShopName: RelativeLayout? = null
    private var addShopSuggestedPrice: RelativeLayout? = null
    private var mSwitch: Switch? = null
    private var switch_zzcf: Switch? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_commodity)
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        ButterKnife.bind(this)
        realm = Realm.getDefaultInstance()
        init()
        val intert = intent
        num2 = intert.getStringExtra("zhuan")
        request1 = intert.getStringExtra("shopTiao")
        if (num2 != null) {
            if (num2 == "3") {
                chaifenEdittext!!.setText(request1)
            }
        }
    }

    private fun init() {
        //获取数据
        quiryDataFun()
        // 初始化控件
        initActivity()
        // 初始化点击事件
        initEvent()
        // 方法实现
        funRealization()
    }

    /**
     * 初始化控件
     */
    private fun initActivity() {
        commodity_back = findViewById(R.id.commodity_back) as ImageView
        suitoggle = findViewById(R.id.suitoggle) as MyToggle
        suitNum = findViewById(R.id.suitNum) as LinearLayout
        suitDanwei = findViewById(R.id.suitDanwei) as RelativeLayout
        splitToggle = findViewById(R.id.splitToggle) as MyToggle
        splitNum = findViewById(R.id.splitNum) as LinearLayout
        splitDanwei = findViewById(R.id.splitDanwei) as RelativeLayout
        scanningBar = findViewById(R.id.scanningBar) as ImageView
        shopEdittext = findViewById(R.id.shopEdittext) as EditText
        shopChaifen = findViewById(R.id.shopChaifen) as ImageView
        chaifenEdittext = findViewById(R.id.chaifenEdittext) as EditText
        danweiChoose = findViewById(R.id.danweiChoose) as RelativeLayout
        guigeChoose = findViewById(R.id.guigeChoose) as RelativeLayout
        pinpaiChoose = findViewById(R.id.pinpaiChoose) as RelativeLayout
        mSwitch = findViewById(R.id.switch_tc) as Switch
        switch_zzcf = findViewById(R.id.switch_zzcf) as Switch
        addShopName = findViewById(R.id.addShopName) as RelativeLayout
        addShopSuggestedPrice = findViewById(R.id.addShopSuggestedPrice) as RelativeLayout
    }

    /**
     * 初始化点击事件
     */
    private fun initEvent() {
        commodity_back!!.setOnClickListener(this)
        danweiChoose!!.setOnClickListener(this)
        guigeChoose!!.setOnClickListener(this)
        pinpaiChoose!!.setOnClickListener(this)
    }

    /**
     * 方法实现
     */
    private fun funRealization() {
        //设置开关显示所用的图片
        suitoggle!!.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft)
        //套餐初始为关闭
        suitNum!!.visibility = View.GONE
        suitDanwei!!.visibility = View.GONE
        switch_zzcf!!.isChecked = true
        //设置开关组装拆分图片
        splitToggle!!.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight)

        //设置开关的默认状态    true开启状态
        suitoggle!!.setToggleState(true)

        //设置开关的监听
        suitoggle!!.setOnToggleStateListener { state ->
            if (state) {
                suitoggle!!.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight)
                suitNum!!.visibility = View.VISIBLE
                suitDanwei!!.visibility = View.VISIBLE
                splitToggle!!.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight)
                splitNum!!.visibility = View.VISIBLE
                splitDanwei!!.visibility = View.VISIBLE
            } else {
                suitoggle!!.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft)
                suitNum!!.visibility = View.GONE
                suitDanwei!!.visibility = View.GONE
                splitToggle!!.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft)
                splitNum!!.visibility = View.GONE
                splitDanwei!!.visibility = View.GONE
            }
        }

        splitToggle!!.setOnToggleStateListener { state ->
            if (state) {
                splitToggle!!.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight)
                splitNum!!.visibility = View.VISIBLE
                splitDanwei!!.visibility = View.VISIBLE
                splitDanwei!!.visibility = View.VISIBLE
            } else {
                splitToggle!!.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft)
                splitNum!!.visibility = View.GONE
                splitDanwei!!.visibility = View.GONE
            }
        }

        scanningBar!!.setOnClickListener {
            realm!!.beginTransaction()
            val janePinBean = realm!!.createObject<JanePinBean>(JanePinBean::class.java) // Create a new object
            janePinBean.NewlyAddedDistinguish = "1"
            realm!!.commitTransaction()
            val intent = Intent()
            intent.setClass(this@IncreaseCommodityActivity, ZbarActivity::class.java!!)
            startActivity(intent)
        }

        shopChaifen!!.setOnClickListener {
            realm!!.beginTransaction()
            val janePinBean = realm!!.createObject<JanePinBean>(JanePinBean::class.java) // Create a new object
            janePinBean.NewlyAddedDistinguish = "2"
            realm!!.commitTransaction()
            val intent = Intent()
            intent.setClass(this@IncreaseCommodityActivity, ZbarActivity::class.java!!)
            startActivity(intent)
        }

        mSwitch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                suitNum!!.visibility = View.VISIBLE
                suitDanwei!!.visibility = View.VISIBLE
            } else {
                suitNum!!.visibility = View.GONE
                suitDanwei!!.visibility = View.GONE
            }
        }

        switch_zzcf!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                splitNum!!.visibility = View.VISIBLE
                splitDanwei!!.visibility = View.VISIBLE
                addShopName!!.visibility = View.VISIBLE
                addShopSuggestedPrice!!.visibility = View.VISIBLE
            } else {
                splitNum!!.visibility = View.GONE
                splitDanwei!!.visibility = View.GONE
                addShopName!!.visibility = View.GONE
                addShopSuggestedPrice!!.visibility = View.GONE
            }
        }

    }

    override fun onResume() {
        super.onResume()
        quiryDataFun()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (realm != null) {
            realm!!.close()
        }
        realm = Realm.getDefaultInstance()
        realm!!.beginTransaction()
        val guests = realm!!.where<JanePinBean>(JanePinBean::class.java).equalTo("id", 0).findAll()
        realm!!.commitTransaction()
        guests.removeAllChangeListeners()
    }

    private fun quiryDataFun() {
        realm = Realm.getDefaultInstance()
        realm!!.beginTransaction()
        val guests = realm!!.where<JanePinBean>(JanePinBean::class.java).equalTo("id", 0).findAll()
        realm!!.commitTransaction()
        var barCode: String? = ""
        for (guest in guests) {
            barCode = guest.NewlyAddedBarCode
        }
        if (barCode != null && !barCode.isEmpty()) {
            shopEdittext!!.setText(barCode)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.commodity_back -> finish()
            R.id.danweiChoose -> {
                val intent = Intent()
                intent.setClass(this@IncreaseCommodityActivity, ChooseListView::class.java!!)
                intent.putExtra("danwei", "1")
                startActivity(intent)
            }
            R.id.guigeChoose -> {
                val guigeintent = Intent()
                guigeintent.setClass(this@IncreaseCommodityActivity, ChooseListView::class.java!!)
                guigeintent.putExtra("guige", "2")
                startActivity(guigeintent)
            }
            R.id.pinpaiChoose -> {
                val pinpaiintent = Intent()
                pinpaiintent.setClass(this@IncreaseCommodityActivity, ChooseListView::class.java!!)
                pinpaiintent.putExtra("pinpai", "3")
                startActivity(pinpaiintent)
            }
        }
    }

}