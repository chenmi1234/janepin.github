package com.lianpos.devfoucs.reportform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.idcard.activity.IDCard;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;

import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 个人资料
 * Created by wangshuai on 2017/11/06 .
 */

public class ReportForm extends BaseActivity implements View.OnClickListener {

    private Realm realm = null;
    private ImageView zl_back;
    private TextView brand1, brand2, brand3, brand4, brand5, brand6, brand7, brand8;
    private RelativeLayout frist_brand, brand2_layout, brand3_layout, brand4_layout, brand5_layout, brand6_layout, brand7_layout, brand8_layout;
    private LinearLayout no_brand;
    private TextView idCardName, idCardPhone, idCardSupplier,idCardSex,idCardBriday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_zl);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String ywUserId = "";
        for (JanePinBean guest : guests) {
            ywUserId = guest.ywUserId;
        }
        init();
        try {
            runIDCard(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (frist_brand.getVisibility() == View.VISIBLE) {
            no_brand.setVisibility(View.GONE);
        }
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        zl_back = (ImageView) findViewById(R.id.zl_back);
        idCardName = (TextView) findViewById(R.id.idCardName);
        idCardBriday = (TextView) findViewById(R.id.idCardBriday);
        idCardSex = (TextView) findViewById(R.id.idCardSex);
        idCardPhone = (TextView) findViewById(R.id.idCardPhone);
        idCardSupplier = (TextView) findViewById(R.id.idCardSupplier);
        brand1 = (TextView) findViewById(R.id.brand1);
        brand2 = (TextView) findViewById(R.id.brand2);
        brand3 = (TextView) findViewById(R.id.brand3);
        brand4 = (TextView) findViewById(R.id.brand4);
        brand5 = (TextView) findViewById(R.id.brand5);
        brand6 = (TextView) findViewById(R.id.brand6);
        brand7 = (TextView) findViewById(R.id.brand7);
        brand8 = (TextView) findViewById(R.id.brand8);
        frist_brand = (RelativeLayout) findViewById(R.id.frist_brand);
        brand2_layout = (RelativeLayout) findViewById(R.id.brand2_layout);
        brand3_layout = (RelativeLayout) findViewById(R.id.brand3_layout);
        brand4_layout = (RelativeLayout) findViewById(R.id.brand4_layout);
        brand5_layout = (RelativeLayout) findViewById(R.id.brand5_layout);
        brand6_layout = (RelativeLayout) findViewById(R.id.brand6_layout);
        brand7_layout = (RelativeLayout) findViewById(R.id.brand7_layout);
        brand8_layout = (RelativeLayout) findViewById(R.id.brand8_layout);
        no_brand = (LinearLayout) findViewById(R.id.no_brand);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        zl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zl_back:
                finish();
                break;
        }
    }

    /**
     * 获取身份卡数据
     * post请求后台
     */
    private void runIDCard(final String idCardStr) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("yw_user_id", idCardStr);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.idCardUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    String resultUserName = paramJson.getString("yw_user_name");
                    String resultUserPhone = paramJson.getString("yw_user_phone");
                    String resultUserSex = paramJson.getString("yw_sex");
                    String resultUserBirthday = paramJson.getString("yw_birthday");
                    String resultUserSupplier = paramJson.getString("user_supplier_name");
                    JSONArray resultBrandList = paramJson.getJSONArray("brand_list");
//					List<String> aaa = JSONArray.toJavaObject(resultBrandList, List.class);
                    if ("1".equals(resultFlag)) {
                        idCardName.setText(resultUserName);
                        idCardPhone.setText(resultUserPhone);
                        idCardSupplier.setText(resultUserSupplier);
                        idCardSex.setText(resultUserSex);
                        idCardBriday.setText(resultUserBirthday);
                        if (StringUtil.isNotNull(resultBrandList)) {
                            for (int i = 0; i < resultBrandList.size(); i++) {
                                JSONObject info = resultBrandList.getJSONObject(i);
                                String spBrand = info.getString("sp_brand");
                                if (i == 0) {
                                    frist_brand.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand1.setText(spBrand);
                                    } else {
                                        frist_brand.setVisibility(View.GONE);
                                        brand6_layout.setVisibility(View.GONE);
                                        no_brand.setVisibility(View.GONE);
                                    }
                                } else if (i == 1) {
                                    brand2_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand2.setText(spBrand);
                                    } else {
                                    }
                                } else if (i == 2) {
                                    brand3_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand3.setText(spBrand);
                                    } else {
                                    }
                                } else if (i == 3) {
                                    brand4_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand4.setText(spBrand);
                                    } else {
                                    }
                                } else if (i == 4) {
                                    brand5_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand5.setText(spBrand);
                                    } else {
                                    }
                                }else if (i == 5) {
                                    brand6_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand6.setText(spBrand);
                                    } else {
                                    }
                                }else if (i == 6) {
                                    brand7_layout.setVisibility(View.VISIBLE);
                                    if (StringUtil.isNotNull(spBrand)) {
                                        brand7.setText(spBrand);
                                    } else {
                                    }
                                }
                            }
                        }

                        brand8_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent();
                                intent1.setClass(ReportForm.this, IDCard.class);
                                startActivity(intent1);
                            }
                        });
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

}