package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.reportform.activity.EditerAreaActivity;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.MoneyEditText;

/**
 * 开单盘点详情页
 * Created by wangshuai on 2017/11/06 .
 */

public class ShopInformation extends BaseActivity implements View.OnClickListener {

    private ImageView enterprise_back;
    private TextView enterprise_editer;
    private EditText number_unit_edit, pifa_price_edit, jianyi_price_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
        // 实现
        editFun();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        enterprise_back = (ImageView) findViewById(R.id.enterprise_back);
        enterprise_editer = (TextView) findViewById(R.id.enterprise_editer);
        number_unit_edit = (EditText) findViewById(R.id.number_unit_edit);
        pifa_price_edit = (EditText) findViewById(R.id.pifa_price_edit);
        jianyi_price_edit = (EditText) findViewById(R.id.jianyi_price_edit);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        enterprise_back.setOnClickListener(this);
        enterprise_editer.setOnClickListener(this);
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

        }
    }

}