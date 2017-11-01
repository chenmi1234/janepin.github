package com.lianpos.devfoucs.contacts.model;


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by wangshuai .
 * Date: 17/10/31
 */

public class PhoneBean {

    private String phone;//城市名字
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public PhoneBean() {
    }

    public PhoneBean(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public PhoneBean setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isTop() {
        return isTop;
    }

    public PhoneBean setTop(boolean top) {
        isTop = top;
        return this;
    }
}
