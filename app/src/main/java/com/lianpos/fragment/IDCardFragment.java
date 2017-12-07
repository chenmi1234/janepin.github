package com.lianpos.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.util.CallAPIUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 身份卡
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class IDCardFragment extends Fragment {

	private LinearLayout no_brand;
	private RelativeLayout frist_brand;
	private Realm realm = null;
	private TextView idCardName,idCardPhone,idCardSupplier;
	private ImageView idCardImage;
	private TextView brand1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_idcard, null);

		realm = Realm.getDefaultInstance();
		realm.beginTransaction();
		RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
		realm.commitTransaction();
		String ywUserId = "";
		for (JanePinBean guest : guests) {
			ywUserId = guest.ywUserId;
		}
		no_brand = (LinearLayout) rootView.findViewById(R.id.no_brand);
		frist_brand = (RelativeLayout) rootView.findViewById(R.id.frist_brand);
		idCardName = (TextView) rootView.findViewById(R.id.idCardName);
		idCardPhone = (TextView) rootView.findViewById(R.id.idCardPhone);
		idCardSupplier = (TextView) rootView.findViewById(R.id.idCardSupplier);
		idCardImage = (ImageView) rootView.findViewById(R.id.idCardImage);
		brand1 = (TextView) rootView.findViewById(R.id.brand1);

		try {
			runIDCard(ywUserId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (frist_brand.getVisibility() == View.VISIBLE){
			no_brand.setVisibility(View.GONE);
		}
		return rootView;
	}

	/**
	 * 获取身份卡数据
	 * post请求后台
	 */
	private void runIDCard(final String idCardStr) throws InterruptedException {
		//处理注册逻辑
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				JSONObject jsonObject = new JSONObject();
				String json = "";
				try {
					jsonObject.put("yw_user_id", idCardStr);
					json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
					json = URLEncoder.encode(json, "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}

				String result = CallAPIUtil.ObtainFun(json, Common.idCardUrl);

				if (!result.isEmpty()){
					JSONObject paramJson = JSON.parseObject(result);
					String resultFlag = paramJson.getString("result_flag");
					String resultUserName = paramJson.getString("yw_user_name");
					String resultUserPhone = paramJson.getString("yw_user_phone");
					String resultUserSupplier = paramJson.getString("user_supplier_name");
					String resultUserImageUrl = paramJson.getString("yw_qrcode");
					JSONArray resultBrandList = paramJson.getJSONArray("brand_list");
					if ("1".equals(resultFlag)) {
						idCardName.setText(resultUserName);
						idCardPhone.setText(resultUserPhone);
						idCardSupplier.setText(resultUserSupplier);
						//得到可用的图片
						Bitmap bitmap = getHttpBitmap(resultUserImageUrl);
						//显示
						idCardImage.setImageBitmap(bitmap);
//						brand1.setText(resultBrandList.get(0).toString());
					}
				}
			}
		});
		t1.start();
		t1.join();
	}


	/**
	 * 获取网落图片资源
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url){
		URL myFileURL;
		Bitmap bitmap=null;
		try{
			myFileURL = new URL(url);
			//获得连接
			HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
			//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			//连接设置获得数据流
			conn.setDoInput(true);
			//不使用缓存
			conn.setUseCaches(false);
			//这句可有可无，没有影响
			//conn.connect();
			//得到数据流
			InputStream is = conn.getInputStream();
			//解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			//关闭数据流
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bitmap;
	}

}
