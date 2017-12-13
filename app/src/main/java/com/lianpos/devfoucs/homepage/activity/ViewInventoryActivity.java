package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.devfoucs.contacts.adapter.InvertoryAdapter;
import com.lianpos.devfoucs.contacts.decoration.DividerItemDecoration;
import com.lianpos.devfoucs.contacts.model.InventoryBean;
import com.lianpos.devfoucs.homepage.view.SwipeListLayout;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 查看库存
 * <p>
 * Created by wangshuai on 2017/11/24
 */
public class ViewInventoryActivity extends BaseActivity {

    private ImageView inventory_back;
    private List<String> list = new ArrayList<String>(15);
    private Set<SwipeListLayout> sets = new HashSet();
    private TextView billing_total;
    JSONArray resultKcList = null;
    private SwipeDelMenuAdapter mAdapter;
    private RecyclerView mRv;
    List<String> spNameData = new ArrayList<String>();
    List<String> barcodeData = new ArrayList<String>();
    List<String> sellCountData = new ArrayList<String>();
    List<String> xsUnitData = new ArrayList<String>();
    List<String> spInventoryCountData = new ArrayList<String>();
    List<String> spUnitCountData = new ArrayList<String>();
    private Realm realm = null;
    private List<InventoryBean> mDatas = new ArrayList<>();
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        ListView lv_inventory = (ListView) findViewById(R.id.lv_inventory);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String shUserId = "";
        for (JanePinBean guest : guests) {
            shUserId = guest.shUserId;
        }
        try {
            runInventory(shUserId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(ViewInventoryActivity.this, DividerItemDecoration.Companion.getVERTICAL_LIST()));

        //加载数据
        initDatas(spNameData, barcodeData, sellCountData, xsUnitData, spInventoryCountData, spUnitCountData);

        inventory_back = (ImageView) findViewById(R.id.inventory_back);
        inventory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取联系人数据
     * post请求后台
     */
    private void runInventory(final String idCardStr) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("user_id", idCardStr);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.invertoryUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    resultKcList = paramJson.getJSONArray("kucun_list");
                    if ("1".equals(resultFlag)) {
                        if (StringUtil.isNotNull(resultKcList)) {
                            for (int i = 0; i < resultKcList.size(); i++) {
                                JSONObject info = resultKcList.getJSONObject(i);
                                String sp_name = info.getString("sp_name");
                                String barcode = info.getString("barcode");
                                String sell_count = info.getString("sell_count");
                                String xs_unit = info.getString("xs_unit");
                                String sp_inventory_count = info.getString("sp_inventory_count");
                                String sp_unit = info.getString("sp_unit");
                                if (StringUtil.isNotNull(sp_name)) {
                                    spNameData.add(sp_name);
                                    barcodeData.add(barcode);
                                    sellCountData.add(sell_count);
                                    xsUnitData.add(xs_unit);
                                    spInventoryCountData.add(sp_inventory_count);
                                    spUnitCountData.add(sp_unit);
                                }
                            }
                        }
                    } else if ("2".equals(resultFlag)) {
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }


    private void initDatas(final List<String> spNameData, final List<String> barcodeData, final List<String> sellCountData, final List<String> xsUnitData, final List<String> spInventoryCountData, final List<String> spUnitCountData) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                if (StringUtil.isNotNull(resultKcList)) {
                    for (int i = 0; i < resultKcList.size(); i++) {
                        InventoryBean inventoryBean = new InventoryBean();
                        inventoryBean.setInName(spNameData.get(i));//名称
                        inventoryBean.setTiaoma(barcodeData.get(i));//条码
                        inventoryBean.setInNumber(sellCountData.get(i));//销量
                        inventoryBean.setUnit(xsUnitData.get(i));//单位
                        inventoryBean.setInventory(spInventoryCountData.get(i));//库存
                        inventoryBean.setInventoryunit(spUnitCountData.get(i));//库存单位
                        mDatas.add(inventoryBean);
                    }
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();
            }
        }, 1);
    }

    /**
     * 和CityAdapter 一模一样，只是修改了 Item的布局
     * Created by wangshuai .
     * Date: 17/11/1
     */

    private class SwipeDelMenuAdapter extends InvertoryAdapter {

        public SwipeDelMenuAdapter(ViewInventoryActivity mContext, List<InventoryBean> mDatas) {
            super(mContext, mDatas);
        }

        @Override
        public SwipeDelMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getMInflater().inflate(R.layout.inventory_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            final TextView callText = (TextView) holder.itemView.findViewById(R.id.tvPhone);

        }
    }

}