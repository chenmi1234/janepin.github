package com.lianpos.devfoucs.contacts.model


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean

/**
 * Created by wangshuai .
 * Date: 17/10/31
 */

class PhoneBean {

    private var phone: String? = null//城市名字
    private var isTop: Boolean = false//是否是最上面的 不需要被转化成拼音的

    constructor() {}

    constructor(phone: String) {
        this.phone = phone
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String): PhoneBean {
        this.phone = phone
        return this
    }

    fun isTop(): Boolean {
        return isTop
    }

    fun setTop(top: Boolean): PhoneBean {
        isTop = top
        return this
    }
}
