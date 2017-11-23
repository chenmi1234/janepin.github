package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.listviewlinkage.Activity.LinkageActivity;
import com.lianpos.firebase.BaseActivity;

/**
 * 我要赚钱跳转身份卡页面
 *
 * Created by wangshuai on 2017/11/06
 */
public class BillingFristPageActivity extends BaseActivity {

    private RelativeLayout addShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_first);
        addShopButton = (RelativeLayout) findViewById(R.id.addShopButton);
        addShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BillingFristPageActivity.this, LinkageActivity.class);
                startActivity(intent);
            }
        });
    }

}