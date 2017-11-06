package com.lianpos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.login.activity.RegisterActivity;
import com.lianpos.devfoucs.reportform.activity.ReportForm;

/**
 * 个人中心
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class PersonFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout person_zl;
    View rootView;

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
    }

    // 初始化点击事件
    private void initOnClick() {
        person_zl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()) {
			case R.id.person_zl:
                Intent intent = new Intent();
                intent.setClass(getContext(), ReportForm.class);
                startActivity(intent);
				break;
		}
    }
}
