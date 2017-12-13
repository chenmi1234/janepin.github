package com.lianpos.devfoucs.login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.view.OneButtonSuccessDialog;
import com.lianpos.devfoucs.view.OneButtonWarningDialog;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.CheckInforUtils;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;

import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/31.
 */

public class RegisterAreaActivity extends BaseActivity implements View.OnClickListener {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
    // 一个按钮的dialog
    private OneButtonWarningDialog onewarnButtonDialog;
    // 用户协议
    private TextView agreement;
    final OkHttpClient client = new OkHttpClient();
    String pdStr = "";
    Realm realm;


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
        //用户协议
        agreement = (TextView) findViewById(R.id.agreement);
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

        agreement.setText(Html.fromHtml("注册既同意" + "<a href='http://www.baidu.com'>《简销用户服务协议》</a>"));
        agreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area_choose:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                CityPickerView cityPicker = new CityPickerView.Builder(RegisterAreaActivity.this).textSize(20)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province("山东省")
                        .city("济南市")
                        .district("市辖区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        area_text.setText(
                                province.getName() + "  " + city.getName() + "   " + district.getName());
                        area_text.setTextColor(Color.parseColor("#333333"));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.immediate_registration:

                if (enterprise_name.getText().toString().isEmpty() || supplier_name.getText().toString().isEmpty() || boss_phone.getText().toString().isEmpty() || area_text.getText().equals("请选择") || detailed_address.getText().toString().isEmpty()) {
                    onewarnButtonDialog = new OneButtonWarningDialog(RegisterAreaActivity.this);
                    onewarnButtonDialog.setYesOnclickListener(new OneButtonWarningDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            onewarnButtonDialog.dismiss();
                        }
                    });
                    onewarnButtonDialog.show();
                } else if (!CheckInforUtils.isMobile(boss_phone.getText().toString())) {
                    registerPhoneMessage.setVisibility(View.VISIBLE);
                    registerPhoneMessage.setText("请输入正确的手机号");
                } else {
                    registerPhoneMessage.setVisibility(View.GONE);

                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                    realm.commitTransaction();
                    String showName = "";
                    String showPaw = "";
                    String showConPaw = "";
                    String yw_user_name = "";
                    String yw_sex = "";
                    String yw_birthday = "";
                    for (JanePinBean guest : guests) {
                        showName = guest.PhoneNumber;
                        showPaw = guest.Psw;
                        showConPaw = guest.ConPsw;
                        yw_user_name = guest.yw_user_name;
                        yw_sex = guest.yw_sex;
                        yw_birthday = guest.yw_birthday;
                    }
                    try {
                        runRegist(showName, showPaw, yw_user_name, yw_sex, yw_birthday);
                        if ("1".equals(pdStr)){
//                            Toast.makeText(RegisterAreaActivity.this, "注册成功~", Toast.LENGTH_SHORT).show();
                            oneButtonDialog = new OneButtonSuccessDialog(RegisterAreaActivity.this);
                            oneButtonDialog.setYesOnclickListener(new OneButtonSuccessDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    Intent intent1 = new Intent();
                                    intent1.setClass(RegisterAreaActivity.this, LoginActivity.class);
                                    startActivity(intent1);
                                }
                            });
                            oneButtonDialog.show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }


    /**
     * post请求后台
     */
    private void runRegist(final String showName, final String showPaw, final String yw_user_name, final String yw_sex, final String yw_birthday) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("yw_user_phone", showName);
                    jsonObject.put("yw_user_password", showPaw);
                    jsonObject.put("yw_user_name", yw_user_name);
                    jsonObject.put("yw_sex", "1");
                    jsonObject.put("yw_birthday", yw_birthday);
                    jsonObject.put("user_supplier_name", enterprise_name.getText().toString());
                    jsonObject.put("supplier_compellation", supplier_name.getText().toString());
                    jsonObject.put("supplier_phone", boss_phone.getText().toString());
                    jsonObject.put("user_area", area_text.getText().toString());
                    jsonObject.put("user_address", detailed_address.getText().toString());
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.registerUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = com.alibaba.fastjson.JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    if ("1".equals(resultFlag)) {
                        pdStr = "1";
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }
}
