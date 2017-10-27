package com.lianpos.devfoucs.login.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianpos.firebase.BaseActivity;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/26.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //用户名输入框
    private EditText etUsername;
    //密码输入框
    private EditText etPwd;
    //注册
    private TextView tvRegister;
    //协议
    private LinearLayout llAgreement;
    //协议选择
    private ImageView ivSelect;
    //验证码输入框
    private EditText etVerification;
    //获取验证码
    private TextView tvGetVerification;
    private TextView tvAgreement;

    private boolean isAgree = true;


    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(BaseActivity mContext) {

    }

}
