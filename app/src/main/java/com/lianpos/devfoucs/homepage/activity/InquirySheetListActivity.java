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
import com.lianpos.devfoucs.login.activity.LoginActivity;
import com.lianpos.devfoucs.shoppingcart.activity.ChooseListView;
import com.lianpos.devfoucs.shoppingcart.view.PinnedHeaderListView;
import com.lianpos.firebase.BaseActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    String shopNameStr,shopPhoneStr,inquiryNumber;
    private TextView inquiry_shopName;
    private TextView inquiry_shopPhone;
    private TextView inquiry_shopNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);
        Intent intent = getIntent();
        shopNameStr = intent.getStringExtra("shopName");
        shopPhoneStr = intent.getStringExtra("shopPhone");
        inquiryNumber = intent.getStringExtra("inquiryNumber");
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initID();
        initClick();
        funRealization();
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
    }

    /**
     * 点击事件
     */
    private void initClick() {
        inquiry_sheet_list_back.setOnClickListener(this);
        inquiry_sheet_cancel.setOnClickListener(this);
        confirm_send_layout.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        getData();
        inquiry_listview.setAdapter(new InquirySheetListAdapter(list, R.layout.activity_inquiry_listview_item,
                InquirySheetListActivity.this));
        shopNumber.setText(list.size() + "");
        inquiry_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(InquirySheetListActivity.this, InquiryEditerActyvity.class);
                startActivity(intent);
            }
        });

        inquiry_shopName.setText(shopNameStr);
        inquiry_shopPhone.setText(shopPhoneStr);
        inquiry_shopNumber.setText(inquiryNumber);
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

    private void getData() {
        list.add("可口可乐");
        list.add("哇哈哈");
        list.add("康师傅");
        list.add("养乐多");
        list.add("优益C");
        list.add("雀巢咖啡");
        list.add("好多鱼饼干");
        list.add("德芙巧克力");
        list.add("旺旺");
        list.add("三只松鼠杏仁");
        list.add("香蕉雪糕");
        list.add("哈尔滨啤酒");
    }
}
