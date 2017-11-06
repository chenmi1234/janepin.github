package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 我要赚钱跳转身份卡页面
 *
 * Created by wangshuai on 2017/11/06
 */
public class IWantBillingActivity extends BaseActivity {

    private ImageView make_money_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_billing);
        init();
    }

    private void init(){
        initID();
        initClick();
    }

    private void initID(){
        make_money_back = (ImageView) findViewById(R.id.make_money_back);
    }

    private void initClick(){
        make_money_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
