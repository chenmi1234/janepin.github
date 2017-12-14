package com.lianpos.devfoucs.shopmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;

import java.util.ArrayList;

public class ShopManageActivity extends AppCompatActivity {
    private ListView lv_left;
    private ListView lv_right;
    ArrayList<ShopManageBean.AddressEntity> leftlist;
    private ArrayList<String> rightlist;
    private LeftAdapter leftAdapter;
    private RelativeLayout addShopButton;
    private ImageView merchant_back;

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
                rightlist = (ArrayList<String>) leftlist.get(i).getArea();
                initAdapter(rightlist);
                leftAdapter.notifyDataSetChanged();
            }
        });

        initAdapter((ArrayList<String>) leftlist.get(0).getArea());

    }

    private void initAdapter(ArrayList<String> rightlist) {
        RightAdapter rightAdapter = new RightAdapter(ShopManageActivity.this, rightlist);
        lv_right.setAdapter(rightAdapter);
        rightAdapter.notifyDataSetChanged();
    }

    private void initModle() {
        leftlist = new ArrayList<ShopManageBean.AddressEntity>();
        Gson gson = new Gson();
        String json = "{\"result\":\"Y\", \"address\":[{\"name\":\"衣服\",\"custId\":\""
                + "\", \"area\":[\"呢大衣\",\"小西服\",\"背心\",\"外套\",\"运动服\"]},{\"name\":\"裤子\", \"custId\":\""
                + "\",\"area\":[\"长裤子\",\"短裤子\",\"秋裤\",\"牛仔裤\",\"优衣库\",\"各种裤子\",\"短裤子\"]}]}";
        java.lang.reflect.Type type = new TypeToken<ShopManageBean>() {
        }.getType();
        ShopManageBean b = gson.fromJson(json, type);
        leftlist.addAll(b.getAddress());
    }
}
