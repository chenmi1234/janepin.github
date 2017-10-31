package com.lianpos.devfoucs.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.view.OneButtonSuccessDialog;
import com.lianpos.devfoucs.view.OneButtonWarningDialog;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/31.
 */

public class RegisterAreaActivity extends BaseActivity implements View.OnClickListener {
    // 企业名称
    private EditText enterprise_name;
    // 供货商名称
    private EditText supplier_name;
    // 老板电话
    private EditText boss_phone;
    // 区域选择
    private RelativeLayout area_choose;
    // 区域
    private TextView area_text;
    // 详细地址
    private EditText detailed_address;
    // 立即注册
    private Button immediate_registration;
    // 返回
    private ImageView register_back;
    // 手机号check
    private TextView registerPhoneMessage;
    // 一个按钮的dialog
    private OneButtonSuccessDialog oneButtonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_area);
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
        // 企业名称
        enterprise_name = (EditText) findViewById(R.id.enterprise_name);
        // 供货商名称
        supplier_name = (EditText) findViewById(R.id.supplier_name);
        // 老板电话
        boss_phone = (EditText) findViewById(R.id.boss_phone);
        // 区域选择
        area_choose = (RelativeLayout) findViewById(R.id.area_choose);
        // 区域
        area_text = (TextView) findViewById(R.id.area_text);
        // 详细地址
        detailed_address = (EditText) findViewById(R.id.detailed_address);
        // 立即注册
        immediate_registration = (Button) findViewById(R.id.immediate_registration);
        // 返回
        register_back = (ImageView) findViewById(R.id.register_back);
        // 手机号验证
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 区域选择
        area_choose.setOnClickListener(this);
        // 立即注册
        immediate_registration.setOnClickListener(this);
        // 返回
        register_back.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        // PhoneNumber输入结束监听
        boss_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!boss_phone.getText().toString().isEmpty()) {
                    if (CheckInforUtils.isMobile(boss_phone.getText().toString())) {
                        registerPhoneMessage.setVisibility(View.GONE);
                    } else {
                        registerPhoneMessage.setVisibility(View.VISIBLE);
                        registerPhoneMessage.setText("请输入正确的手机号");
                    }
                } else {
                    registerPhoneMessage.setVisibility(View.VISIBLE);
                    registerPhoneMessage.setText("请输入手机号");
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area_choose:
                break;
            case R.id.immediate_registration:

                oneButtonDialog = new OneButtonSuccessDialog(RegisterAreaActivity.this);
                oneButtonDialog.setYesOnclickListener(new OneButtonSuccessDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent1 = new Intent();
                        intent1.setClass(RegisterAreaActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                });
                oneButtonDialog.show();
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }
}
