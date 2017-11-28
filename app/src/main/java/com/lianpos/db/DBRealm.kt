package com.lianpos.db

import com.lianpos.entity.JanePinBean
import io.realm.Realm

/**
 * 共通类 增删改查
 * Created by wangshuai on 2017/11/14 0014.
 */

class DBRealm {
    /**
     * 新增
     */
    fun createTyokinInfoBean(realm: Realm) {
        realm.beginTransaction()
        val appUserInfoBean = realm.createObject(JanePinBean::class.java)
//        appUserInfoBean.docType = "TYOKININFO"
        realm.commitTransaction()
    }


    /**
     * 查询
     *
     * @param realm Realm
     */
    fun seachTyokinInfo(realm: Realm): JanePinBean? {
        val queryApp = realm.where(JanePinBean::class.java)
        queryApp.equalTo("id", 0)
        val accountAppli = queryApp.findFirst()
        return accountAppli
    }

    /**
     * 删除
     */
    fun deleteTyokinInfo(realm: Realm) {
        realm.beginTransaction()
        realm.delete(JanePinBean::class.java)
        realm.commitTransaction()
    }

    /**
     * 修改
     */
    fun insertTyokinInfo(realm: Realm, acList: MutableList<JanePinBean>) {
        realm.beginTransaction()
        val saveBean = seachTyokinInfo(realm)
        for ((i, item) in acList.withIndex()) {
            val tmp = JanePinBean()
            tmp.id = item.id
//            tmp.account_name = item.account_name
//            tmp.account_number = item.account_number
//            tmp.show_name = item.show_name
//            tmp.current_balance = item.current_balance
//            saveBean!!.resultList!!.add(tmp)

        }
        realm.commitTransaction()
    }
}
