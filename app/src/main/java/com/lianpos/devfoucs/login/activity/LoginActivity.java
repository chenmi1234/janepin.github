package com.lianpos.devfoucs.login.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.CheckInforUtils;
import com.lianpos.util.JumpUtil;
import com.lianpos.util.NetUtil;
import com.lianpos.util.WeiboDialogUtils;

import java.net.URLEncoder;

import io.realm.Realm;
import okhttp3.MediaType;

/**
 * Description: 登陆
 * Created by wangshuai on 2017/10/27.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    // 手机号输入
    private EditText phone_edittext;
    // 显示/隐藏密码
    private EditText password_editText;
    // 眼睛
    private ImageView imageView;
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHideFirst = true;
    // 确认按钮
    private Button login_button;
    // 跳转共通方法
    private JumpUtil jumpUtil;
    // 输入框密码是否是隐藏的，默认为true
    private boolean isHidePwd = true;
    // 电话号Message
    private TextView phoneMessage;
    // 密码Message
    private TextView passwordMessage;
    // 注册
    private TextView registerText;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private RelativeLayout wifi_layout;
    private Dialog mWeiboDialog;
    Realm realm;
    String resultId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //启动时判断网络状态
        boolean netConnect = this.isNetConnect();
        if (netConnect) {
            wifi_layout.setVisibility(View.GONE);
        } else {
            wifi_layout.setVisibility(View.VISIBLE);
        }
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
        phone_edittext = (EditText) findViewById(R.id.phone_edittext);
        // 密码输入
        password_editText = (EditText) findViewById(R.id.password_editText);
        // 密码小眼睛显示隐藏密码
        imageView = (ImageView) findViewById(R.id.imageView);
        // 默认小眼睛图片
        imageView.setImageResource(R.mipmap.eye);
        // 登录按钮
        login_button = (Button) findViewById(R.id.login_button);
        // 电话号Message
        phoneMessage = (TextView) findViewById(R.id.phoneMessage);
        // 密码Message
        passwordMessage = (TextView) findViewById(R.id.passwordMessage);
        // 注册
        registerText = (TextView) findViewById(R.id.registerText);
        wifi_layout = (RelativeLayout) findViewById(R.id.wifi_layout);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 小眼睛点击事件
        imageView.setOnClickListener(this);
        // 登录按钮点击事件
        login_button.setOnClickListener(this);
        // 注册点击事件
        registerText.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        // 账号输入结束监听
        phone_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String phoneNo1Str = "";
                if (!phone_edittext.getText().toString().isEmpty()) {
                    phoneNo1Str = phone_edittext.getText().toString().substring(0, 1);
                    if (CheckInforUtils.isMobile(phone_edittext.getText().toString())) {
                        phoneMessage.setVisibility(View.GONE);
                    } else {
                        phoneMessage.setText("请输入正确的手机号");
                        phoneMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    phoneMessage.setText("请输入账号");
                    phoneMessage.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // 密码输入结束监听
        password_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (password_editText.getText().toString().isEmpty()) {
                    passwordMessage.setVisibility(View.VISIBLE);
                } else {
                    passwordMessage.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                if (isHideFirst) {
                    imageView.setImageResource(R.mipmap.visible);
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    password_editText.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    imageView.setImageResource(R.mipmap.eye);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    password_editText.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index = password_editText.getText().toString().length();
                password_editText.setSelection(index);
                break;
            case R.id.login_button:

                if (wifi_layout.getVisibility() == View.GONE) {
                    if (phone_edittext.getText().toString().isEmpty() || password_editText.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "请输入账号或密码", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        if (CheckInforUtils.isMobile(phone_edittext.getText().toString())) {
                            try {
                                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "加载中...");
                                runLogin(phone_edittext.getText().toString(), password_editText.getText().toString());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                } else {
                    setWifi4();
                }
                break;
            case R.id.registerText:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void setWifi4() {
        Intent i = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            //Honeycomb
            i.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
        } else {
            //other versions
            i.setClassName("com.android.settings"
                    , "com.android.settings.wifi.WifiSettings");
        }
        startActivity(i);
    }

    private void setWifi3() {
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    private void setWifi2() {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        startActivity(intent);
    }

    private void setWifi1() {
        Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
        startActivity(wifiSettingsIntent);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            wifi_layout.setVisibility(View.VISIBLE);
        } else {
            wifi_layout.setVisibility(View.GONE);
        }
    }

    /**
     * 登录方法
     * post请求后台
     */
    private void runLogin(final String showName, final String showPaw) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("yw_user_phone", showName);
                    jsonObject.put("yw_user_password", showPaw);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.loginUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    resultId = paramJson.getString("yw_user_id");
                    if ("1".equals(resultFlag)) {

                        // 1.实例化SharedPreferences对象（第一步）
                        SharedPreferences mySharedPreferences = getSharedPreferences("resultinfo", MODE_PRIVATE);
                        // 2. 实例化SharedPreferences.Editor对象（第二步）
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        // 3. 用putString的方法保存数据
                        editor.putString("result_id", resultId);
                        // 4. 提交当前数据
                        editor.commit();


                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.ywUserId = resultId;
                        realm.commitTransaction();
                        Intent intent1 = new Intent();
                        intent1.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent1);
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }
}
