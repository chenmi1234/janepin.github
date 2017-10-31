package com.lianpos.devfoucs.login.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.view.OneButtonDialog;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/26.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    // 手机号输入
    private EditText register_phone_editText;
    // 密码输入
    private EditText register_password_editText;
    // 确认密码输入
    private EditText register_pwd_editText;
    // 手机号删除
    private ImageView deleteImg;
    // 密码删除
    private ImageView pwdDeleteImg;
    // 确认密码删除
    private ImageView deleteQrpwdImg;
    // 下一步
    private Button next_button;
    // 返回
    private ImageView register_back;
    // 注册账号message
    private TextView registerPhoneMessage;
    // 一个按钮的dialog
    private OneButtonDialog oneButtonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
    @SuppressLint("WrongViewCast")
    private void initActivity() {
        // 手机号输入（账号）
        register_phone_editText = (EditText) findViewById(R.id.register_phone_editText);
        // 密码输入
        register_password_editText = (EditText) findViewById(R.id.register_password_editText);
        // 确认密码输入
        register_pwd_editText = (EditText) findViewById(R.id.register_pwd_editText);
        // 手机号删除
        deleteImg = (ImageView) findViewById(R.id.deleteImg);
        // 密码删除
        pwdDeleteImg = (ImageView) findViewById(R.id.pwdDeleteImg);
        // 确认密码删除
        deleteQrpwdImg = (ImageView) findViewById(R.id.deleteQrpwdImg);
        // 下一步
        next_button = (Button) findViewById(R.id.next_button);
        // 返回
        register_back = (ImageView) findViewById(R.id.register_back);
        // 注册账号message
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 手机号删除
        deleteImg.setOnClickListener(this);
        // 密码删除
        pwdDeleteImg.setOnClickListener(this);
        // 确认密码删除
        deleteQrpwdImg.setOnClickListener(this);
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
        register_phone_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!register_phone_editText.getText().toString().isEmpty()) {
                    if (CheckInforUtils.isMobile(register_phone_editText.getText().toString())) {
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

        // 密码输入结束监听
        register_password_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });

        // 密码输入结束监听
        register_pwd_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteImg:
                break;
            case R.id.pwdDeleteImg:
                break;
            case R.id.deleteQrpwdImg:
                break;
            case R.id.next_button:
                if (register_phone_editText.getText().toString().isEmpty()){
                    oneButtonDialog = new OneButtonDialog(RegisterActivity.this);
                    oneButtonDialog.setYesOnclickListener(new OneButtonDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {

                        }
                    });
                    oneButtonDialog.show();
                }else{
                    Intent intent1 = new Intent();
                    intent1.setClass(RegisterActivity.this, RegisterInfoActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }
}
