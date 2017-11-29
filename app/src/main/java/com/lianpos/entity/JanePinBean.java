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
    //添加商品 Dialog条码
    public String AddShopDialogTiaoma;
    //添加商品 Dialog数量
    public String AddShopDialogNumber;
    //添加商品 Dialog单位
    public String AddShopDialogUnit;
    //添加商品 Dialog状态
    public String AddShopDialogStateNumber;
    //添加商品 Dialog单价
    public String AddShopDialogPrice;
    //添加商品 Dialog建议
    public String AddShopDialogJyPrice;
    //添加商品 DialogTitle
    public String AddShopDialogTitle;
    // 开单盘点分辨字段 0 开单 1 盘点 2 查看库存
    public String BillingInventoryCode = "0";
    // 开单盘点分辨字段 0 不弹出 1 弹出
    public String DialogEjectCode = "0";
    //新增商品 商品条码判断  新增商品 1  组装拆分 2
    public String NewlyAddedDistinguish;
    //新增商品 商品条码
    public String NewlyAddedBarCode;
    //新增商品 商品名称
    public String NewlyAddedName;
    //新增商品 基本单位
    public String NewlyAddedUnit;
    //新增商品 规格
    public String NewlyAddedSpecifications;
    //新增商品 品牌
    public String NewlyAddedBrand;
    //新增商品 批发价
    public String NewlyAddedTradePrice;
    //新增商品 建议售价
    public String NewlyAddedSuggestedPrice;
    //新增商品 套餐
    public String NewlyAddedPackage;
    //新增商品 套餐 数量
    public String NewlyAddedPackageNumber;
    //新增商品 套餐 单位
    public String NewlyAddedPackageUnit;
    //新增商品 套餐 最小单位
    public String NewlyAddedPackageMinUnit;
    //新增商品 套餐 建议售价
    public String NewlyAddedPackagePrice;
    //新增商品 组装拆分
    public String NewlyAddedAssemble;
    //新增商品 套餐 数量
    public String NewlyAddedAssembleNumber;
    //新增商品 套餐 单位
    public String NewlyAddedAssembleUnit;
    //新增商品 组装拆分 商品条码
    public String NewlyAddedAssembleBarCode;
    //新增商品 组装拆分 商品名称
    public String NewlyAddedAssembleName;
    //新增商品 组装拆分 建议售价
    public String NewlyAddedAssemblePrice;
}