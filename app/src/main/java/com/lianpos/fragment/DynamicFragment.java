package com.lianpos.fragment;

import com.lianpos.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 动态
 * 
 * @author wangshuai
 * @create time 2017/10/27
 */
public class DynamicFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dynamic, null);
		return rootView;
	}
}
