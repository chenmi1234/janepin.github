package com.lianpos.devfoucs.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.activity.ScanningActivity;
import com.lianpos.firebase.BaseActivity;
import com.lianpos.util.MyToggle;
import com.lianpos.util.MyToggle.OnToggleStateListener;
import com.lianpos.zxing.android.CaptureActivity;

/**
 * 新增商品
 * Created by wangshuai on 2017/11/09 .
 */

public class IncreaseCommodityActivity extends BaseActivity implements View.OnClickListener {
    //返回按钮
    private ImageView commodity_back;
    //开关工具类
    private MyToggle suitoggle;
    //套餐计算
    private LinearLayout suitNum;
    //套餐单位
    private RelativeLayout suitDanwei;
    //开关工具类
    private MyToggle splitToggle;
    //套餐计算
    private LinearLayout splitNum;
    //套餐单位
    private RelativeLayout splitDanwei;
    //扫描商品条形码
    private ImageView scanningBar;
    //组装拆分扫描条形码按钮
    private ImageView shopChaifen;
    String num1 = "";
    String request = "";
    String num2 = "";
    String request1 = "";
    //添加商品商品条码赋值
    private EditText shopEdittext;
    //组装拆分商品条码赋值
    private EditText chaifenEdittext;
    private RelativeLayout danweiChoose;
    private RelativeLayout guigeChoose;
    private RelativeLayout pinpaiChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        Intent intert = getIntent();
        num1 = intert.getStringExtra("page");
        request = intert.getStringExtra("codedContent");
        if (num1 != null) {
            if (num1.equals("2")) {
                shopEdittext.setText(request);
            }
        }
        num2 = intert.getStringExtra("zhuan");
        request1 = intert.getStringExtra("shopTiao");
        if (num2 != null) {
            if (num2.equals("3")) {
                chaifenEdittext.setText(request1);
            }
        }
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
        commodity_back = (ImageView) findViewById(R.id.commodity_back);
        suitoggle = (MyToggle) findViewById(R.id.suitoggle);
        suitNum = (LinearLayout) findViewById(R.id.suitNum);
        suitDanwei = (RelativeLayout) findViewById(R.id.suitDanwei);
        splitToggle = (MyToggle) findViewById(R.id.splitToggle);
        splitNum = (LinearLayout) findViewById(R.id.splitNum);
        splitDanwei = (RelativeLayout) findViewById(R.id.splitDanwei);
        scanningBar = (ImageView) findViewById(R.id.scanningBar);
        shopEdittext = (EditText) findViewById(R.id.shopEdittext);
        shopChaifen = (ImageView) findViewById(R.id.shopChaifen);
        chaifenEdittext = (EditText) findViewById(R.id.chaifenEdittext);
        danweiChoose = (RelativeLayout) findViewById(R.id.danweiChoose);
        guigeChoose = (RelativeLayout) findViewById(R.id.guigeChoose);
        pinpaiChoose = (RelativeLayout) findViewById(R.id.pinpaiChoose);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        commodity_back.setOnClickListener(this);
        danweiChoose.setOnClickListener(this);
        guigeChoose.setOnClickListener(this);
        pinpaiChoose.setOnClickListener(this);
    }

    /**
     * 方法实现
     */
    private void funRealization() {
        //设置开关显示所用的图片
        suitoggle.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft);
        //套餐初始为关闭
        suitNum.setVisibility(View.GONE);
        suitDanwei.setVisibility(View.GONE);
        //设置开关组装拆分图片
        splitToggle.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight);

        //设置开关的默认状态    true开启状态
        suitoggle.setToggleState(true);

        //设置开关的监听
        suitoggle.setOnToggleStateListener(new OnToggleStateListener() {
            @Override
            public void onToggleState(boolean state) {
                if (state) {
                    suitoggle.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight);
                    suitNum.setVisibility(View.VISIBLE);
                    suitDanwei.setVisibility(View.VISIBLE);
                    splitToggle.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight);
                    splitNum.setVisibility(View.VISIBLE);
                    splitDanwei.setVisibility(View.VISIBLE);
                } else {
                    suitoggle.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft);
                    suitNum.setVisibility(View.GONE);
                    suitDanwei.setVisibility(View.GONE);
                    splitToggle.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft);
                    splitNum.setVisibility(View.GONE);
                    splitDanwei.setVisibility(View.GONE);
                }
            }
        });

        splitToggle.setOnToggleStateListener(new OnToggleStateListener() {
            @Override
            public void onToggleState(boolean state) {
                if (state) {
                    splitToggle.setImageRes(R.mipmap.swipeleft, R.mipmap.swipeleft, R.mipmap.swiperight);
                    splitNum.setVisibility(View.VISIBLE);
                    splitDanwei.setVisibility(View.VISIBLE);
                } else {
                    splitToggle.setImageRes(R.mipmap.swiperight, R.mipmap.swiperight, R.mipmap.swipeleft);
                    splitNum.setVisibility(View.GONE);
                    splitDanwei.setVisibility(View.GONE);
                }
            }
        });

        scanningBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IncreaseCommodityActivity.this, ScanningActivity.class);
                intent.putExtra("commodity", "shop");
                startActivity(intent);
            }
        });

        shopChaifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IncreaseCommodityActivity.this, ScanningActivity.class);
                intent.putExtra("increase", "chaifen");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commodity_back:
                finish();
                break;
            case R.id.danweiChoose:
                Intent intent = new Intent();
                intent.setClass(IncreaseCommodityActivity.this, ChooseListView.class);
                intent.putExtra("danwei", "1");
                startActivity(intent);
                break;
            case R.id.guigeChoose:
                Intent guigeintent = new Intent();
                guigeintent.setClass(IncreaseCommodityActivity.this, ChooseListView.class);
                guigeintent.putExtra("guige", "2");
                startActivity(guigeintent);
                break;
            case R.id.pinpaiChoose:
                Intent pinpaiintent = new Intent();
                pinpaiintent.setClass(IncreaseCommodityActivity.this, ChooseListView.class);
                pinpaiintent.putExtra("pinpai", "3");
                startActivity(pinpaiintent);
                break;
        }
    }

}