package com.lianpos.devfoucs.reportform.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.WeiboDialogUtils;

import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 修改密码
 * Created by wangshuai on 2017/11/07
 */

public class ModifyPassword extends BaseActivity implements View.OnClickListener {

    private ImageView modify_password_back, oldPassword, newPassword, aginNewPassword;
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHideFirst = true;
    // 显示/隐藏密码
    private EditText old_password_editText, new_password_editText, agin_password_editText;
    private TextView savePassword;
    private Dialog mWeiboDialog;
    Realm realm;
    String ywUserId = "";
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
        savePassword = (TextView) findViewById(R.id.savePassword);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        modify_password_back.setOnClickListener(this);
        oldPassword.setOnClickListener(this);
        newPassword.setOnClickListener(this);
        aginNewPassword.setOnClickListener(this);
        savePassword.setOnClickListener(this);
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
            case R.id.savePassword:
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                realm.commitTransaction();
                for (JanePinBean guest : guests) {
                    ywUserId = guest.ywUserId;
                }
                try {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(ModifyPassword.this, "加载中...");
                    runSavePassword(old_password_editText.getText().toString(), new_password_editText.getText().toString(), ywUserId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 密码修改
     * post请求后台
     */
    private void runSavePassword(final String showOldPwd, final String showNewPaw, final String ywUserId) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("old_password", showOldPwd);
                    jsonObject.put("new_password", showNewPaw);
                    jsonObject.put("yw_user_id", ywUserId);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.userPassUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    if ("1".equals(resultFlag)) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.modifyPswDialog = "1";
                        realm.commitTransaction();
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        finish();
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

}