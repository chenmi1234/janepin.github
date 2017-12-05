package com.lianpos.devfoucs.listviewlinkage.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lianpos.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本功能：左侧Adapter
 * 创建：wangshuai
 * 创建时间：17/11/22
 */
public class LeftListAdapter extends BaseAdapter {
    private List<String> leftStr;
    List<Boolean> flagArray;
    private Context context;

    public LeftListAdapter(Context context, List<String> leftStr, List<Boolean> flagArray) {
        this.leftStr = leftStr;
        this.context = context;
        this.flagArray = flagArray;
    }

    @Override
    public int getCount() {
        return leftStr.size();
    }

    @Override
    public Object getItem(int arg0) {
        return leftStr.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.left_list_item, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
            holder.left_list_item_number = (TextView) arg1.findViewById(R.id.left_list_item_number);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        holder.updataView(arg0);
        return arg1;
    }

    private class Holder {
        private TextView left_list_item;
        private TextView left_list_item_number;

        public void updataView(final int position) {
            left_list_item.setText(leftStr.get(position).toString());
            left_list_item_number.setText("4");
            if (flagArray.get(position)) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }
}
