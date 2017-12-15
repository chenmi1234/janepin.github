package com.lianpos.devfoucs.homepage.bean;

/**
 * 开单确认Bean
 * Created by wangshuai on 2017/11/16 0016.
 */

public class WantBillingBean {
    //商品id
    private String sp_id;
    //商品名称
    private String sp_name;
    //商品条码
    private String barcode;
    //商品数量
    private String jh_count;
    //商品单位
    private String sp_unit;
    //商品批发价
    private String jh_purchasing_price;
    //商品建议售价
    private String pd_selling_price;
    //商品金额
    private String jh_money;

    public WantBillingBean() {
    }

    public WantBillingBean(String itemShopId, String itemShopName, String shopTiaoma, String shopNumber, String shopUnit, String shopPifajia, String shopPrice, String shopTotal) {
        this.sp_id = itemShopId;
        this.sp_name = itemShopName;
        this.barcode = shopTiaoma;
        this.jh_count = shopNumber;
        this.sp_unit = shopUnit;
        this.jh_purchasing_price = shopPifajia;
        this.pd_selling_price = shopPrice;
        this.jh_money = shopTotal;
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

    public String getJh_count() {
        return jh_count;
    }

    public void setJh_count(String jh_count) {
        this.jh_count = jh_count;
    }

    public String getSp_unit() {
        return sp_unit;
    }

    public void setSp_unit(String sp_unit) {
        this.sp_unit = sp_unit;
    }

    public String getJh_purchasing_price() {
        return jh_purchasing_price;
    }

    public void setJh_purchasing_price(String jh_purchasing_price) {
        this.jh_purchasing_price = jh_purchasing_price;
    }

    public String getPd_selling_price() {
        return pd_selling_price;
    }

    public void setPd_selling_price(String pd_selling_price) {
        this.pd_selling_price = pd_selling_price;
    }

    public String getJh_money() {
        return jh_money;
    }

    public void setJh_money(String jh_money) {
        this.jh_money = jh_money;
    }
}
