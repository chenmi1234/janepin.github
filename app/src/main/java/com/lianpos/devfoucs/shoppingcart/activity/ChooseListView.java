package com.lianpos.devfoucs.shoppingcart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianpos.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品选择listview
 * Created by wangshuai on 2017/11/9 0009.
 */

public class ChooseListView extends Activity {

    private ListView listView;
    private TextView choose_title;
    String danwei = null;
    String guige = null;
    String pinpai = null;
    private ImageView choose_back;
    private RelativeLayout addChooseItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_listview);
        Intent intent = getIntent();
        danwei = intent.getStringExtra("danwei");
        guige = intent.getStringExtra("guige");
        pinpai = intent.getStringExtra("pinpai");
        listView = (ListView) findViewById(R.id.choose_list);
        choose_title = (TextView) findViewById(R.id.choose_title);
        choose_back = (ImageView) findViewById(R.id.choose_back);
        addChooseItem = (RelativeLayout) findViewById(R.id.addChooseItem);

        if (danwei != null) {
            if (danwei.equals("1")) {
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
                choose_title.setText("选择基本单位");
            }
        } else if (guige != null) {
            if (guige.equals("2")) {
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getGuigeData()));
                choose_title.setText("选择规格");
            }
        } else if (pinpai != null) {
            if (pinpai.equals("3")) {
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getPinpaiData()));
                choose_title.setText("选择品牌");
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chooseStr = (String) ((TextView) view).getText();
                choose_title.setText(chooseStr);
                finish();
            }
        });

        choose_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addChooseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(ChooseListView.this);//提示框
                final View view = factory.inflate(R.layout.dialog_choose_add_layout, null);//这里必须是final的
                final EditText edit=(EditText)view.findViewById(R.id.editText);//获得输入框对象

                new AlertDialog.Builder(ChooseListView.this)
                        .setTitle("请填写")//提示框标题
                        .setView(view)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //事件
                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });
    }


    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        data.add("瓶");
        data.add("箱");
        data.add("件");
        data.add("袋");
        return data;
    }

    private List<String> getGuigeData() {

        List<String> data = new ArrayList<String>();
        data.add("500ml");
        data.add("600ml");
        data.add("1000ml");
        data.add("1500ml");
        data.add("2000ml");
        data.add("3500ml");
        return data;
    }

    private List<String> getPinpaiData() {

        List<String> data = new ArrayList<String>();
        data.add("哇哈哈");
        data.add("农夫山泉");
        data.add("康师傅");
        data.add("雀巢咖啡");
        data.add("百事可乐");
        data.add("可口可乐");
        data.add("天涯");
        return data;
    }
}