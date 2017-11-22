package com.lianpos.devfoucs.listviewlinkage.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.listviewlinkage.View.AddCommodityDialog;
import com.lianpos.entity.JanePinBean;

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
    // 两个按钮的dialog
    private AddCommodityDialog addCommodityDialog;
    private Realm realm = null;

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
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.right_list_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }

        ButterKnife.bind(layout);
        realm = Realm.getDefaultInstance();
        ((TextView) layout.findViewById(R.id.textItem)).setText(rightStr[section][position]);
        final LinearLayout finalLayout = layout;
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(mContext, rightStr[section][position], Toast.LENGTH_SHORT).show();
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.AddShopDialogTitle = rightStr[section][position];
                realm.commitTransaction();
                addCommodityDialog = new AddCommodityDialog(mContext);
                addCommodityDialog.setYesOnclickListener(new AddCommodityDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                    }
                });
                addCommodityDialog.setNoOnclickListener(new AddCommodityDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
                        realm.commitTransaction();
                        String addNumber = "";
                        String addPrice = "";
                        for (JanePinBean guest : guests) {
                            addNumber = guest.AddShopDialogNumber;
                            addPrice = guest.AddShopDialogPrice;
                        }
                        ((TextView) finalLayout.findViewById(R.id.shoppingNum)).setText(addNumber);
                        ((TextView) finalLayout.findViewById(R.id.prise)).setText(addPrice);
                        addCommodityDialog.dismiss();
                    }
                });
                addCommodityDialog.show();
            }
        });
        return layout;
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

}
