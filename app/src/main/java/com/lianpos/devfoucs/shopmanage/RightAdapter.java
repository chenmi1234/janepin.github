package com.lianpos.devfoucs.shopmanage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 2017/12/6.
 */
public class RightAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private View view;
    private Holder holder;
    private Set<SwipeListLayout> sets = new HashSet();

    public RightAdapter(Context context, ArrayList<String> leftlist) {
        this.context = context;
        this.list = leftlist;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.right_shop_listview, null);
            holder = new Holder(view);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        holder.tv_name.setText(list.get(i).toString());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   list.set(i,"ni ma bi");
                Toast.makeText(context, "点击了添加按钮", Toast.LENGTH_SHORT).show();
            }
        });

        final SwipeListLayout sll_main = (SwipeListLayout) view
                .findViewById(R.id.sll_main);
        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                sll_main));

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sll_main.setStatus(SwipeListLayout.Status.Close, true);
                list.remove(i);
                notifyDataSetChanged();
            }
        });

        sll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, IncreaseCommodityActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private static class Holder {
        TextView tv_right_number;
        TextView tv_right;
        TextView btn_add;
        TextView tv_name;

        public Holder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_right_number = (TextView) view.findViewById(R.id.jyPrice);
            tv_right = (TextView) view.findViewById(R.id.prise);
            btn_add = (TextView) view.findViewById(R.id.rightTm);
        }

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
//                if (sets.size() > 0) {
//                    for (SwipeListLayout s : sets) {
//                        s.setStatus(SwipeListLayout.Status.Close, true);
//                        sets.remove(s);
//                    }
//                }
                sets.add(slipListLayout);
                notifyDataSetChanged();
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
