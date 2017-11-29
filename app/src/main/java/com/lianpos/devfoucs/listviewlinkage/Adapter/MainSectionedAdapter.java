package com.lianpos.devfoucs.listviewlinkage.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.view.SwipeListLayout;
import com.lianpos.devfoucs.listviewlinkage.View.PinnedHeaderListView;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 基本功能：右侧Adapter
 * 创建：王帅
 * 创建时间：17/11/22
 */
public class MainSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private String[] leftStr;
    private String[][] rightStr;
    private Realm realm = null;
    private Set<SwipeListLayout> sets = new HashSet();

    public MainSectionedAdapter(Context context, String[] leftStr, String[][] rightStr) {
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    @Override
    public Object getItem(int section, int position) {
        return rightStr[section][position];
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.length;
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr[section].length;
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.right_list_item, null);
        }
        //获得listview的实例
        final PinnedHeaderListView listView = (PinnedHeaderListView) parent;

        final SwipeListLayout sll_main = (SwipeListLayout) convertView
                .findViewById(R.id.sll_main);
        TextView tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
        sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                sll_main));

        final List list = Arrays.asList(rightStr);
        final List arrayList = new ArrayList(list);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sll_main.setStatus(SwipeListLayout.Status.Close, true);
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        ButterKnife.bind(convertView);
        realm = Realm.getDefaultInstance();
        ((TextView) convertView.findViewById(R.id.textItem)).setText(rightStr[section][position]);
//        final LinearLayout finalLayout = layout;

        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String billingInventory = "";
        for (JanePinBean guest : guests) {
            billingInventory = guest.BillingInventoryCode;
        }
        if (billingInventory.equals("1")){
            ((LinearLayout) convertView.findViewById(R.id.number_right_item)).setVisibility(View.GONE);
            ((LinearLayout) convertView.findViewById(R.id.prise_right_item)).setVisibility(View.GONE);
        }else{
            ((LinearLayout) convertView.findViewById(R.id.number_right_item)).setVisibility(View.VISIBLE);
            ((LinearLayout) convertView.findViewById(R.id.prise_right_item)).setVisibility(View.VISIBLE);
        }

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(mContext,IncreaseCommodityActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftStr[section]);
        return layout;
    }

    class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

        private SwipeListLayout slipListLayout;

        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }

}
