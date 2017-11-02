package com.lianpos.devfoucs.homepage.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.model.InquryFruit;
import com.lianpos.firebase.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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
    private List<InquryFruit> inquryList = new ArrayList<InquryFruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_sheet);
        init();
        getData();
        listView.setAdapter(new BaseAdapter() {
            //返回多少条记录
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return inquryList.size();
            }

            //每一个item项，返回一次界面
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                //布局不变，数据变

                //如果缓存为空，我们生成新的布局作为1个item
                if (convertView == null) {
                    LayoutInflater inflater = InquirySheetActivity.this.getLayoutInflater();
                    //因为getView()返回的对象，adapter会自动赋给ListView
                    view = inflater.inflate(R.layout.activity_inquiry_sheet_item, null);
                } else {
                    view = convertView;
                }
                InquryFruit m = inquryList.get(position);
                TextView tv_userName = (TextView) view.findViewById(R.id.shopName);
                tv_userName.setText(m.getName());
                tv_userName.setTextSize(15);

                return view;
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return 0;
            }

        });
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

    private List<InquryFruit> getData() {

        for (int i = 1; i < 10; i++) {
            //添加数据
            InquryFruit m = new InquryFruit();
            m.setName("洋洋超市");
            inquryList.add(m);
        }

        return inquryList;
    }
}
