package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.shoppingcart.activity.ChooseListView;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 编辑
 * Created by wangshuai on 2017/11/13 .
 */

public class InquiryEditerActyvity extends BaseActivity implements View.OnClickListener {

    private ImageView editter_back;
    //基本单位选择
    private RelativeLayout basic_unit_layout;
    //保存按钮
    private TextView editter_save;
    Realm realm;
    //条形码  商品名称
    private TextView shopNumber_text,shopName_text,shopDanwei_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_editter);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
        //数据获取方法
        dataFun();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        editter_back = (ImageView) findViewById(R.id.editter_back);
        basic_unit_layout = (RelativeLayout) findViewById(R.id.basic_unit_layout);
        editter_save = (TextView) findViewById(R.id.editter_save);
        shopNumber_text = (TextView) findViewById(R.id.shopNumber_text);
        shopName_text = (TextView) findViewById(R.id.shopName_text);
        shopDanwei_text = (TextView) findViewById(R.id.shopDanwei_text);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        editter_back.setOnClickListener(this);
        basic_unit_layout.setOnClickListener(this);
        editter_save.setOnClickListener(this);
    }

    private void dataFun(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        shopNumber_text.setText("");
        String showNumber = "";
        String showName = "";
        String showDanwei = "";
        for (JanePinBean guest : guests) {
            showNumber = guest.InquiryShopNumber;
            showName = guest.InquiryShopName;
            showDanwei = guest.InquiryShopUnit;
        }
        shopNumber_text.setText(showNumber);
        shopName_text.setText(showName);
        shopDanwei_text.setText(showDanwei);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editter_back:
                finish();
                break;
            case R.id.basic_unit_layout:
                Intent intent = new Intent();
                intent.setClass(InquiryEditerActyvity.this, ChooseListView.class);
                intent.putExtra("danwei", "1");
                startActivity(intent);
                break;
            case R.id.editter_save:
                finish();
                break;
        }
    }

}