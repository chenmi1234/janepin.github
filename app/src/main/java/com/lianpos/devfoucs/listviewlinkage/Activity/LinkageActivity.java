package com.lianpos.devfoucs.listviewlinkage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.listviewlinkage.Adapter.LeftListAdapter;
import com.lianpos.devfoucs.listviewlinkage.Adapter.MainSectionedAdapter;
import com.lianpos.devfoucs.listviewlinkage.View.AddCommodityDialog;
import com.lianpos.devfoucs.listviewlinkage.View.PinnedHeaderListView;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.scancodeidentify.zbar.ZbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LinkageActivity extends AppCompatActivity {

    @Bind(R.id.left_listview)
    ListView leftListview;
    @Bind(R.id.pinnedListView)
    PinnedHeaderListView pinnedListView;
    private boolean isScroll = true;
    private LeftListAdapter adapter;
    //返回键
    private ImageView merchant_back;
    //新增商品
    private TextView addShopping;
    private ImageView scanning_shop_tiaoxing;

    private String[] leftStr = new String[]{"面食类", "盖饭", "寿司", "烧烤", "酒水", "凉菜", "小吃", "粥", "休闲"};
    private boolean[] flagArray = {true, false, false, false, false, false, false, false, false};
    private String[][] rightStr = new String[][]{{"热干面", "臊子面", "烩面"},
            {"番茄鸡蛋", "红烧排骨", "农家小炒肉"},
            {"芝士", "丑小丫", "金枪鱼"}, {"羊肉串", "烤鸡翅", "烤羊排"}, {"长城干红", "燕京鲜啤", "青岛鲜啤"},
            {"拌粉丝", "大拌菜", "菠菜花生"}, {"小食组", "紫薯"},
            {"小米粥", "大米粥", "南瓜粥", "玉米粥", "紫米粥"}, {"儿童小汽车", "悠悠球", "熊大", " 熊二", "光头强"}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage);
        ButterKnife.bind(this);
        pinnedListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        final MainSectionedAdapter sectionedAdapter = new MainSectionedAdapter(this, leftStr, rightStr);
        pinnedListView.setAdapter(sectionedAdapter);
        adapter = new LeftListAdapter(this, leftStr, flagArray);
        leftListview.setAdapter(adapter);
        leftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                isScroll = false;

                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);

            }

        });

        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            leftListview.setSelection(0);
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == leftListview.getLastVisiblePosition()) {
//                            z = z + 3;
                            leftListview.setSelection(z);
                        }
                        if (x == leftListview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            leftListview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });

        merchant_back = (ImageView) findViewById(R.id.merchant_back);
        merchant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addShopping = (TextView) findViewById(R.id.addShopping);
        addShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LinkageActivity.this,IncreaseCommodityActivity.class);
                startActivity(intent);
            }
        });

        scanning_shop_tiaoxing = (ImageView)findViewById(R.id.scanning_shop_tiaoxing);
        scanning_shop_tiaoxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LinkageActivity.this, ZbarActivity.class);
                intent.putExtra("addshop", "addshop");
                startActivity(intent);
            }
        });

        pinnedListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
