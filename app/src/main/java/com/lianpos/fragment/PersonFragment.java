package com.lianpos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.login.activity.RegisterActivity;
import com.lianpos.devfoucs.reportform.activity.EnterpriseInformation;
import com.lianpos.devfoucs.reportform.activity.ModifyPassword;
import com.lianpos.devfoucs.reportform.activity.PrinterActivity;
import com.lianpos.devfoucs.reportform.activity.ReportForm;
import com.lianpos.devfoucs.shoppingcart.CommodityManagementActivity;
import com.lianpos.devfoucs.shoppingcart.MerchantActivity;
import com.lianpos.devfoucs.view.TwoButtonLoginoutDialog;
import com.lianpos.devfoucs.view.TwoButtonWarningDialog;

/**
 * 个人中心
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class PersonFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout person_zl,modifyPassword,enterprise_informtion,commodity_management,printer_layout,loginout;
    View rootView;
    // 两个按钮的dialog
    private TwoButtonLoginoutDialog twoButtonDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person, null);
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
        person_zl = (RelativeLayout)rootView.findViewById(R.id.person_zl);
        modifyPassword = (RelativeLayout)rootView.findViewById(R.id.modify_password);
        enterprise_informtion = (RelativeLayout)rootView.findViewById(R.id.enterprise_informtion);
        printer_layout = (RelativeLayout)rootView.findViewById(R.id.printer_layout);
        commodity_management = (RelativeLayout)rootView.findViewById(R.id.commodity_management);
        loginout = (RelativeLayout)rootView.findViewById(R.id.loginout);
    }

    // 初始化点击事件
    private void initOnClick() {
        person_zl.setOnClickListener(this);
        modifyPassword.setOnClickListener(this);
        enterprise_informtion.setOnClickListener(this);
        printer_layout.setOnClickListener(this);
        loginout.setOnClickListener(this);
        commodity_management.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()) {
			case R.id.person_zl:
                Intent intent = new Intent();
                intent.setClass(getContext(), ReportForm.class);
                startActivity(intent);
				break;
            case R.id.modify_password:
                Intent intent1 = new Intent();
                intent1.setClass(getContext(), ModifyPassword.class);
                startActivity(intent1);
                break;
            case R.id.enterprise_informtion:
                Intent intent2 = new Intent();
                intent2.setClass(getContext(), EnterpriseInformation.class);
                startActivity(intent2);
                break;
            case R.id.commodity_management:
                Intent commodity = new Intent();
                commodity.setClass(getContext(), CommodityManagementActivity.class);
                startActivity(commodity);
                break;
            case R.id.printer_layout:
                Intent printer = new Intent();
                printer.setClass(getContext(), PrinterActivity.class);
                startActivity(printer);
                break;
            case R.id.loginout:
                twoButtonDialog = new TwoButtonLoginoutDialog(getActivity());
                twoButtonDialog.setYesOnclickListener(new TwoButtonLoginoutDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent3 = new Intent();
                        intent3.setClass(getContext(), LoginActivity.class);
                        startActivity(intent3);
                        getActivity().onBackPressed();
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.setNoOnclickListener(new TwoButtonLoginoutDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.show();
                break;
		}
    }
}
