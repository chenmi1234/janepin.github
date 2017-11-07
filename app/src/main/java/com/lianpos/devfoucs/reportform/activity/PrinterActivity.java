package com.lianpos.devfoucs.reportform.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.firebase.BaseActivity;

/**
 * 打印机
 * Created by wangshuai on 2017/11/07 .
 */

public class PrinterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView printer_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        init();
    }

    private void init() {
        // 初始化控件
        initActivity();
        // 初始化点击事件
        initEvent();
        // 方法实现
        funRealization();
    }

    /**
     * 初始化控件
     */
    private void initActivity() {
        printer_back = (ImageView) findViewById(R.id.printer_back);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        printer_back.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.printer_back:
                finish();
            break;
        }
    }

}