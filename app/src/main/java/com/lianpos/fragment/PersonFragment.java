package com.lianpos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.login.activity.RegisterAreaActivity;

/**
 * 个人中心
 * @author wangshuai
 * @create time 2017/10/27
 */
public class PersonFragment extends Fragment {

	private TextView toLoginPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_person, null);
		toLoginPage = (TextView) rootView.findViewById(R.id.toLoginPage);
		toLoginPage.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}
