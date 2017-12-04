package com.lianpos.devfoucs.homepage.bean;

/**
 * 开单确认Bean
 * Created by wangshuai on 2017/11/16 0016.
 */

public class WantBillingBean {
    //商品名称
    private String itemShopName;
    //商品条码
    private String shopTiaoma;
    //商品数量
    private String shopNumber;
    //商品单位
    private String shopUnit;
    //商品批发价
    private String shopPifajia;
    //商品建议售价
    private String shopPrice;
    //商品金额
    private String shopTotal;

    public WantBillingBean() {
    }

    public WantBillingBean(String itemShopName, String shopTiaoma, String shopNumber, String shopUnit, String shopPifajia, String shopPrice, String shopTotal) {
        this.itemShopName = itemShopName;
        this.shopTiaoma = shopTiaoma;
        this.shopNumber = shopNumber;
        this.shopUnit = shopUnit;
        this.shopPifajia = shopPifajia;
        this.shopPrice = shopPrice;
        this.shopTotal = shopTotal;
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

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getShopUnit() {
        return shopUnit;
    }

    public void setShopUnit(String shopUnit) {
        this.shopUnit = shopUnit;
    }

    public String getShopPifajia() {
        return shopPifajia;
    }

    public void setShopPifajia(String shopPifajia) {
        this.shopPifajia = shopPifajia;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(String shopTotal) {
        this.shopTotal = shopTotal;
    }
}
