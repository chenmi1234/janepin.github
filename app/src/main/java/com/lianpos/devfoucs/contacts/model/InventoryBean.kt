package com.lianpos.devfoucs.contacts.model


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean

/**
 * Created by wangshuai .
 * Date: 17/10/28
 */

class InventoryBean() : BaseIndexPinyinBean() {

    var inName: String? = null//名称
    var tiaoma: String? = null//条码
    var inNumber: String? = null//销量
    var unit: String? = null//单位
    var inventory: String? = null//库存
    var inventoryunit: String? = null//单位

    override fun getTarget(): String? {
        return inName
    }
}
