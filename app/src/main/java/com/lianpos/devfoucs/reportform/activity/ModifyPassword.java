package com.lianpos.devfoucs.reportform.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

/**
 * 修改密码
 * Created by wangshuai on 2017/11/07
 */

public class ModifyPassword extends BaseActivity implements View.OnClickListener {

    private ImageView modify_password_back,oldPassword, newPassword, aginNewPassword;
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHideFirst = true;
    // 显示/隐藏密码
    private EditText old_password_editText, new_password_editText, agin_password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
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
        modify_password_back = (ImageView) findViewById(R.id.modify_password_back);
        oldPassword = (ImageView) findViewById(R.id.oldPassword);
        newPassword = (ImageView) findViewById(R.id.newPassword);
        aginNewPassword = (ImageView) findViewById(R.id.aginNewPassword);
        // 默认小眼睛图片
        oldPassword.setImageResource(R.mipmap.eye);
        newPassword.setImageResource(R.mipmap.eye);
        aginNewPassword.setImageResource(R.mipmap.eye);
        old_password_editText = (EditText) findViewById(R.id.old_password_editText);
        new_password_editText = (EditText) findViewById(R.id.new_password_editText);
        agin_password_editText = (EditText) findViewById(R.id.agin_password_editText);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        modify_password_back.setOnClickListener(this);
        oldPassword.setOnClickListener(this);
        newPassword.setOnClickListener(this);
        aginNewPassword.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        // 密码输入结束监听
        old_password_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_password_back:
                finish();
            break;
            case R.id.oldPassword:
                if (isHideFirst) {
                    oldPassword.setImageResource(R.mipmap.visible);
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    old_password_editText.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    oldPassword.setImageResource(R.mipmap.eye);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    old_password_editText.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index = old_password_editText.getText().toString().length();
                old_password_editText.setSelection(index);
                break;
            case R.id.newPassword:
                if (isHideFirst) {
                    newPassword.setImageResource(R.mipmap.visible);
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    new_password_editText.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    newPassword.setImageResource(R.mipmap.eye);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    new_password_editText.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index2 = new_password_editText.getText().toString().length();
                new_password_editText.setSelection(index2);
                break;
            case R.id.aginNewPassword:
                if (isHideFirst) {
                    aginNewPassword.setImageResource(R.mipmap.visible);
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    agin_password_editText.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    oldPassword.setImageResource(R.mipmap.eye);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    agin_password_editText.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index3 = agin_password_editText.getText().toString().length();
                agin_password_editText.setSelection(index3);
                break;
        }
    }

}