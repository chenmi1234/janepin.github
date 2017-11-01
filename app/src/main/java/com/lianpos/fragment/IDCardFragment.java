package com.lianpos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.idcard.activity.IDCard;
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.login.activity.RegisterActivity;
import com.lianpos.util.JumpUtil;

/**
 * 身份卡
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class IDCardFragment extends Fragment {
	private TextView idcard_text;
	private JumpUtil jumpUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_idcard, null);
		idcard_text = (TextView) rootView.findViewById(R.id.idcard_text);
		idcard_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpUtil.jumpFun(getActivity(), RegisterActivity.class);
			}
		});
		return rootView;
	}
}
