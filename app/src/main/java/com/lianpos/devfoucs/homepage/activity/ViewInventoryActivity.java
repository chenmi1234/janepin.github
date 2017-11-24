package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.view.SwipeListLayout;
import com.lianpos.firebase.BaseActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查看库存
 *
 * Created by wangshuai on 2017/11/24
 */
public class ViewInventoryActivity extends BaseActivity {

    private ImageView inventory_back;
    private List<String> list = new ArrayList<String>(15);
    private Set<SwipeListLayout> sets = new HashSet();
    private TextView billing_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        ListView lv_inventory = (ListView) findViewById(R.id.lv_inventory);
        initList();
        lv_inventory.setAdapter(new ListAdapter());
        lv_inventory.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (sets.size() > 0) {
                            for (SwipeListLayout s : sets) {
                                s.setStatus(SwipeListLayout.Status.Close, true);
                                sets.remove(s);
                            }
                        }
                        break;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
        inventory_back = (ImageView) findViewById(R.id.inventory_back);
        inventory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initList() {
        list.add("可乐");
        list.add("怡宝");
        list.add("虾仁");
        list.add("面包");
        list.add("茶叶");
        list.add("果子");
        list.add("农夫山泉");
        list.add("饼干");
        list.add("蔡子业");
        list.add("三只松鼠");
        list.add("良品铺子");
        list.add("胶带");
        list.add("攻防");
        list.add("雪糕");
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

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int arg0, View view, ViewGroup arg2) {
            if (view == null) {
                view = LayoutInflater.from(ViewInventoryActivity.this).inflate(
                        R.layout.inventory_list_item, null);
            }
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(list.get(arg0));
            return view;
        }

    }

}