package com.lianpos.devfoucs.login.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.firebase.BaseActivity;

/**
 * Description: 登陆
 * Created by SuGuoYu on 2017/9/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //用户名输入框
    private EditText etUsername;
    //密码输入框
    private EditText etPwd;
    //密码状态
    private ImageView ivPwdState;
    //登陆
    private TextView tvLogin;
    //注册
    private TextView tvRegister;
    //忘记密码
    private TextView tvForgetPwd;
    //微信登陆
    private ImageView ivWeiXin;

    private boolean showPwd = false;
    private RelativeLayout rlPwdState;
    private ImageView ivExit;

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
