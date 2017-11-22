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
    //询价单 商品条码
    public String InquiryShopNumber;
    //询价单 商品名称
    public String InquiryShopName;
    //询价单 基本单位
    public String InquiryShopUnit;
    //询价单 批发价
    public String InquiryShopTrade;
    //添加商品 Dialog数量
    public String AddShopDialogNumber;
    //添加商品 Dialog单位
    public String AddShopDialogUnit;
    //添加商品 Dialog状态
    public String AddShopDialogStateNumber;
    //添加商品 Dialog单价
    public String AddShopDialogPrice;
    //添加商品 DialogTitle
    public String AddShopDialogTitle;
}