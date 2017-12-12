package com.lianpos.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.contacts.adapter.CityAdapter;
import com.lianpos.devfoucs.contacts.decoration.DividerItemDecoration;
import com.lianpos.devfoucs.contacts.model.CityBean;
import com.lianpos.devfoucs.contacts.ui.AddFriendPop;
import com.lianpos.devfoucs.view.TwoButtonWarningDialog;
import com.lianpos.entity.JanePinBean;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;
import com.lianpos.util.WeiboDialogUtils;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

/**
 * 联系人
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class DynamicFragment extends Fragment {
    private static final String TAG = "zxt";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private SwipeDelMenuAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();
    private ImageView addFriend;
    private SuspensionDecoration mDecoration;
    private TextView title;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    // 两个按钮的dialog
    private TwoButtonWarningDialog twoButtonDialog;
    List<String> userNameData = new ArrayList<String>();
    List<String> phoneData = new ArrayList<String>();
    List<String> shopNameData = new ArrayList<String>();
    List<String> relationIDData = new ArrayList<String>();
    List<String> userIdData = new ArrayList<String>();
    JSONArray resultSpList = null;
    private Dialog mWeiboDialog;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    private Realm realm = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dynamic, null);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String ywUserId = "";
        for (JanePinBean guest : guests) {
            ywUserId = guest.ywUserId;
        }
        try {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
            runContacts(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mRv = (RecyclerView) rootView.findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        addFriend = (ImageView) rootView.findViewById(R.id.begin_menu);
        addFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                realm.commitTransaction();
                String ywUserId = "";
                for (JanePinBean guest : guests) {
                    ywUserId = guest.ywUserId;
                }

                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.ywUserId = ywUserId;
                realm.commitTransaction();
                AddFriendPop popWindow = new AddFriendPop(getActivity());
                popWindow.showPopupWindow(rootView.findViewById(R.id.begin_menu));
            }
        });
        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.Companion.getVERTICAL_LIST()));

        mAdapter.setOnLongItemClickListener(new CityAdapter.OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, final int position) {
                twoButtonDialog = new TwoButtonWarningDialog(getActivity());
                twoButtonDialog.setYesOnclickListener(new TwoButtonWarningDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                        try {
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
                            runDelContacts(relationIDData.get(position),position);
                            mAdapter.notifyDataSetChanged();
                            twoButtonDialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                twoButtonDialog.setNoOnclickListener(new TwoButtonWarningDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.show();

            }
        });

        //使用indexBar
        mTvSideBarHint = (TextView) rootView.findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) rootView.findViewById(R.id.indexBar);//IndexBar

        //加载数据
        initDatas(userNameData, phoneData, shopNameData);

        title = (TextView) rootView.findViewById(R.id.title);
        title.setText("联系人");

        return rootView;
    }

    /**
     * 组织数据源
     *
     * @param name
     * @return
     */
    private void initDatas(final List<String> name, final List<String> phone, final List<String> shop) {
        //延迟两秒 模拟加载数据中....
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                if (StringUtil.isNotNull(resultSpList)) {
                    for (int i = 0; i < resultSpList.size(); i++) {
                        CityBean cityBean = new CityBean();
                        cityBean.setCity(name.get(i));//设置名称
                        cityBean.setPhone(phone.get(i));//设置电话
                        cityBean.setShopName(shop.get(i));//设置商铺
                        mDatas.add(cityBean);
                    }
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                        .setNeedRealIndex(true)//设置需要真实的索引
                        .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                        .setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
            }
        }, 1);
    }


    /**
     * 获取联系人数据
     * post请求后台
     */
    private void runContacts(final String idCardStr) throws InterruptedException {
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

                String result = CallAPIUtil.ObtainFun(json, Common.userListByYwUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    resultSpList = paramJson.getJSONArray("sh_list");
                    if ("1".equals(resultFlag)) {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        if (StringUtil.isNotNull(resultSpList)) {
                            for (int i = 0; i < resultSpList.size(); i++) {
                                JSONObject info = resultSpList.getJSONObject(i);
                                String userName = info.getString("username");
                                String phone = info.getString("phone");
                                String name = info.getString("name");
                                String relation = info.getString("relation_id");
                                String userID = info.getString("user_id");
                                if (StringUtil.isNotNull(userName)) {
                                    userNameData.add(userName);
                                    phoneData.add(phone);
                                    shopNameData.add(name);
                                    relationIDData.add(relation);
                                    userIdData.add(userID);
                                }
                            }
                        }
                    }else if ("2".equals(resultFlag)){
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }


    /**
     * 删除联系人数据
     * post请求后台
     */
    private void runDelContacts(final String idCardStr,final int position) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("relation_id", idCardStr);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.userDelYwUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    if ("1".equals(resultFlag)) {
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                        mDatas.remove(position);
                    }else if ("2".equals(resultFlag)){
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

    /**
     * 和CityAdapter 一模一样，只是修改了 Item的布局
     * Created by wangshuai .
     * Date: 17/11/1
     */

    private class SwipeDelMenuAdapter extends CityAdapter {

        public SwipeDelMenuAdapter(DynamicFragment mContext, List<CityBean> mDatas) {
            super(mContext.getContext(), mDatas);
        }

        @Override
        public SwipeDelMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getMInflater().inflate(R.layout.item_city_swipe, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            final TextView callText = (TextView) holder.itemView.findViewById(R.id.tvPhone);
            holder.itemView.findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone(callText.getText().toString());
                }
            });
        }
    }

    public void callPhone(String str) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                title.setText("解码结果： \n" + content);

            }
        }
    }

}
