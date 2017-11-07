package com.lianpos.devfoucs.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.reportform.activity.EditerAreaActivity;
import com.lianpos.firebase.BaseActivity;

/**
 * 添加好友
 * Created by wangshuai on 2017/11/07 .
 */

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    private ImageView add_friend_back;
    String num1 = "";
    String request = "";
    private TextView company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        init();
        Intent intert = getIntent();
        num1 = intert.getStringExtra("page");
        request = intert.getStringExtra("codedContent");
        if (num1 != null){
            if (num1.equals("2")) {
                company_name.setText(request);
            }
        }
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
        add_friend_back = (ImageView) findViewById(R.id.add_friend_back);
        company_name = (TextView) findViewById(R.id.company_name);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        add_friend_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_friend_back:
                finish();
                break;

        }
    }

}