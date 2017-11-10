package com.lianpos.devfoucs.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.activity.IWantBillingActivity;
import com.lianpos.devfoucs.homepage.activity.InquirySheetListActivity;

import java.util.List;
import java.util.Map;

/**
 * 询价单适配器
 * Created by wangshuai on 2017/11/2 0002.
 */

public class InquiryFruitAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public InquiryFruitAdapter(Context context,List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Zujian{
        public TextView title;
        public TextView info;
        public RelativeLayout inquiryContent;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian=null;
        if(convertView==null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.activity_inquiry_sheet_item, null);
            zujian.title=(TextView)convertView.findViewById(R.id.shopName);
            zujian.info=(TextView)convertView.findViewById(R.id.phoneNumber);
            zujian.inquiryContent = (RelativeLayout)convertView.findViewById(R.id.inquiryContent);
            convertView.setTag(zujian);
        }else{
            zujian=(Zujian)convertView.getTag();
        }
        //绑定数据
        zujian.title.setText((String)data.get(position).get("title"));
        zujian.info.setText((String)data.get(position).get("info"));
        zujian.inquiryContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, InquirySheetListActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}