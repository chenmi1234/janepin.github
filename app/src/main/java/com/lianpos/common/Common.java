package com.lianpos.common;

/**
 * 静态变量
 * Created by wangshuai on 2017/11/27
 */

public class Common {
    // LOCALURL
    private static final String LOCALURL = "http://192.168.5.100";
    // 登录API url
    public static String loginUrl  = LOCALURL + "/app/appUser/login.do";
    // 注册API url
    public static String registerUrl  =  LOCALURL + "/app/appUser/register.do";
    // 身份卡API url
    public static String idCardUrl = LOCALURL + "/app/appUser/findywView.do";
    // 库存API url
    public static String invertoryUrl = LOCALURL + "/app/appGoods/queryGoods.do";
    // 个人资料API url
    public static String userZlUrl = LOCALURL + "/app/appUser/findywView.do";
}
