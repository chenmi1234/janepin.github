package com.lianpos.devfoucs.reportform.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 个人资料
 * Created by wangshuai on 2017/11/06 .
 */

public class ReportForm extends BaseActivity implements View.OnClickListener {

    private ImageView zl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_zl);
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
        zl_back = (ImageView) findViewById(R.id.zl_back);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        zl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zl_back:
                finish();
                break;
        }
    }

}