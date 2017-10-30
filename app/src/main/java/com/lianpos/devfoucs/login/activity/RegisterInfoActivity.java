package com.lianpos.devfoucs.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/26.
 */

public class RegisterInfoActivity extends BaseActivity implements View.OnClickListener {
    // 姓名输入
    private EditText register_name_editText;
    // 下一步
    private Button next_button;
    // 返回
    private ImageView register_back;
    // 姓名删除
    private ImageView deleteNameImg;
    // 注册姓名message
    private TextView registerPhoneMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
        // 方法实现
        funRealization();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        // 姓名输入
        register_name_editText = (EditText) findViewById(R.id.register_name_editText);
        // 下一步
        next_button = (Button) findViewById(R.id.next_button);
        // 返回
        register_back = (ImageView) findViewById(R.id.register_back);
        // 删除姓名
        deleteNameImg = (ImageView) findViewById(R.id.deleteNameImg);
        // 注册账号message
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 姓名删除
        deleteNameImg.setOnClickListener(this);
        // 下一步
        next_button.setOnClickListener(this);
        // 返回
        register_back.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        // 账号输入结束监听
        register_name_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!register_name_editText.getText().toString().isEmpty()) {
                } else {
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteNameImg:
                break;
            case R.id.next_button:
                Intent intent1 = new Intent();
                intent1.setClass(RegisterInfoActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }
}
