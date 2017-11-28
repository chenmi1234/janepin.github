package com.lianpos.devfoucs.shoppingcart

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import butterknife.ButterKnife

import com.lianpos.activity.R
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity
import com.lianpos.devfoucs.shoppingcart.fragment.CommodityFragment
import com.lianpos.entity.JanePinBean
import com.lianpos.scancodeidentify.zbar.ZbarActivity
import io.realm.Realm

import java.util.ArrayList

/**
 * Created by wangshuai on 2017/11/6.
 * 小区商家商家详情
 */
class CommodityManagementActivity : FragmentActivity() {
    // 下划线标记
    private var group_line: View? = null
    // 服务产品
    private var commodityFragment: CommodityFragment? = null
    //上门服务Fragment
    private var fragments: ArrayList<Fragment>? = null
    //返回键
    private var merchant_back: ImageView? = null
    //新增商品
    private var addShopping: TextView? = null
    //标题名集合
    private val titleText: Array<RadioButton>? = null
    private var pager: ViewPager? = null
    //    private RadioGroup discount_layout;
    private var scanning_shop_tiaoxing: ImageView? = null
    private var serch_shop: EditText? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_merchant)
        ButterKnife.bind(this)
        realm = Realm.getDefaultInstance()
        val intert = intent
        initView()
        val addShopTiao = intert.getStringExtra("shopAdd")
        serch_shop!!.setText(addShopTiao)
    }

    private fun initView() {
        serch_shop = findViewById(R.id.serch_shop) as EditText
        pager = findViewById(R.id.pager) as ViewPager
        merchant_back = findViewById(R.id.merchant_back) as ImageView
        merchant_back!!.setOnClickListener { finish() }
        addShopping = findViewById(R.id.addShopping) as TextView
        addShopping!!.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@CommodityManagementActivity, IncreaseCommodityActivity::class.java)
            startActivity(intent)
        }
        group_line = findViewById(R.id.group_line)
        fragments = ArrayList()
        commodityFragment = CommodityFragment()
        fragments!!.add(commodityFragment!!)
        val fragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragments)
        pager!!.adapter = fragmentPagerAdapter
        fragmentPagerAdapter.setFragments(fragments!!)
        pager!!.setOnPageChangeListener(MyOnPageChangeListener())
        // 第一次启动时选中第0个tab
        pager!!.currentItem = 0
        pager!!.offscreenPageLimit = 2
        scanning_shop_tiaoxing = findViewById(R.id.scanning_shop_tiaoxing) as ImageView
        scanning_shop_tiaoxing!!.setOnClickListener {
            realm!!.beginTransaction()
            val janePinBean = realm!!.createObject<JanePinBean>(JanePinBean::class.java) // Create a new object
            janePinBean.NewlyAddedDistinguish = "3"
            realm!!.commitTransaction()
            val intent = Intent()
            intent.setClass(this@CommodityManagementActivity, ZbarActivity::class.java)
            intent.putExtra("addshop", "addshop")
            startActivity(intent)
        }
    }

    /**
     * 切换更换下划线状态
     *
     * @param position
     */
    private fun setVisible(position: Int) {
        when (position) {
            0 -> group_line!!.visibility = View.VISIBLE
        }
    }

    /**
     * 设置选中图标的文字颜色与
     * 下划线可见
     *
     * @param index
     */
    private fun chingeIndexView(index: Int) {
        for (i in titleText!!.indices) {
            titleText[i].isChecked = false
        }
        if (index < titleText.size) {
            titleText[index].isChecked = true
        }

    }


    private inner class MyFragmentPagerAdapter(private val fm: FragmentManager, private var fragments: ArrayList<Fragment>?) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return fragments!!.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }


        fun setFragments(fragments: ArrayList<Fragment>) {
            if (this.fragments != null) {
                var ft: FragmentTransaction? = fm.beginTransaction()
                for (f in this.fragments!!) {
                    ft!!.remove(f)
                }
                ft!!.commit()
                ft = null
                fm.executePendingTransactions()
            }
            this.fragments = fragments
            notifyDataSetChanged()
        }


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }

    }

    inner class MyOnPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            chingeIndexView(position)
            setVisible(position)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float,
                                    positionOffsetPixels: Int) {
        }
    }
}
