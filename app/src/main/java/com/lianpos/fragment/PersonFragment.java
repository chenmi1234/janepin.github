package com.lianpos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianpos.activity.R;

/**
 * 个人中心
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class PersonFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person, null);
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
    }

    // 初始化点击事件
    private void initOnClick() {
    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.inquiry_sheet_back:
//				break;
//		}
    }
}
