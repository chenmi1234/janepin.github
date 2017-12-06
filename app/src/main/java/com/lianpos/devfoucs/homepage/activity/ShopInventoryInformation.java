package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.shoppingcart.activity.ChooseListView;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.MoneyEditText;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 盘点详情页
 * Created by wangshuai on 2017/11/06 .
 */

public class ShopInventoryInformation extends BaseActivity implements View.OnClickListener {

    private ImageView enterprise_back;
    private TextView enterprise_editer, inventory_page_tiaoma, inventory_page_name, inventory_page_unit;
    private EditText number_unit_edit, jianyi_price_edit;
    private RelativeLayout danweiChoose;
    private Button inventory_page_btn;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_inventory_information);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 获取数据
        queryData();
        // 初始化点击事件
        initEvent();
        // 实现
        editFun();
    }

    private void queryData() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String addTiaoma = "";
        String addNumber = "";
        String addPrice = "";
        String addUnit = "";
        for (JanePinBean guest : guests) {
            addTiaoma = guest.AddShopInventoryTiaoma;
            addNumber = guest.AddShopInventoryStock;
            addUnit = guest.AddShopInventoryUnit;
            addPrice = guest.AddShopDInventoryPrice;
        }

        inventory_page_tiaoma.setText(addTiaoma);
        inventory_page_name.setText("商品名称");
        number_unit_edit.setText(addNumber);
        inventory_page_unit.setText(addUnit);
        jianyi_price_edit.setText(addPrice);
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        enterprise_back = (ImageView) findViewById(R.id.enterprise_back);
        enterprise_editer = (TextView) findViewById(R.id.enterprise_editer);
        inventory_page_tiaoma = (TextView) findViewById(R.id.inventory_page_tiaoma);
        inventory_page_name = (TextView) findViewById(R.id.inventory_page_name);
        inventory_page_unit = (TextView) findViewById(R.id.inventory_page_unit);
        number_unit_edit = (EditText) findViewById(R.id.number_unit_edit);
        jianyi_price_edit = (EditText) findViewById(R.id.jianyi_price_edit);
        danweiChoose = (RelativeLayout) findViewById(R.id.danweiChoose);
        inventory_page_btn = (Button) findViewById(R.id.inventory_page_btn);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        enterprise_back.setOnClickListener(this);
        enterprise_editer.setOnClickListener(this);
        danweiChoose.setOnClickListener(this);
        inventory_page_btn.setOnClickListener(this);
    }

    private void editFun() {
        MoneyEditText.setPricePoint(jianyi_price_edit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterprise_back:
                finish();
                break;
            case R.id.enterprise_editer:
                Intent intent = new Intent();
                intent.setClass(ShopInventoryInformation.this, IncreaseCommodityActivity.class);
                startActivity(intent);
                break;
            case R.id.danweiChoose:
                Intent intent1 = new Intent();
                intent1.setClass(ShopInventoryInformation.this, ChooseListView.class);
                intent1.putExtra("danwei", "1");
                startActivity(intent1);
                break;
            case R.id.inventory_page_btn:
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.AddShopInventoryTiaoma = inventory_page_tiaoma.getText().toString();
                janePinBean.AddShopInventoryStock = number_unit_edit.getText().toString();
                janePinBean.AddShopInventoryUnit = inventory_page_unit.getText().toString();
                janePinBean.AddShopDInventoryPrice = jianyi_price_edit.getText().toString();
                janePinBean.AddShopInventoryCode = "1";
                realm.commitTransaction();

                finish();
                break;

        }
    }

}