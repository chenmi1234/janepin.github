package com.lianpos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianpos.activity.R;

/**
 * 个人中心
 * @author wangshuai
 * @create time 2017/10/27
 */
public class PersonFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_person, null);
		return rootView;
	}
}
