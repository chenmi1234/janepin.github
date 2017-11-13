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
import com.lianpos.firebase.BaseActivity;

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
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        editter_back = (ImageView) findViewById(R.id.editter_back);
        basic_unit_layout = (RelativeLayout) findViewById(R.id.basic_unit_layout);
        editter_save = (TextView) findViewById(R.id.editter_save);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        editter_back.setOnClickListener(this);
        basic_unit_layout.setOnClickListener(this);
        editter_save.setOnClickListener(this);
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