package com.lianpos.devfoucs.homepage.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.view.SwipeListLayout;
import com.lianpos.devfoucs.listviewlinkage.Activity.LinkageActivity;
import com.lianpos.devfoucs.listviewlinkage.View.AddCommodityDialog;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.scancodeidentify.zbar.ZbarActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 开单盘点确认
 * <p>
 * Created by wangshuai on 2017/11/06
 */
public class IWantBillingActivity extends BaseActivity {

    private ImageView make_money_back;
    private List<String> list = new ArrayList<String>(15);
    private Set<SwipeListLayout> sets = new HashSet();
    private TextView billing_total;
    private TextView billing_Inventory_title;
    private TextView see_stock;
    private ImageView scanning_shop_tiaoxing;
    private EditText serch_shop;
    private RelativeLayout sureAndSend,cencelRela;
    // 两个按钮的dialog
    private AddCommodityDialog addCommodityDialog;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_billing);
        serch_shop = (EditText) findViewById(R.id.serch_shop);
        ListView lv_main = (ListView) findViewById(R.id.lv_main);
        initList();
        lv_main.setAdapter(new ListAdapter());
        lv_main.setOnScrollListener(new AbsListView.OnScrollListener() {

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
        billing_Inventory_title = (TextView) findViewById(R.id.billing_Inventory_title);
        see_stock = (TextView) findViewById(R.id.see_stock);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String billingInventory = "";
        for (JanePinBean guest : guests) {
            billingInventory = guest.BillingInventoryCode;
        }
        if (billingInventory.equals("1")) {
            billing_Inventory_title.setText("我要盘点");
            see_stock.setVisibility(View.GONE);
        } else {
            billing_Inventory_title.setText("我要开单");
            see_stock.setVisibility(View.VISIBLE);
        }

        see_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IWantBillingActivity.this, ViewInventoryActivity.class);
                startActivity(intent);
            }
        });
        scanning_shop_tiaoxing = (ImageView) findViewById(R.id.scanning_shop_tiaoxing);
        scanning_shop_tiaoxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IWantBillingActivity.this, ZbarActivity.class);
                intent.putExtra("addshop", "addshop");
                startActivity(intent);
            }
        });
        sureAndSend = (RelativeLayout) findViewById(R.id.sureAndSend);
        cencelRela = (RelativeLayout) findViewById(R.id.cencelRela);
        sureAndSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("发送成功！");
                builder.setMessage("2秒后自动关闭！");
                builder.setCancelable(true);
                final AlertDialog dlg = builder.create();
                dlg.show();
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        Intent intent1 = new Intent();
                        intent1.setClass(IWantBillingActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        dlg.dismiss();
                        t.cancel();
                    }
                }, 2000);
            }
        });
        cencelRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String ejectNumber = "0";
        for (JanePinBean guest : guests) {
            ejectNumber = guest.DialogEjectCode;
        }

        if (ejectNumber.equals("1")){
            addCommodityDialog = new AddCommodityDialog(this);
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
                    addCommodityDialog.dismiss();
                }
            });
            addCommodityDialog.show();
        }
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
                view = LayoutInflater.from(IWantBillingActivity.this).inflate(
                        R.layout.slip_list_item, null);
            }
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(list.get(arg0));
            final SwipeListLayout sll_main = (SwipeListLayout) view
                    .findViewById(R.id.sll_main);
            TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                    sll_main));
            tv_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    sll_main.setStatus(SwipeListLayout.Status.Close, true);
                    list.remove(arg0);
                    notifyDataSetChanged();
                }
            });
            return view;
        }

    }

}