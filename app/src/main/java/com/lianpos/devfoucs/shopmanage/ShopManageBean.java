package com.lianpos.devfoucs.shopmanage;

import java.util.List;

/**
 * Created by wangshuai on 2017/12/5.
 */
public class ShopManageBean {

    private String result;

    private List<AddressEntity> address;

    public void setResult(String result) {
        this.result = result;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }

    public String getResult() {
        return result;
    }

    public List<AddressEntity> getAddress() {
        return address;
    }

    public static class AddressEntity {
        private String name;
        private String custId;
        private List<String> area;

        public void setName(String name) {
            this.name = name;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }

        public String getName() {
            return name;
        }

        public String getCustId() {
            return custId;
        }

        public List<String> getArea() {
            return area;
        }
    }
}
