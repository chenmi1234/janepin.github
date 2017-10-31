package com.lianpos.devfoucs.login.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liangmutian.mypicker.DataPickerDialog;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.CheckInforUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:注册
 * Created by wangshuai on 2017/10/26.
 */

public class RegisterInfoActivity extends BaseActivity implements View.OnClickListener {
    // 姓名输入
    private EditText register_name_editText;
    // 下一步
    private Button next_button;
    // 返回
    private ImageView register_back;
    // 姓名删除
    private ImageView deleteNameImg;
    // 注册姓名message
    private TextView registerPhoneMessage;
    // 时间选择器dialog 性别选择器dialog
    private Dialog dateDialog, chooseDialog;
    // 性别选择按钮，生日日期选择按钮
    private RelativeLayout choose_sex_layout, choose_birday_layout;
    // 性别选择器数组
    private List<String> list = new ArrayList<>();
    // 性别 生日
    private TextView sex_text, briday_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
        // 方法实现
        funRealization();
        // 初始化性别
        sexArrayListFun();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        // 姓名输入
        register_name_editText = (EditText) findViewById(R.id.register_name_editText);
        // 下一步
        next_button = (Button) findViewById(R.id.next_button);
        // 返回
        register_back = (ImageView) findViewById(R.id.register_back);
        // 删除姓名
        deleteNameImg = (ImageView) findViewById(R.id.deleteNameImg);
        // 注册账号message
        registerPhoneMessage = (TextView) findViewById(R.id.registerPhoneMessage);
        // 性别选择按钮
        choose_sex_layout = (RelativeLayout) findViewById(R.id.choose_sex_layout);
        // 生日选择按钮
        choose_birday_layout = (RelativeLayout) findViewById(R.id.choose_birday_layout);
        // 性别
        sex_text = (TextView) findViewById(R.id.sex_text);
        // 生日
        briday_text = (TextView) findViewById(R.id.briday_text);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        // 姓名删除
        deleteNameImg.setOnClickListener(this);
        // 下一步
        next_button.setOnClickListener(this);
        // 返回
        register_back.setOnClickListener(this);
        // 性别选择按钮
        choose_sex_layout.setOnClickListener(this);
        // 生日选择按钮
        choose_birday_layout.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        // 账号输入结束监听
        register_name_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!register_name_editText.getText().toString().isEmpty()) {
                } else {
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteNameImg:
                break;
            case R.id.next_button:
                Intent intent1 = new Intent();
                intent1.setClass(RegisterInfoActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.register_back:
                finish();
                break;
            case R.id.choose_sex_layout:
                showChooseDialog(list);
                break;
            case R.id.choose_birday_layout:
                showDateDialog(DateUtil.getDateForString("1990-01-01"));
                break;
        }
    }

    /**
     * 初始化性别
     */
    private void sexArrayListFun() {
        String[] data = getResources().getStringArray(R.array.list);
        for (String str : data) {
            list.add(str);
        }
    }

    /**
     * 性别选择器
     */
    private void showChooseDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        chooseDialog = builder.setData(mlist).setSelection(0).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        sex_text.setText(itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();

        chooseDialog.show();
    }

    /**
     * 生日选择器
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

                briday_text.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));

            }

            @Override
            public void onCancel() {

            }
        })

                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }
}
