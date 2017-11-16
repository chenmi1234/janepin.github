package com.lianpos.devfoucs.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.bean.InquirySheetBean;

import java.util.List;

import io.realm.Realm;

/**
 * 询价单列表适配器
 * Created by wangshuai on 2017/11/16 0010.
 */

public class InquirySheetListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<InquirySheetBean> mDatas;
    private Realm realm = null;
    private Context mContext;


    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public InquirySheetListAdapter(Context context, List<InquirySheetBean> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    //返回数据集的长度
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_inquiry_listview_item, parent, false); //加载布局
            holder = new ViewHolder();
            holder.inquiry_sheet_item_layout = (LinearLayout) convertView.findViewById(R.id.inquiry_sheet_item_layout);
            holder.itemShopNameTv = (TextView) convertView.findViewById(R.id.itemShopName);
            holder.shopTiaomaTv = (TextView) convertView.findViewById(R.id.shopTiaoma);
            holder.shopDanweiTv = (TextView) convertView.findViewById(R.id.shopDanwei);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        InquirySheetBean bean = mDatas.get(position);
        holder.itemShopNameTv.setText(bean.getItemShopName());
        holder.shopTiaomaTv.setText(bean.getShopTiaoma());
        holder.shopDanweiTv.setText(bean.getShopDanwei());

        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    public class ViewHolder {
        LinearLayout inquiry_sheet_item_layout;
        TextView itemShopNameTv;
        TextView shopTiaomaTv;
        TextView shopDanweiTv;
    }

}