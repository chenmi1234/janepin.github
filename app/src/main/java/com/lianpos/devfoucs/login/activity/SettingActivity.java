package com.lianpos.devfoucs.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.firebase.BaseActivity;


/**
 * Description：设置
 * Created by wangshuai
 * 2017/10/26
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //修改密码
    private RelativeLayout rlChangePwd;
    //意见反馈
    private RelativeLayout rlSuggest;
    //登出
    private TextView logoutTextView;
    //好友验证
    private ImageView confirmFriendImageView;

    private boolean needConfirmFriend;

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

