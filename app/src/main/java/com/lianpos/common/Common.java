package com.lianpos.common;

/**
 * 静态变量
 * Created by wangshuai on 2017/11/27
 */

public class Common {
    // 本地Url
    private static final String LOCALURL = "http://47.100.123.184";
    // 登录API url
    public static String loginUrl  = LOCALURL + "/app/appUser/login.do";
    // 注册API url
    public static String registerUrl  =  LOCALURL + "/app/appUser/register.do";
    // 身份卡API url
    public static String idCardUrl = LOCALURL + "/app/appUser/findywView.do";
    // 联系人列表
    public static String userListByYwUrl = LOCALURL + "/app/appUser/getShUserListByYw.do";
    // 联系人删除
    public static String userDelYwUrl = LOCALURL + "/app/appUser/delRelation.do";
    // 搜索好友和扫一扫添加好友
    public static String querySysUserUrl = LOCALURL + "/app/appUser/querySysUser.do";
    // 添加好友
    public static String addFriendUserUrl = LOCALURL + "/app/appUser/addRelation.do";
    // 库存API url
    public static String invertoryUrl = LOCALURL + "/app/appGoods/queryGoods.do";
    // 个人资料API url
    public static String userZlUrl = LOCALURL + "/app/appUser/findywView.do";
    // 修改密码
    public static String userPassUrl = LOCALURL + "/app/userPassword/userPass.do";
    // 修改企业信息
    public static String editInfoUrl = LOCALURL + "/app/userPassword/editInfo.do";
    // 新增销售单（进货单）
    public static String importBillUrl = LOCALURL + "/app/appGoods/getGoods.do";
    // 进货单，确定并发送
    public static String saveReceiptUrl = LOCALURL + "/app/appGoods/saveReceipt.do";
    // 商品管理  获取商品列表
    public static String obtainShopUrl = LOCALURL + "/app/appGoods/getYwGoodsList.do";
    // 商品管理  新增商品
    public static String addShopUrl = LOCALURL + "/app/appGoods/saveYwGoodsInfo.do";
    // 商品管理  查询单位、规格、口味、品牌
    public static String queryListUrl = LOCALURL + "/app/appGoods/getInfoListByUser.do";
    // 云码库查询
    public static String goodsBySpxUrl = LOCALURL + "/app/appGoods/getGoodsBySpxx.do";
    // 盘点单新增
    public static String saveInventoryUrl = LOCALURL + "/app/appGoods/saveInventory.do";
}
