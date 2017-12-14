package com.lianpos.devfoucs.shopmanage;

import java.util.List;

/**
 * Created by wangshuai on 2017/12/5.
 */
public class ShopManageBean {

    private String result_flag;

    private List<AddressEntity> info_list;

    public String getResult_flag() {
        return result_flag;
    }

    public void setResult_flag(String result_flag) {
        this.result_flag = result_flag;
    }

    public List<AddressEntity> getInfo_list() {
        return info_list;
    }

    public void setInfo_list(List<AddressEntity> info_list) {
        this.info_list = info_list;
    }

    public static class AddressEntity {
        private String brand_name;
        private List<AddressAreaEntity> sp_list;

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public List<AddressAreaEntity> getSp_list() {
            return sp_list;
        }

        public void setSp_list(List<AddressAreaEntity> sp_list) {
            this.sp_list = sp_list;
        }
    }


    public static class AddressAreaEntity {
        private String sp_id;
        private String sp_name;
        private String barcode;
        private String sp_selling_price;

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

        public String getSp_selling_price() {
            return sp_selling_price;
        }

        public void setSp_selling_price(String sp_selling_price) {
            this.sp_selling_price = sp_selling_price;
        }
    }
}
