package com.lianpos.devfoucs.reportform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.login.activity.RegisterActivity;
import com.lianpos.devfoucs.login.activity.RegisterAreaActivity;
import com.lianpos.firebase.BaseActivity;

/**
 * 企业信息
 * Created by wangshuai on 2017/11/06 .
 */

public class EnterpriseInformation extends BaseActivity implements View.OnClickListener {

    private ImageView enterprise_back;
    private TextView enterprise_editer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_information);
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
        enterprise_back = (ImageView) findViewById(R.id.enterprise_back);
        enterprise_editer = (TextView) findViewById(R.id.enterprise_editer);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        enterprise_back.setOnClickListener(this);
        enterprise_editer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterprise_back:
                finish();
                break;
            case R.id.enterprise_editer:
                Intent intent = new Intent();
                intent.setClass(EnterpriseInformation.this, EditerAreaActivity.class);
                startActivity(intent);
                break;

        }
    }

}