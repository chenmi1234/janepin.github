package com.lianpos.devfoucs.reportform.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.lianpos.devfoucs.view.OneButtonSuccessDialog;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.CheckInforUtils;
import com.lianpos.util.WeiboDialogUtils;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;

import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/31.
 */

public class EditerAreaActivity extends BaseActivity implements View.OnClickListener {
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
    // 保存
    private TextView enterprise_editer_text;
    // 返回
    private ImageView register_back;
    // 手机号check
    private TextView registerPhoneMessage;
    // 一个按钮的dialog
    private OneButtonSuccessDialog oneButtonDialog;
    Realm realm;
    String ywUserId = "";
    private Dialog mWeiboDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_area);
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
        // 保存
        enterprise_editer_text = (TextView) findViewById(R.id.enterprise_editer_text);
        // 返回
        register_back = (ImageView) findViewById(R.id.enterprise_back);
        // 手机号验证
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 区域选择
        area_choose.setOnClickListener(this);
        // 保存
        enterprise_editer_text.setOnClickListener(this);
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                CityPickerView cityPicker = new CityPickerView.Builder(EditerAreaActivity.this).textSize(20)
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

                oneButtonDialog = new OneButtonSuccessDialog(EditerAreaActivity.this);
                oneButtonDialog.setYesOnclickListener(new OneButtonSuccessDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent1 = new Intent();
                        intent1.setClass(EditerAreaActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                });
                oneButtonDialog.show();
                break;
            case R.id.register_back:
                finish();
                break;
            case R.id.enterprise_editer_text:
                String enterpriseName = enterprise_name.getText().toString();
                String supplierName = supplier_name.getText().toString();
                String bossPhone = boss_phone.getText().toString();
                String areaText = area_text.getText().toString();
                String detailedAddress = detailed_address.getText().toString();
                try {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(EditerAreaActivity.this, "加载中...");
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                    realm.commitTransaction();
                    for (JanePinBean guest : guests) {
                        ywUserId = guest.ywUserId;
                    }

                    if (enterpriseName.equals("") || supplierName.equals("") || bossPhone.equals("") || areaText.equals("") || detailedAddress.equals("")){
                        Toast.makeText(this, "信息不全请填写完整", Toast.LENGTH_SHORT).show();
                    }else{
                        runEditerArea(enterpriseName, supplierName, bossPhone, areaText, detailedAddress, ywUserId);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }
    }


    /**
     * 企业信息修改
     * post请求后台
     */
    private void runEditerArea(final String enterpriseName, final String supplierName, final String bossPhone, final String areaText, final String detailedAddress, final String ywUserId) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("user_supplier_name", enterpriseName);
                    jsonObject.put("supplier_compellation", supplierName);
                    jsonObject.put("supplier_phone", bossPhone);
                    jsonObject.put("user_area", areaText);
                    jsonObject.put("user_address", detailedAddress);
                    jsonObject.put("yw_user_id", ywUserId);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.editInfoUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    if ("1".equals(resultFlag)) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                        realm.commitTransaction();
                        String ywUserId = "";
                        for (JanePinBean guest : guests) {
                            ywUserId = guest.ywUserId;
                        }

                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.modifyEnterDialog = "1";
                        janePinBean.ywUserId = ywUserId;
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
