package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.adapter.InquiryFruitAdapter;
import com.lianpos.devfoucs.homepage.model.InquryFruit;
import com.lianpos.firebase.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 询价单
 *
 * @author 询价
 *         Created by wangshuai on 2017/11/2
 */
public class InquirySheetActivity extends BaseActivity implements View.OnClickListener {
    // 无询价单
    private ImageView inquiry_sheet_back;
    // 询价单list
    private ListView listView;
    private InquiryFruitAdapter mAdapter;
    private List<InquryFruit> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_sheet);
        init();
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new InquiryFruitAdapter(this, list));

    }

    private void init() {
        // 初始化id
        initID();
        // 初始化点击事件
        initOnClick();
    }

    // 初始化id
    private void initID() {
        inquiry_sheet_back = (ImageView) findViewById(R.id.inquiry_sheet_back);
        listView = (ListView) findViewById(R.id.list_view);
    }

    // 初始化点击事件
    private void initOnClick() {
        inquiry_sheet_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inquiry_sheet_back:
                finish();
                break;
        }
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("title", "达达超市"+i);
            map.put("info", "1884444333"+i);
            list.add(map);
        }
        return list;
    }
}
