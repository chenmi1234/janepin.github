package com.lianpos.devfoucs.linkman.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.contacts.model.CityBean;
import com.lianpos.devfoucs.homepage.activity.IWantBillingActivity;

import java.util.List;

/**
 * 开单盘点adapter
 * Created by wangshuai on 2017/11/7 0007.
 */

public class LinkManAdapter extends RecyclerView.Adapter<LinkManAdapter.ViewHolder> {
    protected Context mContext;
    protected List<CityBean> mDatas;
    protected LayoutInflater mInflater;

    public LinkManAdapter(Context mContext, List<CityBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<CityBean> getDatas() {
        return mDatas;
    }

    public LinkManAdapter setDatas(List<CityBean> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public LinkManAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinkManAdapter.ViewHolder(mInflater.inflate(R.layout.item_city_link, parent, false));
    }

    @Override
    public void onBindViewHolder(final LinkManAdapter.ViewHolder holder, final int position) {
        final CityBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getCity());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, IWantBillingActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.avatar.setText("18842535353");
        holder.tvSupermarket.setText("十七超市");
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        TextView avatar;
        TextView tvSupermarket;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            tvSupermarket = (TextView) itemView.findViewById(R.id.tvSupermarket);
            avatar = (TextView) itemView.findViewById(R.id.tvPhone);
            content = itemView.findViewById(R.id.content);
        }
    }
}
