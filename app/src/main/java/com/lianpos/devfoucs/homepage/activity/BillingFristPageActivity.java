package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.listviewlinkage.Activity.LinkageActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 联系人跳到开单盘点页
 * <p>
 * Created by wangshuai on 2017/11/06
 */
public class BillingFristPageActivity extends BaseActivity {

    private RelativeLayout addShopButton;
    private RelativeLayout view_inventory_btn;
    private ImageView billilg_frist_back;
    private TextView billing_Inventory_title;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_first);
        addShopButton = (RelativeLayout) findViewById(R.id.addShopButton);
        addShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BillingFristPageActivity.this, IWantBillingActivity.class);
                startActivity(intent);
            }
        });
        view_inventory_btn = (RelativeLayout) findViewById(R.id.view_inventory_btn);
        view_inventory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BillingFristPageActivity.this, ViewInventoryActivity.class);
                startActivity(intent);
            }
        });
        billilg_frist_back = (ImageView) findViewById(R.id.billilg_frist_back);
        billilg_frist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        billing_Inventory_title = (TextView) findViewById(R.id.billing_Inventory_title);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String billingInventory = "";
        for (JanePinBean guest : guests) {
            billingInventory = guest.BillingInventoryCode;
        }
        if (billingInventory.equals("1")){
            billing_Inventory_title.setText("我要盘点");
            view_inventory_btn.setVisibility(View.GONE);
        }else{
            billing_Inventory_title.setText("我要开单");
            view_inventory_btn.setVisibility(View.VISIBLE);
        }
    }

}