package com.lianpos.devfoucs.reportform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;

import java.net.URLEncoder;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 企业信息
 * Created by wangshuai on 2017/11/06 .
 */

public class EnterpriseInformation extends BaseActivity implements View.OnClickListener {

    private ImageView enterprise_back;
    private TextView enterprise_editer;
    private TextView enterName, enterPhone, enterAddress, enterReName;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_information);
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
            runInformation(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.bind(EnterpriseInformation.this);
        realm = Realm.getDefaultInstance();

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String modifyNumber = "0";
        for (JanePinBean guest : guests) {
            modifyNumber = guest.modifyEnterDialog;
        }

        if ("1".equals(modifyNumber)) {
            Toast.makeText(EnterpriseInformation.this, "修改成功", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        enterprise_back = (ImageView) findViewById(R.id.enterprise_back);
        enterprise_editer = (TextView) findViewById(R.id.enterprise_editer);
        enterName = (TextView) findViewById(R.id.enterName);
        enterPhone = (TextView) findViewById(R.id.enterPhone);
        enterAddress = (TextView) findViewById(R.id.enterAddress);
        enterReName = (TextView) findViewById(R.id.enterReName);

    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        enterprise_back.setOnClickListener(this);
        enterprise_editer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterprise_back:
                finish();
                break;
            case R.id.enterprise_editer:
                Intent intent = new Intent();
                intent.setClass(EnterpriseInformation.this, EditerAreaActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 获取企业信息
     * post请求后台
     */
    private void runInformation(final String idCardStr) throws InterruptedException {
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
                    String resultSupName = paramJson.getString("user_supplier_name");
                    String resultSupPhone = paramJson.getString("supplier_phone");
                    String resultAddSex = paramJson.getString("user_address");
                    String resultSupCom = paramJson.getString("supplier_compellation");
                    if ("1".equals(resultFlag)) {
                        enterName.setText(resultSupName);
                        enterPhone.setText(resultSupPhone);
                        enterAddress.setText(resultAddSex);
                        enterReName.setText(resultSupCom);
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

}