package com.lianpos.devfoucs.contacts.model


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean

/**
 * Created by wangshuai .
 * Date: 17/10/28
 */

class CityBean : BaseIndexPinyinBean {

    var city: String? = null//城市名字
    private var isTop: Boolean = false//是否是最上面的 不需要被转化成拼音的

    constructor() {}

    override fun getTarget(): String? {
        return city
    }

    override fun isNeedToPinyin(): Boolean {
        return !isTop
    }


    override fun isShowSuspension(): Boolean {
        return !isTop
    }
}
