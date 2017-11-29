package com.lianpos.devfoucs.homepage.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.MainActivity;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.adapter.InquirySheetListAdapter;
import com.lianpos.devfoucs.homepage.bean.InquirySheetBean;
import com.lianpos.entity.JanePinBean;
import com.lianpos.firebase.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 询价列表
 * Created by wangshuai on 2017/11/10
 */
public class InquirySheetListActivity extends BaseActivity implements View.OnClickListener {

    //返回
    private ImageView inquiry_sheet_list_back;
    //取消
    private RelativeLayout inquiry_sheet_cancel;
    private ListView inquiry_listview;
    List<String> list = new ArrayList<String>();
    private TextView shopNumber;
    private RelativeLayout confirm_send_layout;
    String shopNameStr, shopPhoneStr, inquiryNumber;
    private TextView inquiry_shopName;
    private TextView inquiry_shopPhone;
    private TextView inquiry_shopNumber;
    private TextView shopTiaoma;
    private Realm realm = null;
    private List<InquirySheetBean> mDatas;
    private InquirySheetListAdapter mAdapter;
    String shopTiaomaStr, itemShopNameStr, shopDanweiStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        init();
        Intent intent = getIntent();
        inquiry_shopName.setText(intent.getStringExtra("shopName"));
        inquiry_shopPhone.setText(intent.getStringExtra("shopPhone"));
//        inquiry_shopNumber.setText(intent.getStringExtra("inquiryNumber"));
    }

    /**
     * 初始化
     */
    private void init() {
        initID();
        initClick();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initID() {
        inquiry_sheet_list_back = (ImageView) findViewById(R.id.inquiry_sheet_list_back);
        inquiry_sheet_cancel = (RelativeLayout) findViewById(R.id.inquiry_sheet_cancel);
        inquiry_listview = (ListView) findViewById(R.id.inquiry_listview);
        shopNumber = (TextView) findViewById(R.id.shopNumber);
        confirm_send_layout = (RelativeLayout) findViewById(R.id.confirm_send_layout);
        inquiry_shopName = (TextView) findViewById(R.id.inquiry_shopName);
        inquiry_shopPhone = (TextView) findViewById(R.id.inquiry_shopPhone);
        inquiry_shopNumber = (TextView) findViewById(R.id.inquiryNumber);
        shopTiaoma = (TextView) findViewById(R.id.shopTiaoma);
    }

    /**
     * 点击事件
     */
    private void initClick() {
        inquiry_sheet_list_back.setOnClickListener(this);
        inquiry_sheet_cancel.setOnClickListener(this);
        confirm_send_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inquiry_sheet_list_back:
                finish();
                break;
            case R.id.inquiry_sheet_cancel:
                finish();
                break;
            case R.id.confirm_send_layout:
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
                        intent1.setClass(InquirySheetListActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        dlg.dismiss();
                        t.cancel();
                    }
                }, 2000);
                break;
        }
    }

    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<InquirySheetBean>();

        //将数据装到集合中去
        InquirySheetBean bean = new InquirySheetBean("可口可乐", "6911112223000", "瓶");
        mDatas.add(bean);

        bean = new InquirySheetBean("三只松鼠杏仁", "6911112223999", "箱");
        mDatas.add(bean);

        bean = new InquirySheetBean("哈尔滨啤酒", "6911112223222", "瓶");
        mDatas.add(bean);

        bean = new InquirySheetBean("香蕉雪糕", "6911112223222", "个");
        mDatas.add(bean);

        //为数据绑定适配器
        mAdapter = new InquirySheetListAdapter(this, mDatas);

        inquiry_listview.setAdapter(mAdapter);

        shopNumber.setText(mDatas.size()+"");

        inquiry_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View convertView, int position, long id) {
                TextView itemShopNameTv = (TextView) convertView.findViewById(R.id.itemShopName);
                TextView shopTiaomaTv = (TextView) convertView.findViewById(R.id.shopTiaoma);
                TextView shopDanweiTv = (TextView) convertView.findViewById(R.id.shopDanwei);

                shopTiaomaStr = itemShopNameTv.getText().toString();
                itemShopNameStr = shopTiaomaTv.getText().toString();
                shopDanweiStr = shopDanweiTv.getText().toString();

                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.InquiryShopNumber = shopTiaomaStr;
                janePinBean.InquiryShopName = itemShopNameStr;
                janePinBean.InquiryShopUnit = shopDanweiStr;
                realm.commitTransaction();

                Intent intent = new Intent();
                intent.setClass(InquirySheetListActivity.this, InquiryEditerActyvity.class);
                startActivity(intent);
            }
        });
    }
}
