package com.lianpos.devfoucs.contacts.model


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean

/**
 * Created by wangshuai .
 * Date: 17/10/28
 */

class CityBean() : BaseIndexPinyinBean() {

    var city: String? = null//城市名字
    var phone: String? = null//电话号
    var shopName: String? = null//超市名称

    override fun getTarget(): String? {
        return city
    }
}
