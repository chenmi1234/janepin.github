package com.lianpos.devfoucs.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lianpos.activity.R;

import java.util.List;

/**
 * 询价单列表适配器
 * Created by wangshuai on 2017/11/10 0010.
 */

public class InquirySheetListAdapter extends BaseAdapter implements ListAdapter {
    private List<String> data;
    private int layout;
    private Context context;
    private TextView tv = null;

    public InquirySheetListAdapter(List<String> data, int layout, Context context) {
        this.data = data;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, null);
            tv = (TextView) view.findViewById(R.id.shopName);

            view.setTag(new ObjectClass(tv));

        } else {
            ObjectClass objectclass = (ObjectClass) view.getTag();
            tv = objectclass.text;

        }
        tv.setText(data.get(arg0));
        return view;
    }

    private final class ObjectClass {

        TextView text = null;

        public ObjectClass(TextView text) {
            this.text = text;
        }
    }
}