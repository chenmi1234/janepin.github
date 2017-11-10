package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.adapter.InquirySheetListAdapter;
import com.lianpos.devfoucs.shoppingcart.view.PinnedHeaderListView;
import com.lianpos.firebase.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);
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
    }

    /**
     * 点击事件
     */
    private void initClick() {
        inquiry_sheet_list_back.setOnClickListener(this);
        inquiry_sheet_cancel.setOnClickListener(this);
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

            }
        });
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
        }
    }

    private void getData() {
        list.add("1");
        list.add("12");
        list.add("郑明亮");
        list.add("1234");
        list.add("12345");
        list.add("123456");
        list.add("1");
        list.add("12");
        list.add("郑明亮");
        list.add("1234");
        list.add("12345");
        list.add("123456");
    }
}
