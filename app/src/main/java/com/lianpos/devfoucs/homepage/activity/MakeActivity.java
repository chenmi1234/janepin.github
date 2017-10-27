package com.lianpos.devfoucs.homepage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 首页Activity
 * @author wangshuai
 * Created by wangshuai on 2017/10/27
 */
public class MakeActivity extends BaseActivity {
	@Override
	public void widgetClick(View v) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
	}

	/**
	 * 解析bundle内容或者设置是否旋转，沉浸，全屏
	 * @param parms
	 */
	@Override
	public void initParms(Bundle parms) {

	}

	@Override
	public View bindView() {
		return null;
	}

	@Override
	public int bindLayout() {
		return 0;
	}

	@Override
	public void initView(View view) {

	}

	@Override
	public void setListener() {

	}

	@Override
	public void doBusiness(BaseActivity mContext) {

	}
}
