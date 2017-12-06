package com.lianpos.devfoucs.homepage.bean;

/**
 * 盘点确认Bean
 * Created by wangshuai on 2017/11/16 0016.
 */

public class WantInventoryBean {
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

    public WantInventoryBean() {
    }

    public WantInventoryBean(String itemShopName, String shopTiaoma, String shopNumber, String shopUnit, String shopPifajia) {
        this.itemShopName = itemShopName;
        this.shopTiaoma = shopTiaoma;
        this.shopNumber = shopNumber;
        this.shopUnit = shopUnit;
        this.shopPifajia = shopPifajia;
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

}
