package com.lianpos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

	private LinearLayout no_brand;
	private RelativeLayout frist_brand;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_idcard, null);
		no_brand = (LinearLayout) rootView.findViewById(R.id.no_brand);
		frist_brand = (RelativeLayout) rootView.findViewById(R.id.frist_brand);
		if (frist_brand.getVisibility() == View.VISIBLE){
			no_brand.setVisibility(View.GONE);
		}
		return rootView;
	}
}
