package com.lianpos.devfoucs.homepage.bean;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class InquirySheetBean {
    //商品名称
    private String itemShopName;
    //商品条码
    private String shopTiaoma;
    //商品单位
    private String shopDanwei;

    public InquirySheetBean() {
    }

    public InquirySheetBean(String itemShopName, String shopTiaoma, String shopDanwei) {
        this.itemShopName = itemShopName;
        this.shopTiaoma = shopTiaoma;
        this.shopDanwei = shopDanwei;
    }

    public String getItemShopName() {
        return itemShopName;
    }

    public void setItemShopName(String itemShopName) {
        this.itemShopName = itemShopName;
    }

    public String getShopTiaoma() {
        return shopTiaoma;
    }

    public void setShopTiaoma(String shopTiaoma) {
        this.shopTiaoma = shopTiaoma;
    }

    public String getShopDanwei() {
        return shopDanwei;
    }

    public void setShopDanwei(String shopDanwei) {
        this.shopDanwei = shopDanwei;
    }
}
