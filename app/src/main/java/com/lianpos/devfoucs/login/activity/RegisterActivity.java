package com.lianpos.devfoucs.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.view.OneButtonWarningDialog;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

import butterknife.ButterKnife;
import io.realm.Realm;

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
    private LinearLayout deleteImg;
    // 下一步
    private Button next_button;
    // 返回
    private ImageView register_back;
    // 注册账号message
    private TextView registerPhoneMessage;
    // 一个按钮的dialog
    private OneButtonWarningDialog oneButtonDialog;
    // 确认密码message
    private TextView password_confirm_message;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
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
        // 手机号输入（账号）
        register_phone_editText = (EditText) findViewById(R.id.register_phone_editText);
        // 密码输入
        register_password_editText = (EditText) findViewById(R.id.register_password_editText);
        // 确认密码输入
        register_pwd_editText = (EditText) findViewById(R.id.register_pwd_editText);
        // 手机号删除
        deleteImg = (LinearLayout) findViewById(R.id.deleteImg);
        // 下一步
        next_button = (Button) findViewById(R.id.next_button);
        // 返回
        register_back = (ImageView) findViewById(R.id.register_back);
        // 注册账号message
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
        password_confirm_message = (TextView) findViewById(R.id.password_confirm_message);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 手机号删除
        deleteImg.setOnClickListener(this);
        // 下一步
        next_button.setOnClickListener(this);
        // 返回
        register_back.setOnClickListener(this);
        password_confirm_message.setOnClickListener(this);
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

        deleteImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button:
                if (register_phone_editText.getText().toString().isEmpty() || register_password_editText.getText().toString().isEmpty() || register_pwd_editText.getText().toString().isEmpty()) {
                    oneButtonDialog = new OneButtonWarningDialog(RegisterActivity.this);
                    oneButtonDialog.setYesOnclickListener(new OneButtonWarningDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            oneButtonDialog.dismiss();
                        }
                    });
                    oneButtonDialog.show();
                } else if (!register_password_editText.getText().toString().equals(register_pwd_editText.getText().toString())) {
                    password_confirm_message.setVisibility(View.VISIBLE);
                } else if (!CheckInforUtils.isMobile(register_phone_editText.getText().toString())) {
                    registerPhoneMessage.setVisibility(View.VISIBLE);
                    registerPhoneMessage.setText("请输入正确的手机号");
                } else {
                    password_confirm_message.setVisibility(View.GONE);
                    registerPhoneMessage.setVisibility(View.GONE);
                    realm.beginTransaction();
                    JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                    janePinBean.PhoneNumber = register_phone_editText.getText().toString();
                    janePinBean.Psw = register_password_editText.getText().toString();
                    janePinBean.ConPsw = register_pwd_editText.getText().toString();
                    realm.commitTransaction();
                    Intent intent1 = new Intent();
                    intent1.setClass(RegisterActivity.this, RegisterInfoActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.register_back:
                finish();
                break;
            case R.id.deleteImg:
                register_phone_editText.setText("");
                break;
        }
    }
}
