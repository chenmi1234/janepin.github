package com.lianpos.devfoucs.shoppingcart.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import butterknife.ButterKnife
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.lianpos.activity.R
import com.lianpos.common.Common
import com.lianpos.entity.JanePinBean
import com.lianpos.firebase.BaseActivity
import com.lianpos.scancodeidentify.zbar.ZbarActivity
import com.lianpos.util.CallAPIUtil
import com.lianpos.util.MyToggle
import io.realm.Realm
import java.net.URLEncoder

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
    private var kouweiChoose: RelativeLayout? = null
    private var addShopName: RelativeLayout? = null
    private var addShopSuggestedPrice: RelativeLayout? = null
    private var mSwitch: Switch? = null
    private var switch_zzcf: Switch? = null
    private var realm: Realm? = null
    private var danweiTextview: TextView? = null
    private var guigeTextview: TextView? = null
    private var pinpaiTextView: TextView? = null
    private var kouweiTextview: TextView? = null
    private var commodity_baocun: TextView? = null
    // 条码Str
    var barCode: String? = ""
    //商品名称
    var icSpNameStr: String? = ""
    //单位
    var icSpUnitStr: String? = ""
    //规格
    var icSpSizeStr: String? = ""
    //品牌
    var icSpBrandStr: String? = ""
    //进价
    var icJPriceStr: String? = ""
    //建议售价
    var icJyPriceStr: String? = ""
    //商品ID
    var icSpIdStr: String? = ""
    // 组装拆分条码Str
    var assemBarCode: String? = ""
    var increaseDanwei: String? = ""
    var increaseGuiger: String? = ""
    var increasePinpai: String? = ""
    var increaseKouwei: String? = ""
    private var spNameEditText: EditText? = null
    private var pifaPriceEditText: EditText? = null
    private var sellingPrice: EditText? = null
    private var taocanNumber: EditText? = null
    private var taocanItem: TextView? = null
    private var minUnitEditText: EditText? = null
    private var jyPriceEditText: EditText? = null
    private var zzcfNumberEditText: EditText? = null
    private var zzcfUnitTextView: TextView? = null
    private var zzcfUnitChooseText: TextView? = null
    private var zzcfShopNameEditText: EditText? = null
    private var zzcfPriceEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_commodity)
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        ButterKnife.bind(this)
        realm = Realm.getDefaultInstance()
        init()
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
        //返回
        commodity_back = findViewById(R.id.commodity_back) as ImageView
        suitoggle = findViewById(R.id.suitoggle) as MyToggle
        //套餐
        suitNum = findViewById(R.id.suitNum) as LinearLayout
        //套餐单位
        suitDanwei = findViewById(R.id.suitDanwei) as RelativeLayout
        splitToggle = findViewById(R.id.splitToggle) as MyToggle
        //拆分数量
        splitNum = findViewById(R.id.splitNum) as LinearLayout
        splitDanwei = findViewById(R.id.splitDanwei) as RelativeLayout
        scanningBar = findViewById(R.id.scanningBar) as ImageView
        //商品条码
        shopEdittext = findViewById(R.id.shopEdittext) as EditText
        shopChaifen = findViewById(R.id.shopChaifen) as ImageView
        chaifenEdittext = findViewById(R.id.chaifenEdittext) as EditText
        danweiChoose = findViewById(R.id.danweiChoose) as RelativeLayout
        guigeChoose = findViewById(R.id.guigeChoose) as RelativeLayout
        pinpaiChoose = findViewById(R.id.pinpaiChoose) as RelativeLayout
        kouweiChoose = findViewById(R.id.kouweiChoose) as RelativeLayout
        mSwitch = findViewById(R.id.switch_tc) as Switch
        switch_zzcf = findViewById(R.id.switch_zzcf) as Switch
        addShopName = findViewById(R.id.addShopName) as RelativeLayout
        addShopSuggestedPrice = findViewById(R.id.addShopSuggestedPrice) as RelativeLayout
        //基本单位
        danweiTextview = findViewById(R.id.danweiTextview) as TextView
        guigeTextview = findViewById(R.id.guigeTextview) as TextView
        pinpaiTextView = findViewById(R.id.pinpaiTextView) as TextView
        kouweiTextview = findViewById(R.id.kouweiTextview) as TextView
        commodity_baocun = findViewById(R.id.commodity_baocun) as TextView
        //商品名称
        spNameEditText = findViewById(R.id.spNameEditText) as EditText
        //批发价
        pifaPriceEditText = findViewById(R.id.pifaPriceEditText) as EditText
        //建议售价
        sellingPrice = findViewById(R.id.sellingPrice) as EditText
        taocanNumber = findViewById(R.id.taocanNumber) as EditText
        taocanItem = findViewById(R.id.taocanItem) as TextView
        minUnitEditText = findViewById(R.id.minUnitEditText) as EditText
        jyPriceEditText = findViewById(R.id.jyPriceEditText) as EditText
        zzcfNumberEditText = findViewById(R.id.zzcfNumberEditText) as EditText
        zzcfUnitTextView = findViewById(R.id.zzcfUnitTextView) as TextView
        zzcfUnitChooseText = findViewById(R.id.zzcfUnitChooseText) as TextView
        zzcfShopNameEditText = findViewById(R.id.zzcfShopNameEditText) as EditText
        zzcfPriceEditText = findViewById(R.id.zzcfPriceEditText) as EditText
    }

    /**
     * 初始化点击事件
     */
    private fun initEvent() {
        commodity_back!!.setOnClickListener(this)
        danweiChoose!!.setOnClickListener(this)
        guigeChoose!!.setOnClickListener(this)
        pinpaiChoose!!.setOnClickListener(this)
        kouweiChoose!!.setOnClickListener(this)
        commodity_baocun!!.setOnClickListener(this)
        taocanItem!!.setOnClickListener(this)
    }

    /**
     * 方法实现
     */
    private fun funRealization() {
        //设置开关显示所用的图片
        suitoggle!!.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft)
        //套餐初始为关闭
