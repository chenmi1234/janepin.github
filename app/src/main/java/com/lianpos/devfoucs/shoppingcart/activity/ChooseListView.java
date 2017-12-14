package com.lianpos.devfoucs.shoppingcart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lianpos.activity.R;
import com.lianpos.common.Common;
import com.lianpos.entity.JanePinBean;
import com.lianpos.util.CallAPIUtil;
import com.lianpos.util.StringUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

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
    String kouwei = null;
    private ImageView choose_back;
    private RelativeLayout addChooseItem;
    List<String> data1 = null;
    List<String> data2 = null;
    List<String> data3 = null;
    List<String> data4 = null;
    Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_listview);
        Intent intent = getIntent();
        danwei = intent.getStringExtra("danwei");
        guige = intent.getStringExtra("guige");
        pinpai = intent.getStringExtra("pinpai");
        kouwei = intent.getStringExtra("kouwei");
        listView = (ListView) findViewById(R.id.choose_list);
        choose_title = (TextView) findViewById(R.id.choose_title);
        choose_back = (ImageView) findViewById(R.id.choose_back);
        addChooseItem = (RelativeLayout) findViewById(R.id.addChooseItem);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        final ArrayAdapter<String> adapterGuige = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getGuigeData());
        final ArrayAdapter<String> adapterPinpai = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getPinpaiData());
        final ArrayAdapter<String> adapterKouwei = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getKouweiData());
        listView.setAdapter(adapter);

        if (danwei != null) {
            if (danwei.equals("1")) {
                listView.setAdapter(adapter);
                choose_title.setText("选择基本单位");
            }
        } else if (guige != null) {
            if (guige.equals("2")) {
                listView.setAdapter(adapterGuige);
                choose_title.setText("选择规格");
            }
        } else if (pinpai != null) {
            if (pinpai.equals("3")) {
                listView.setAdapter(adapterPinpai);
                choose_title.setText("选择品牌");
            }
        } else if (kouwei != null) {
            if (kouwei.equals("4")) {
                listView.setAdapter(adapterKouwei);
                choose_title.setText("选择口味");
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chooseStr = (String) ((TextView) view).getText();
                if (danwei != null) {
                    if (danwei.equals("1")) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.NewlyAddedUnit = chooseStr;
                        realm.commitTransaction();
                    }
                } else if (guige != null) {
                    if (guige.equals("2")) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.NewlyAddedSpecifications = chooseStr;
                        realm.commitTransaction();
                    }
                } else if (pinpai != null) {
                    if (pinpai.equals("3")) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.NewlyAddedBrand = chooseStr;
                        realm.commitTransaction();
                    }
                } else if (kouwei != null) {
                    if (kouwei.equals("4")) {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.NewlyAddedKouwei = chooseStr;
                        realm.commitTransaction();
                    }
                }

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
                final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象

                new AlertDialog.Builder(ChooseListView.this)
                        .setTitle("请填写")//提示框标题
                        .setView(view)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (danwei != null) {
                                            if (danwei.equals("1")) {
                                                data1.add(edit.getText().toString());
                                                adapter.notifyDataSetChanged();
                                            }
                                        } else if (guige != null) {
                                            if (guige.equals("2")) {
                                                data2.add(edit.getText().toString());
                                                adapterGuige.notifyDataSetChanged();
                                            }
                                        } else if (pinpai != null) {
                                            if (pinpai.equals("3")) {
                                                data3.add(edit.getText().toString());
                                                adapterPinpai.notifyDataSetChanged();
                                            }
                                        } else if (kouwei != null) {
                                            if (kouwei.equals("4")) {
                                                data4.add(edit.getText().toString());
                                                adapterKouwei.notifyDataSetChanged();
                                            }
                                        }

                                    }
                                }).setNegativeButton("取消", null).create().show();
            }
        });
    }


    private String initRelam() {
//        realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
//        realm.commitTransaction();
//        String ywUserId = "";
//        for (JanePinBean guest : guests) {
//            ywUserId = guest.ywUserId;
//        }

        // 从本地缓存中获取城市信息
        SharedPreferences sharedPreferences = getSharedPreferences("resultinfo", Context.MODE_PRIVATE);
        String ywUserId = sharedPreferences.getString("result_id", "");
        return ywUserId;
    }

    //单位
    private List<String> getData() {

        data1 = new ArrayList<String>();

        try {
            runChooseListView(initRelam(), "1", data1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data1;
    }

    //规格
    private List<String> getGuigeData() {

        data2 = new ArrayList<String>();
        try {
            runChooseListView(initRelam(), "2", data2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data2;
    }

    //品牌
    private List<String> getPinpaiData() {

        data3 = new ArrayList<String>();
        try {
            runChooseListView(initRelam(), "4", data3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data3;
    }

    //口味
    private List<String> getKouweiData() {

        data4 = new ArrayList<String>();
        try {
            runChooseListView(initRelam(), "3", data4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data4;
    }

    /**
     * 获取选择数据
     * post请求后台
     */
    private void runChooseListView(final String ywUserId, final String flag, final List<String> data) throws InterruptedException {
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                String json = "";
                try {
                    jsonObject.put("yw_user_id", ywUserId);
                    jsonObject.put("flag", flag);
                    json = JSONObject.toJSONString(jsonObject);//参数拼接成的String型json
                    json = URLEncoder.encode(json, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = CallAPIUtil.ObtainFun(json, Common.queryListUrl);

                if (!result.isEmpty()) {
                    JSONObject paramJson = JSON.parseObject(result);
                    String resultFlag = paramJson.getString("result_flag");
                    JSONArray resultBrandList = paramJson.getJSONArray("name_list");
                    if ("1".equals(resultFlag)) {
                        if (StringUtil.isNotNull(resultBrandList)) {
                            for (int i = 0; i < resultBrandList.size(); i++) {
                                JSONObject info = resultBrandList.getJSONObject(i);
                                String spBrand = info.getString("name");
                                if (StringUtil.isNotNull(spBrand)) {
                                    data.add(spBrand);
                                }
                            }
                        }
                    }
                }
            }
        });
        t1.start();
        t1.join();
    }

}