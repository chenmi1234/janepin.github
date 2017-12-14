package com.lianpos.devfoucs.shopmanage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ShopManageActivity extends AppCompatActivity {
    private ListView lv_left;
    private ListView lv_right;
    ArrayList<ShopManageBean.AddressEntity> leftlist;
    private List<ShopManageBean.AddressAreaEntity> rightlist;
    private LeftAdapter leftAdapter;
    private RelativeLayout addShopButton;
    private ImageView merchant_back;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_manage_main);
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
        merchant_back = (ImageView) findViewById(R.id.merchant_back);
        merchant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addShopButton = (RelativeLayout) findViewById(R.id.addShopButton);
        addShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShopManageActivity.this, IncreaseCommodityActivity.class);
                startActivity(intent);
            }
        });
        initModle();
        leftAdapter = new LeftAdapter(ShopManageActivity.this, leftlist);
        lv_left.setAdapter(leftAdapter);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rightlist = leftlist.get(i).getSp_list();
                initAdapter(rightlist);
                leftAdapter.notifyDataSetChanged();
            }
        });

        initAdapter( leftlist.get(0).getSp_list());

    }

    private void initAdapter(List<ShopManageBean.AddressAreaEntity> rightlist) {
        RightAdapter rightAdapter = new RightAdapter(ShopManageActivity.this, rightlist);
        lv_right.setAdapter(rightAdapter);
        rightAdapter.notifyDataSetChanged();
    }

    private void initModle() {
        // 从本地缓存中获取城市信息
        SharedPreferences sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE);
        String ywUserId = sharedPreferences.getString("result_id", "");

        try {
            runShopData(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    /**
     * 获取商品数据
     * post请求后台
     */
    private void runShopData(final String ywUserId) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("yw_user_id", ywUserId);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = CallAPIUtil.ObtainFun(json, Common.obtainShopUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    JSONArray resultInfoList = paramJson.getJSONArray("info_list");
                    if ("1".equals(resultFlag)) {
                        if (StringUtil.isNotNull(resultInfoList)) {
                            leftlist = new ArrayList<ShopManageBean.AddressEntity>();
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<ShopManageBean>() {
                            }.getType();
                            ShopManageBean b = gson.fromJson(result, type);
                            leftlist.addAll(b.getInfo_list());
                        }
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }
}
