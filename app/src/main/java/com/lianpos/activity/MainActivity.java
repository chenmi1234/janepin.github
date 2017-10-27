package com.lianpos.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.lianpos.fragment.DynamicFragment;
import com.lianpos.fragment.HomeFragment;
import com.lianpos.fragment.IDCardFragment;
import com.lianpos.fragment.MessageFragment;
import com.lianpos.fragment.PersonFragment;

/**
 * 对fragment的切换,底部图标颜色的切换
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class MainActivity extends FragmentActivity {
    //要切换显示的四个Fragment
    private IDCardFragment idCardFragment;
    private DynamicFragment dynamicFragment;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private PersonFragment personFragment;

    private int currentId = R.id.iv_make;// 当前选中id,默认是主页
    private TextView tvMain, tvDynamic, ivHome, tvMessage, tvPerson;//底部5个TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain = (TextView) findViewById(R.id.tv_main);
        tvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        tvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvPerson = (TextView) findViewById(R.id.tv_person);
        ivHome = (TextView) findViewById(R.id.iv_make);
        ivHome.setSelected(true);//首页默认选中
        /**
         * 默认加载首页
         */
        if (homeFragment == null) {//如果为空先添加进来.不为空直接显示
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, homeFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(homeFragment);
        }
        tvMain.setOnClickListener(tabClickListener);
        tvDynamic.setOnClickListener(tabClickListener);
        ivHome.setOnClickListener(tabClickListener);
        tvMessage.setOnClickListener(tabClickListener);
        tvPerson.setOnClickListener(tabClickListener);
    }

    private OnClickListener tabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
                changeSelect(v.getId());//改变图标跟文字颜色的选中
                changeFragment(v.getId());//fragment的切换
                currentId = v.getId();//设置选中id
            }
        }
    };

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务
        //隐藏所有fragment
        hideFragments(transaction);
        if (resId == R.id.tv_main) {
            //身份卡
            //如果为空先添加进来.不为空直接显示
            if (idCardFragment == null) {
                idCardFragment = new IDCardFragment();
                transaction.add(R.id.main_container, idCardFragment);
            } else {
                transaction.show(idCardFragment);
            }
        } else if (resId == R.id.tv_dynamic) {
            //联系人
            if (dynamicFragment == null) {
                dynamicFragment = new DynamicFragment();
                transaction.add(R.id.main_container, dynamicFragment);
            } else {
                transaction.show(dynamicFragment);
            }
        } else if (resId == R.id.iv_make) {
            //首页
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                transaction.add(R.id.main_container, homeFragment);
            } else {
                transaction.show(homeFragment);
            }
        } else if (resId == R.id.tv_message) {
            //报表
            if (messageFragment == null) {
                messageFragment = new MessageFragment();
                transaction.add(R.id.main_container, messageFragment);
            } else {
                transaction.show(messageFragment);
            }
        } else if (resId == R.id.tv_person) {
            //我
            if (personFragment == null) {
                personFragment = new PersonFragment();
                transaction.add(R.id.main_container, personFragment);
            } else {
                transaction.show(personFragment);
            }
        }
        //提交事务
        transaction.commit();
    }

    /**
     * 显示之前隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        //不为空才隐藏,如果不判断第一次会有空指针异常
        if (idCardFragment != null)
            transaction.hide(idCardFragment);
        if (dynamicFragment != null)
            transaction.hide(dynamicFragment);
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (messageFragment != null)
            transaction.hide(messageFragment);
        if (personFragment != null)
            transaction.hide(personFragment);
    }

    /**
     * 改变TextView选中颜色
     *
     * @param resId
     */
    private void changeSelect(int resId) {
        tvMain.setSelected(false);
        tvDynamic.setSelected(false);
        ivHome.setSelected(false);
        tvMessage.setSelected(false);
        tvPerson.setSelected(false);
        switch (resId) {
            case R.id.tv_main:
                tvMain.setSelected(true);
                break;
            case R.id.tv_dynamic:
                tvDynamic.setSelected(true);
                break;
            case R.id.iv_make:
                ivHome.setSelected(true);
                break;
            case R.id.tv_message:
                tvMessage.setSelected(true);
                break;
            case R.id.tv_person:
                tvPerson.setSelected(true);
                break;
        }
    }
}
