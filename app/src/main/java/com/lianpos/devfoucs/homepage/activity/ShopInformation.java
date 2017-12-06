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
import com.lianpos.devfoucs.reportform.activity.EditerAreaActivity;
import com.lianpos.devfoucs.shoppingcart.activity.ChooseListView;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.MoneyEditText;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 开单详情页
 * Created by wangshuai on 2017/11/06 .
 */

public class ShopInformation extends BaseActivity implements View.OnClickListener {

    private ImageView enterprise_back;
    private TextView enterprise_editer;
    private EditText number_unit_edit, pifa_price_edit, jianyi_price_edit;
    private TextView billing_name,billing_tiaoma;
    private Button billing_page_btn;
    private Realm realm = null;
    private RelativeLayout danweiChoose;
    private TextView inventory_page_unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
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


    private void queryData(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String addName = "";
        String addTiaoma = "";
        String addNumber = "";
        String addPrice = "";
        String addUnit = "";
        String addJyPrice = "";
        for (JanePinBean guest : guests) {
            addName = guest.AddShopBillingName;
            addTiaoma = guest.AddShopBillingTiaoma;
            addNumber = guest.AddShopBillingStock;
            addUnit = guest.AddShopBillingUnit;
            addPrice = guest.AddShopDBillingPrice;
            addJyPrice = guest.AddShopDBillingJYPrice;
        }

        billing_tiaoma.setText(addTiaoma);
        billing_name.setText(addName);
        number_unit_edit.setText(addNumber);
        inventory_page_unit.setText(addUnit);
        pifa_price_edit.setText(addPrice);
        jianyi_price_edit.setText(addJyPrice);
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        billing_name = (TextView) findViewById(R.id.billing_name);
        billing_tiaoma = (TextView) findViewById(R.id.billing_tiaoma);
        enterprise_back = (ImageView) findViewById(R.id.enterprise_back);
        enterprise_editer = (TextView) findViewById(R.id.enterprise_editer);
        number_unit_edit = (EditText) findViewById(R.id.number_unit_edit);
        pifa_price_edit = (EditText) findViewById(R.id.pifa_price_edit);
        jianyi_price_edit = (EditText) findViewById(R.id.jianyi_price_edit);
        inventory_page_unit = (TextView) findViewById(R.id.inventory_page_unit);
        billing_page_btn  = (Button) findViewById(R.id.billing_page_btn);
        danweiChoose = (RelativeLayout) findViewById(R.id.danweiChoose);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        enterprise_back.setOnClickListener(this);
        enterprise_editer.setOnClickListener(this);
        billing_page_btn.setOnClickListener(this);
    }

    private void editFun(){
        MoneyEditText.setPricePoint(number_unit_edit);
        MoneyEditText.setPricePoint(pifa_price_edit);
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
                intent.setClass(ShopInformation.this, IncreaseCommodityActivity.class);
                startActivity(intent);
                break;
            case R.id.billing_page_btn:
                finish();
                break;
            case R.id.danweiChoose:
                Intent intent1 = new Intent();
                intent1.setClass(ShopInformation.this, ChooseListView.class);
                intent1.putExtra("danwei", "1");
                startActivity(intent1);
                break;
        }
    }

}