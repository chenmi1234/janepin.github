package com.lianpos.entity;

import io.realm.RealmObject;

/**
 * 创建实体类
 * Created by wangshuai on 2017/11/14 0014.
 */

public class JanePinBean extends RealmObject {
    public int id;
    //账号
    public String PhoneNumber;
    //密码
    public String Psw;
    //确认密码
    public String ConPsw;
}