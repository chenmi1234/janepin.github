package com.lianpos.devfoucs.shoppingcart.assistant;


import com.lianpos.devfoucs.shoppingcart.mode.ShopProduct;

/**
 * Created by wangshuai on 2017/11/7.
 * 购物车添加接口回调
 */
public interface ShopToDetailListener {
    /**
     * Type表示添加或减少
     * @param product
     * @param type
     */
    void onUpdateDetailList(ShopProduct product, String type);

    void onRemovePriduct(ShopProduct product);
}
