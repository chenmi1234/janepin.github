package com.lianpos.devfoucs.contacts.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 检索好友
 * Created by wangshuai on 2017/11/07 .
 */

public class AddPhoneNmbActivity extends BaseActivity implements View.OnClickListener {

    private ImageView add_friend_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
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
        add_friend_back = (ImageView) findViewById(R.id.add_friend_back);
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