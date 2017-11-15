package com.lianpos.db;

import com.lianpos.entity.JanePinBean;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 共通类 增删改查
 * Created by wangshuai on 2017/11/14 0014.
 */

public class DBRealm {
    private static int i = 1;

    /**
     * 这是一个添加一条数据的方法
     */
    public static JanePinBean add(Realm realm) {
        i = i + 1;
        realm.beginTransaction();
        JanePinBean JanePinBean = realm.createObject(JanePinBean.class); // Create a new object
        realm.commitTransaction();
        return JanePinBean;
    }

    /**
     * 这是一个删除一条数据的方法
     */
    public static RealmResults<JanePinBean> delete(Realm realm) {
        realm.beginTransaction();
        RealmResults<JanePinBean> deleteGuests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        return deleteGuests;
    }

    /**
     * 这是一条更新的方法
     */
    public static RealmResults<JanePinBean> updata(Realm realm) {
        realm.beginTransaction();
        RealmResults<JanePinBean> updataGuests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        return updataGuests;
    }

    /**
     * 这是一个一个查询的方法
     */
    public static RealmResults<JanePinBean> query(Realm realm) {
        realm.beginTransaction();
        RealmResults<JanePinBean> queryGuests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        return queryGuests;
    }
}
