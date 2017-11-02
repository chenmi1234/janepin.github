package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 询价单
 *
 * @author 询价
 *         Created by wangshuai on 2017/11/2
 */
public class InquirySheetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView inquiry_sheet_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_sheet);
        init();
    }

    private void init() {
        // 初始化id
        initID();
        // 初始化点击事件
        initOnClick();
    }

    // 初始化id
    private void initID() {
        inquiry_sheet_back = (ImageView) findViewById(R.id.inquiry_sheet_back);
    }

    // 初始化点击事件
    private void initOnClick() {
        inquiry_sheet_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inquiry_sheet_back:
                finish();
                break;
        }
    }
}
