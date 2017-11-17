package com.lianpos.devfoucs.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.activity.ScanningActivity;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.devfoucs.shoppingcart.fragment.ProductsFragment;

import java.util.ArrayList;

/**
 * Created by wangshuai on 2017/11/6.
 * 小区商家商家详情
 */
public class MerchantActivity extends FragmentActivity {
    // 下划线标记
    private View group_line;
    // 服务产品
    private ProductsFragment productsFragment;
    //上门服务Fragment
    private ArrayList<Fragment> fragments;
    //返回键
    private ImageView merchant_back;
    //新增商品
    private TextView addShopping;
    //标题名集合
    private RadioButton[] titleText = null;
    private ViewPager pager;
//    private RadioGroup discount_layout;
    private ImageView scanning_shop_tiaoxing;
    private EditText serch_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        Intent intert = getIntent();
        initView();
        String addShopTiao = intert.getStringExtra("shopAdd");
        serch_shop.setText(addShopTiao);
    }

    private void initView() {
        serch_shop = (EditText) findViewById(R.id.serch_shop);
        pager = (ViewPager) findViewById(R.id.pager);
        merchant_back = (ImageView) findViewById(R.id.merchant_back);
        merchant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addShopping = (TextView) findViewById(R.id.addShopping);
        addShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MerchantActivity.this,IncreaseCommodityActivity.class);
                startActivity(intent);
            }
        });
        group_line = findViewById(R.id.group_line);
        fragments = new ArrayList<Fragment>();
        productsFragment = new ProductsFragment();
        fragments.add(productsFragment);
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(fragmentPagerAdapter);
        fragmentPagerAdapter.setFragments(fragments);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
        // 第一次启动时选中第0个tab
        pager.setCurrentItem(0);
        pager.setOffscreenPageLimit(2);
        scanning_shop_tiaoxing = (ImageView)findViewById(R.id.scanning_shop_tiaoxing);
        scanning_shop_tiaoxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MerchantActivity.this, ScanningActivity.class);
                intent.putExtra("addshop", "addshop");
                startActivity(intent);
            }
        });
    }

    /**
     * 切换更换下划线状态
     *
     * @param position
     */
    private void setVisible(int position) {
        switch (position) {
            case 0:
                group_line.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 设置选中图标的文字颜色与
     * 下划线可见
     *
     * @param index
     */
    private void chingeIndexView(int index) {
        for (int i = 0; i < titleText.length; i++) {
            titleText[i].setChecked(false);
        }
        if (index < titleText.length) {
            titleText[index].setChecked(true);
        }

    }


    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;
        private FragmentManager fm;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        public void setFragments(ArrayList<Fragment> fragments) {
            if (this.fragments != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragments) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragments = fragments;
            notifyDataSetChanged();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            chingeIndexView(position);
            setVisible(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }
    }
}
