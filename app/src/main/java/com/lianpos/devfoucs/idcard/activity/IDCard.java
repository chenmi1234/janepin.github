package com.lianpos.devfoucs.idcard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 身份卡操作类
 * Created by wangshuai on 2017/10/27 0027.
 */

public class IDCard extends Activity implements View.OnClickListener {

    private ImageView zl_back;
    private Realm realm = null;
    private ListView listView;
    List<String> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_pp);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String ywUserId = "";
        for (JanePinBean guest : guests) {
            ywUserId = guest.ywUserId;
        }
        try {
            runIDCard(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init();
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
        listView = (ListView) findViewById(R.id.idCardListview);
        if (StringUtil.isNotNull(data)){
            listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,data));
        }
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
                    JSONArray resultBrandList = paramJson.getJSONArray("brand_list");
                    if ("1".equals(resultFlag)) {
                        if (StringUtil.isNotNull(resultBrandList)) {
                            data = new ArrayList<String>();
                            for (int i = 0; i < resultBrandList.size(); i++) {
                                JSONObject info = resultBrandList.getJSONObject(i);
                                String spBrand = info.getString("sp_brand");
                                if (StringUtil.isNotNull(spBrand)) {
                                    data.add(spBrand);
                                }
                            }
                        }
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

}