//        suitNum!!.visibility = View.GONE
//        suitDanwei!!.visibility = View.GONE
        mSwitch!!.isChecked = true
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
        var guests = realm!!.where<JanePinBean>(JanePinBean::class.java).equalTo("id", 0).findAll()
        realm!!.commitTransaction()
        guests = null
    }

    private fun quiryDataFun() {
        realm = Realm.getDefaultInstance()
        realm!!.beginTransaction()
        val guests = realm!!.where<JanePinBean>(JanePinBean::class.java).equalTo("id", 0).findAll()
        realm!!.commitTransaction()
        for (guest in guests) {
            barCode = guest.NewlyAddedBarCode
            icSpNameStr = guest.NewlyAddedName
            icSpUnitStr = guest.NewlyAddedUnit
            icSpSizeStr = guest.NewlyAddedSpecifications
            icSpBrandStr = guest.NewlyAddedBrand
            icJPriceStr = guest.NewlyAddedTradePrice
            icJyPriceStr = guest.NewlyAddedSuggestedPrice
            icSpIdStr = guest.NewlyAddedSpId
            assemBarCode = guest.NewlyAddedAssembleBarCode
            increaseDanwei = guest.NewlyAddedUnit
            increaseGuiger = guest.NewlyAddedSpecifications
            increasePinpai = guest.NewlyAddedBrand
            increaseKouwei = guest.NewlyAddedKouwei
        }
        initActivity()
        //商品条码
        if (barCode != null && !barCode!!.isEmpty()) {
            shopEdittext!!.setText(barCode)
        }

        //商品名称
        if (icSpNameStr != null && !icSpNameStr!!.isEmpty()) {
            spNameEditText!!.setText(icSpNameStr)
        }

        //商品基本单位
        if (icSpUnitStr != null && !icSpUnitStr!!.isEmpty()) {
            danweiTextview!!.setText(icSpUnitStr)
        }

        //商品规格
        if (icSpSizeStr != null && !icSpSizeStr!!.isEmpty()) {
            guigeTextview!!.setText(icSpSizeStr)
        }

        //商品品牌
        if (icSpBrandStr != null && !icSpBrandStr!!.isEmpty()) {
            pinpaiTextView!!.setText(icSpBrandStr)
        }

        //商品批发价
        if (icJPriceStr != null && !icJPriceStr!!.isEmpty()) {
            pifaPriceEditText!!.setText(icJPriceStr)
        }

        //商品批发价
        if (icJyPriceStr != null && !icJyPriceStr!!.isEmpty()) {
            sellingPrice!!.setText(icJyPriceStr)
        }

        //组装拆分条码
        if (assemBarCode != null && !assemBarCode!!.isEmpty()) {
            chaifenEdittext!!.setText(assemBarCode)
        }
        //单位
        if (increaseDanwei != null && !increaseDanwei!!.isEmpty()) {
            danweiTextview!!.setText(increaseDanwei)
        }
        //规格
        if (increaseGuiger != null && !increaseGuiger!!.isEmpty()) {
            guigeTextview!!.setText(increaseGuiger)
        }
        //品牌
        if (increasePinpai != null && !increasePinpai!!.isEmpty()) {
            pinpaiTextView!!.setText(increasePinpai)
        }
        //口味
        if (increaseKouwei != null && !increaseKouwei!!.isEmpty()) {
            kouweiTextview!!.setText(increaseKouwei)
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
            R.id.kouweiChoose -> {
                val pinpaiintent = Intent()
                pinpaiintent.setClass(this@IncreaseCommodityActivity, ChooseListView::class.java!!)
                pinpaiintent.putExtra("kouwei", "4")
                startActivity(pinpaiintent)
            }
            R.id.commodity_baocun -> {

                // 从本地缓存中获取城市信息
                val sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE)
                val ywUserId = sharedPreferences.getString("result_id", "")

                try {
                    var shopEdittextStr = shopEdittext!!.text.toString()
                    var spNameEditTextStr = spNameEditText!!.text.toString()
                    var danweiTextviewStr = danweiTextview!!.text.toString()
                    var guigeTextviewStr = guigeTextview!!.text.toString()
                    var pinpaiTextViewStr = pinpaiTextView!!.text.toString()
                    var kouweiTextviewStr = kouweiTextview!!.text.toString()
                    var pifaPriceEditTextStr = pifaPriceEditText!!.text.toString()
                    var sellingPriceStr = sellingPrice!!.text.toString()
                    var taocanItemStr = taocanItem!!.text.toString()
                    var taocanNumberStr = taocanNumber!!.text.toString()
                    var minUnitEditTextStr = minUnitEditText!!.text.toString()
                    var jyPriceEditTextStr = jyPriceEditText!!.text.toString()
                    var zzcfNumberEditTextStr = zzcfNumberEditText!!.text.toString()
                    var zzcfUnitTextViewStr = zzcfUnitTextView!!.text.toString()
                    var zzcfUnitChooseTextStr = zzcfUnitChooseText!!.text.toString()
                    var chaifenEdittextStr = chaifenEdittext!!.text.toString()
                    var zzcfShopNameEditTextStr = zzcfShopNameEditText!!.text.toString()
                    var zzcfPriceEditTextStr = zzcfPriceEditText!!.text.toString()

                    runNewAddShop(ywUserId,shopEdittextStr,spNameEditTextStr,danweiTextviewStr,
                            guigeTextviewStr,pinpaiTextViewStr,kouweiTextviewStr,pifaPriceEditTextStr,
                            sellingPriceStr,taocanItemStr,taocanNumberStr,
                            minUnitEditTextStr,jyPriceEditTextStr,zzcfNumberEditTextStr,
                            zzcfUnitTextViewStr, zzcfUnitChooseTextStr,
                            chaifenEdittextStr,zzcfShopNameEditTextStr,zzcfPriceEditTextStr)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            R.id.taocanItem -> {
                val pinpaiintent = Intent()
                pinpaiintent.setClass(this@IncreaseCommodityActivity, ChooseListView::class.java)
                pinpaiintent.putExtra("danwei", "1")
                startActivity(pinpaiintent)
            }

        }
    }

    /**
     * 新增商品
     * post请求后台
     */
    @Throws(InterruptedException::class)
    private fun runNewAddShop(ywUserId: String, barcode: String, spName: String?,
                              increaseDanwei: String?, increaseGuiger: String?, increasePinpai: String?,
                              increaseKouwei: String?, pifaPriceEditText: String?, sellingPrice: String?,
                              setCount: String?, setUnit: String?, setUnitCount: String?, setPrice: String?,
                              firstCount: String?, firstSingleUnit: String?, firstUnit: String?, firstCode: String?,
                              firstSpName: String?, firstSpPurchasing: String?) {
        //处理注册逻辑
        val t1 = Thread(Runnable {
            val jsonObject = JSONObject()
            var json = ""
            try {
                jsonObject.put("yw_user_id", ywUserId)
                jsonObject.put("barcode", barcode)
                jsonObject.put("sp_name", spName)
                jsonObject.put("sp_unit", increaseDanwei)
                jsonObject.put("sp_standard", increaseGuiger)
                jsonObject.put("sp_brand", increasePinpai)
                jsonObject.put("sp_taste", increaseKouwei)
                jsonObject.put("sp_purchasing_price", pifaPriceEditText)
                jsonObject.put("sp_selling_price", sellingPrice)
                jsonObject.put("set_count", setCount)
                jsonObject.put("set_unit", setUnit)
                jsonObject.put("set_unit_count", setUnitCount)
                jsonObject.put("set_price", setPrice)
                jsonObject.put("first_count", firstCount)
                jsonObject.put("first_single_unit", firstSingleUnit)
                jsonObject.put("first_unit", firstUnit)
                jsonObject.put("first_code", firstCode)
                jsonObject.put("first_sp_name", firstSpName)
                jsonObject.put("first_sp_purchasing", firstSpPurchasing)
                json = JSONObject.toJSONString(jsonObject)//参数拼接成的String型json
                json = URLEncoder.encode(json, "UTF-8")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val result = CallAPIUtil.ObtainFun(json, Common.addShopUrl)

            if (!result.isEmpty()) {
                val paramJson = JSON.parseObject(result)
                val resultFlag = paramJson.getString("result_flag")
                if ("1" == resultFlag) {
                    finish();
                }
            }
        })
        t1.start()
        t1.join()
    }

}