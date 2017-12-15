package com.lianpos.devfoucs.linkman.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.contacts.adapter.CityAdapter;
import com.lianpos.devfoucs.contacts.decoration.DividerItemDecoration;
import com.lianpos.devfoucs.contacts.model.CityBean;
import com.lianpos.devfoucs.homepage.activity.IWantBillingActivity;
import com.lianpos.devfoucs.homepage.activity.IWantInventoryActivity;
import com.lianpos.devfoucs.homepage.activity.ViewInventoryActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 介绍：开单盘点联系人
 * 头部不是HeaderView 因为头部也需要快速导航，"↑"
 * 作者：wangshuai
 * 时间： 2017/11/7.
 */

public class LinkManActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private SwipeDelMenuAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();
    private ImageView addFriend;
    private ImageView linkman_back;
    private SuspensionDecoration mDecoration;

    List<String> userNameData = new ArrayList<String>();
    List<String> phoneData = new ArrayList<String>();
    List<String> shopNameData = new ArrayList<String>();
    List<String> relationIDData = new ArrayList<String>();
    List<String> userIdData = new ArrayList<String>();
    JSONArray resultSpList = null;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkman);

//        realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
//        realm.commitTransaction();
//        String ywUserId = "";
//        for (JanePinBean guest : guests) {
//            ywUserId = guest.ywUserId;
//        }

        // 从本地缓存中获取城市信息
        SharedPreferences sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE);
        String ywUserId = sharedPreferences.getString("result_id", "");

        try {
            runContacts(ywUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(LinkManActivity.this, DividerItemDecoration.Companion.getVERTICAL_LIST()));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //加载数据
        initDatas(userNameData, phoneData, shopNameData);

        addFriend = (ImageView) findViewById(R.id.begin_menu);
        addFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinkAddFriendPop popWindow = new LinkAddFriendPop(LinkManActivity.this);
                popWindow.showPopupWindow(findViewById(R.id.begin_menu));
            }
        });

        linkman_back = (ImageView) findViewById(R.id.linkman_back);
        linkman_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 和CityAdapter 一模一样，只是修改了 Item的布局
     * Created by wangshuai .
     * Date: 17/11/1
     */

    private class SwipeDelMenuAdapter extends CityAdapter {

        public SwipeDelMenuAdapter(LinkManActivity mContext, List<CityBean> mDatas) {
            super(mContext, mDatas);
        }

        @Override
        public SwipeDelMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getMInflater().inflate(R.layout.item_city_link, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            super.onBindViewHolder(holder, position);
            final TextView callText = (TextView) holder.itemView.findViewById(R.id.tvPhone);
            final LinearLayout content = (LinearLayout) holder.itemView.findViewById(R.id.content);

            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
            realm.commitTransaction();
            String billingInventory = "";
            for (JanePinBean guest : guests) {
                billingInventory = guest.BillingInventoryCode;
            }
            final String finalBillingInventory = billingInventory;
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalBillingInventory.equals("0")){
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class);
                        janePinBean.shUserId = userIdData.get(position);
                        janePinBean.BillingShopNameShow = shopNameData.get(position);
                        janePinBean.BillingShopPhoneShow = phoneData.get(position);
                        realm.commitTransaction();
                        Intent intent = new Intent();
                        intent.setClass(LinkManActivity.this, IWantBillingActivity.class);
                        startActivity(intent);
                    }else if (finalBillingInventory.equals("1")){
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class);
                        janePinBean.shUserId = userIdData.get(position);
                        janePinBean.InquiryShopNameShow = shopNameData.get(position);
                        janePinBean.InquiryShopPhoneShow = phoneData.get(position);
                        realm.commitTransaction();
                        Intent intent = new Intent();
                        intent.setClass(LinkManActivity.this, IWantInventoryActivity.class);
                        startActivity(intent);
                    }else{
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class);
                        janePinBean.shUserId = userIdData.get(position);
                        realm.commitTransaction();

                        Intent intent = new Intent();
                        intent.setClass(LinkManActivity.this, ViewInventoryActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
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
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

    /**
     * 组织数据源
     *
     * @param name
     * @return
     */
    private void initDatas(final List<String> name, final List<String> phone, final List<String> shop) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
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

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String inventorySccess = "";
        for (JanePinBean guest : guests) {
            inventorySccess = guest.InventorySuccess;
        }
        if ("1".equals(inventorySccess)){
            finish();
        }
    }

}
