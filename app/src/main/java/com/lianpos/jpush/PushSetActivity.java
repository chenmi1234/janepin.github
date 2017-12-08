package com.lianpos.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.login.activity.LoginActivity;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class PushSetActivity extends FragmentActivity {
    private static final String TAG = "JPush";

	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		setTag("abc");
		Handler handler = new Handler();
		//当计时结束,跳转至主界面
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(PushSetActivity.this, LoginActivity.class);
				startActivity(intent);
				PushSetActivity.this.finish();
			}
		}, 2000);
	}



	/**
	 * 设置tags
	 */
	private void setTag(String tag){

        // 检查 tag 的有效性
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(PushSetActivity.this, R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		// ","隔开的多个 转换成 Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	

	
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "设置别名和标签成功！";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "设置超时，60s后重试！";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                } else {
                	Log.i(TAG, "没有连接网络");
                }
                break;
            
            default:
                logs = "失败代码 = " + code;
                Log.e(TAG, logs);
            }
            
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
        
    };
	private static final int MSG_SET_TAGS = 1002;


	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            case MSG_SET_TAGS:
                Log.d(TAG, "在handler里面设置tags");
                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
                
            default:
                Log.i(TAG, "handler没有内容 - " + msg.what);
            }
        }
    };

	public static boolean isForeground = false;

	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
		JPushInterface.onResume(PushSetActivity.this);
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
		JPushInterface.onPause(PushSetActivity.this);
	}

}