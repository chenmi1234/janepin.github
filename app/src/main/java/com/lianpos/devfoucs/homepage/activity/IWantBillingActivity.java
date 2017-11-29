package com.lianpos.devfoucs.homepage.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.bean.InquirySheetBean;
import com.lianpos.devfoucs.homepage.bean.WantBillingBean;
import com.lianpos.devfoucs.homepage.view.SwipeListLayout;
import com.lianpos.devfoucs.listviewlinkage.View.AddCommodityDialog;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.scancodeidentify.zbar.ZbarActivity;

import org.w3c.dom.Text;

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
//    private List<String> list = new ArrayList<String>(15);
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
    private TextView billAddShopBtn;
    private List<WantBillingBean> mDatas;
    private WantBillingBean bean;
    private ListAdapter listAdapter;
    private TextView billing_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_billing);
        serch_shop = (EditText) findViewById(R.id.serch_shop);
        make_money_back = (ImageView) findViewById(R.id.make_money_back);
        billing_message = (TextView) findViewById(R.id.billing_message);
        make_money_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListView lv_main = (ListView) findViewById(R.id.lv_main);
        initList();
        listAdapter = new ListAdapter();
        lv_main.setAdapter(listAdapter);
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

        billAddShopBtn = (TextView) findViewById(R.id.billAddShopBtn);
        billAddShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IWantBillingActivity.this,IncreaseCommodityActivity.class);
                startActivity(intent);
            }
        });

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(IWantBillingActivity.this,ShopInformation.class);
                startActivity(intent);
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
            billing_Inventory_title.setText("盘点单");
            see_stock.setVisibility(View.GONE);
        } else {
            billing_Inventory_title.setText("销售单");
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
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.NewlyAddedDistinguish = "3";
                realm.commitTransaction();
                Intent intent = new Intent();
                intent.setClass(IWantBillingActivity.this, ZbarActivity.class);
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
                    String addTiaoma = "";
                    String addNumber = "";
                    String addPrice = "";
                    String addUnit = "";
                    String addJyPrice = "";
                    for (JanePinBean guest : guests) {
                        addNumber = guest.AddShopDialogNumber;
                        addPrice = guest.AddShopDialogPrice;
                        addUnit = guest.AddShopDialogUnit;
                        addTiaoma = guest.InquiryShopNumber;
                        addJyPrice = guest.AddShopDialogJyPrice;
                    }

                    Double aaa = 0.00;
                    Double bbb = 0.00;
                    aaa = Double.valueOf(addNumber).doubleValue();
                    bbb = Double.valueOf(addPrice).doubleValue();
                    String addJine = Double.toString(aaa * bbb);

                    bean = new WantBillingBean("商品名称", addTiaoma, addNumber,addUnit, addPrice, addJyPrice, addJine);
                    mDatas.add(bean);
                    billing_message.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    addCommodityDialog.dismiss();
                }
            });
            addCommodityDialog.show();
        }
    }

    private void initList() {

        mDatas = new ArrayList<WantBillingBean>();

        //将数据装到集合中去
//        bean = new WantBillingBean("可口可乐", "6911112223000", "1","瓶", "23.22", "40.00","200.00");
//        mDatas.add(bean);
//
//        bean = new WantBillingBean("三只松鼠杏仁", "6911112223999", "2","袋", "23.88", "30.00","200.00");
//        mDatas.add(bean);
//
//        bean = new WantBillingBean("哈尔滨啤酒", "6911112223222", "3","箱", "1.22", "20.00","200.00");
//        mDatas.add(bean);
//
//        bean = new WantBillingBean("香蕉雪糕", "6911112223222", "4","件", "3.00", "10.00","200.00");
//        mDatas.add(bean);

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
            return mDatas.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mDatas.get(arg0);
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
            TextView tv_number = (TextView) view.findViewById(R.id.billing_tiaoma);
            TextView tv_num_text = (TextView) view.findViewById(R.id.billingNumberText);
            TextView tv_unit = (TextView) view.findViewById(R.id.billingUnit);
            TextView tv_pifa_price = (TextView) view.findViewById(R.id.billing_pifa_price);
            TextView tv_jianyi_price = (TextView) view.findViewById(R.id.billing_jianyi_price);
            TextView tv_total = (TextView) view.findViewById(R.id.billing_total);
            WantBillingBean bean = mDatas.get(arg0);
            tv_name.setText(bean.getItemShopName());
            tv_number.setText(bean.getShopTiaoma());
            tv_num_text.setText(bean.getShopNumber());
            tv_unit.setText(bean.getShopUnit());
            tv_pifa_price.setText(bean.getShopPifajia());
            tv_jianyi_price.setText(bean.getShopPrice());
            tv_total.setText(bean.getShopTotal());
            final SwipeListLayout sll_main = (SwipeListLayout) view
                    .findViewById(R.id.sll_main);
            TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                    sll_main));
            tv_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    sll_main.setStatus(SwipeListLayout.Status.Close, true);
                    mDatas.remove(arg0);
                    if (mDatas.isEmpty()){
                        billing_message.setVisibility(View.VISIBLE);
                    }
                    notifyDataSetChanged();
                }
            });
            final LinearLayout shopShowItem = (LinearLayout) view.findViewById(R.id.shopShowItem);
            shopShowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(IWantBillingActivity.this,ShopInformation.class);
                    startActivity(intent);
                }
            });
            return view;
        }

    }

}