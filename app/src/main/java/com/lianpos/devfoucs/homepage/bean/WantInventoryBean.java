package com.lianpos.devfoucs.homepage.bean;

/**
 * 盘点确认Bean
 * Created by wangshuai on 2017/11/16 0016.
 */

public class WantInventoryBean {
    //商品ID
    private String sp_id;
    //商品名称
    private String sp_name;
    //商品条码
    private String barcode;
    //商品数量
    private String pd_inventory_count;
    //商品单位
    private String sp_unit;
    //商品批发价
    private String pd_selling_price;

    public WantInventoryBean() {
    }

    public WantInventoryBean(String itemID,String itemShopName, String shopTiaoma, String shopNumber, String shopUnit, String shopPifajia) {
        this.sp_id = itemID;
        this.sp_name = itemShopName;
        this.barcode = shopTiaoma;
        this.pd_inventory_count = shopNumber;
        this.sp_unit = shopUnit;
        this.pd_selling_price = shopPifajia;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPd_inventory_count() {
        return pd_inventory_count;
    }

    public void setPd_inventory_count(String pd_inventory_count) {
        this.pd_inventory_count = pd_inventory_count;
    }

    public String getSp_unit() {
        return sp_unit;
    }

    public void setSp_unit(String sp_unit) {
        this.sp_unit = sp_unit;
    }

    public String getPd_selling_price() {
        return pd_selling_price;
    }

    public void setPd_selling_price(String pd_selling_price) {
        this.pd_selling_price = pd_selling_price;
    }
}
