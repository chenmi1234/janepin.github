package com.lianpos.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by wangshuai on 2017/10/27 0027.
 */

class MessageGroupFragmentAdapter : FragmentStatePagerAdapter {
    private var list: List<Fragment>? = null

    constructor(fm: FragmentManager, list: List<Fragment>) : super(fm) {
        this.list = list
    }

    constructor(fm: FragmentManager) : super(fm) {}

    override fun getItem(arg0: Int): Fragment {
        return list!![arg0]
    }

    override fun getCount(): Int {
        return list!!.size
    }
}
