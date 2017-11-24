package com.lianpos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.activity.InquirySheetActivity;
import com.lianpos.devfoucs.homepage.activity.MakeMoneyActivity;
import com.lianpos.devfoucs.homepage.activity.ViewInventoryActivity;
import com.lianpos.devfoucs.linkman.ui.LinkManActivity;
import com.lianpos.devfoucs.view.TwoButtonBillingDialog;
import com.lianpos.devfoucs.view.TwoButtonWarningDialog;
import com.lianpos.entity.JanePinBean;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 首页
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // 开单
    private ImageView billing;
    // 我要赚钱
    private ImageView makeMoney;
    // 盘点
    private ImageView inventory;
    // 询价单
    private ImageView inquiry_sheet;
    // 两个按钮的dialog
    private TwoButtonBillingDialog twoButtonDialog;
    private Realm realm = null;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_make, null);
        ButterKnife.bind(rootView);
        realm = Realm.getDefaultInstance();
        init();
        return rootView;
    }

    private void init() {
        // 初始化id
        initID();
        // 初始化点击事件
        initOnClick();
    }

    // 初始化id
    private void initID() {
        billing = (ImageView) rootView.findViewById(R.id.billing);
        makeMoney = (ImageView) rootView.findViewById(R.id.makeMoney);
        inventory = (ImageView) rootView.findViewById(R.id.inventory);
        inquiry_sheet = (ImageView) rootView.findViewById(R.id.inquiry_sheet);
    }

    // 初始化点击事件
    private void initOnClick() {
        billing.setOnClickListener(this);
        makeMoney.setOnClickListener(this);
        inventory.setOnClickListener(this);
        inquiry_sheet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.billing:
                twoButtonDialog = new TwoButtonBillingDialog(getActivity());
                twoButtonDialog.setYesOnclickListener(new TwoButtonBillingDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.BillingInventoryCode = "1";
                        realm.commitTransaction();

                        Intent billingIntent = new Intent();
                        billingIntent.setClass(getActivity(), LinkManActivity.class);
                        startActivity(billingIntent);
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.setNoOnclickListener(new TwoButtonBillingDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.BillingInventoryCode = "0";
                        realm.commitTransaction();

                        Intent billingIntent = new Intent();
                        billingIntent.setClass(getActivity(), LinkManActivity.class);
                        startActivity(billingIntent);
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.show();
                break;
            case R.id.makeMoney:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewInventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.inventory:
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.BillingInventoryCode = "1";
                realm.commitTransaction();
                Intent pandianIntent = new Intent();
                pandianIntent.setClass(getActivity(), LinkManActivity.class);
                startActivity(pandianIntent);
                break;
            case R.id.inquiry_sheet:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), InquirySheetActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
