package com.lianpos.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import com.lianpos.fragment.*

/**
 * 对fragment的切换,底部图标颜色的切换
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
class MainActivity : FragmentActivity() {
    //要切换显示的四个Fragment
    private var idCardFragment: IDCardFragment? = null
    private var dynamicFragment: DynamicFragment? = null
    private var homeFragment: HomeFragment? = null
    private var messageFragment: MessageFragment? = null
    private var personFragment: PersonFragment? = null
    private var mExitTime: Long = 0

    private var currentId = R.id.iv_make// 当前选中id,默认是主页
    private var tvMain: TextView? = null
    private var tvDynamic: TextView? = null
    private var ivHome: TextView? = null
    private var tvMessage: TextView? = null
    private var tvPerson: TextView? = null//底部5个TextView

    private val tabClickListener = OnClickListener { v ->
        if (v.id != currentId) {//如果当前选中跟上次选中的一样,不需要处理
            changeSelect(v.id)//改变图标跟文字颜色的选中
            changeFragment(v.id)//fragment的切换
            currentId = v.id//设置选中id
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMain = findViewById(R.id.tv_main) as TextView
        tvDynamic = findViewById(R.id.tv_dynamic) as TextView
        tvDynamic = findViewById(R.id.tv_dynamic) as TextView
        tvMessage = findViewById(R.id.tv_message) as TextView
        tvPerson = findViewById(R.id.tv_person) as TextView
        ivHome = findViewById(R.id.iv_make) as TextView
        ivHome!!.isSelected = true//首页默认选中

        /**
         * 默认加载首页
         */
        if (homeFragment == null) {//如果为空先添加进来.不为空直接显示
            homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.main_container, homeFragment).commit()
        } else {
            supportFragmentManager.beginTransaction().show(homeFragment)
        }
        tvMain!!.setOnClickListener(tabClickListener)
        tvDynamic!!.setOnClickListener(tabClickListener)
        ivHome!!.setOnClickListener(tabClickListener)
        tvMessage!!.setOnClickListener(tabClickListener)
        tvPerson!!.setOnClickListener(tabClickListener)
    }

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private fun changeFragment(resId: Int) {
        val transaction = supportFragmentManager.beginTransaction()//开启一个Fragment事务
        //隐藏所有fragment
        hideFragments(transaction)
        if (resId == R.id.tv_main) {
            //身份卡
            //如果为空先添加进来.不为空直接显示
            if (idCardFragment == null) {
                idCardFragment = IDCardFragment()
                transaction.add(R.id.main_container, idCardFragment)
            } else {
                transaction.show(idCardFragment)
            }
        } else if (resId == R.id.tv_dynamic) {
            //联系人
//            if (dynamicFragment == null) {
                dynamicFragment = DynamicFragment()
                transaction.add(R.id.main_container, dynamicFragment)
//            } else {
//                transaction.show(dynamicFragment)
//            }
        } else if (resId == R.id.iv_make) {
            //首页
            if (homeFragment == null) {
                homeFragment = HomeFragment()
                transaction.add(R.id.main_container, homeFragment)
            } else {
                transaction.show(homeFragment)
            }
        } else if (resId == R.id.tv_message) {
            //报表
            if (messageFragment == null) {
                messageFragment = MessageFragment()
                transaction.add(R.id.main_container, messageFragment)
            } else {
                transaction.show(messageFragment)
            }
        } else if (resId == R.id.tv_person) {
            //我
            if (personFragment == null) {
                personFragment = PersonFragment()
                transaction.add(R.id.main_container, personFragment)
            } else {
                transaction.show(personFragment)
            }
        }
        //提交事务
        transaction.commit()
    }

    /**
     * 显示之前隐藏所有fragment
     *
     * @param transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        //不为空才隐藏,如果不判断第一次会有空指针异常
        if (idCardFragment != null)
            transaction.hide(idCardFragment)
        if (dynamicFragment != null)
            transaction.hide(dynamicFragment)
        if (homeFragment != null)
            transaction.hide(homeFragment)
        if (messageFragment != null)
            transaction.hide(messageFragment)
        if (personFragment != null)
            transaction.hide(personFragment)
    }

    /**
     * 改变TextView选中颜色
     *
     * @param resId
     */
    private fun changeSelect(resId: Int) {
        tvMain!!.isSelected = false
        tvDynamic!!.isSelected = false
        ivHome!!.isSelected = false
        tvMessage!!.isSelected = false
        tvPerson!!.isSelected = false
        when (resId) {
            R.id.tv_main -> tvMain!!.isSelected = true
            R.id.tv_dynamic -> tvDynamic!!.isSelected = true
            R.id.iv_make -> ivHome!!.isSelected = true
            R.id.tv_message -> tvMessage!!.isSelected = true
            R.id.tv_person -> tvPerson!!.isSelected = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                val mHelperUtils: Any
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()

            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
