package com.lianpos.devfoucs.linkman.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.contacts.adapter.CityAdapter;
import com.lianpos.devfoucs.contacts.decoration.DividerItemDecoration;
import com.lianpos.devfoucs.contacts.model.CityBean;
import com.lianpos.devfoucs.contacts.ui.AddFriendPop;
import com.lianpos.devfoucs.linkman.adapter.LinkManAdapter;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：开单盘点联系人
 * 头部不是HeaderView 因为头部也需要快速导航，"↑"
 * 作者：wangshuai
 * 时间： 2017/11/7.
 */

public class LinkManActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private LinkManAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();
    private ImageView addFriend;
    private ImageView linkman_back;
    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkman);

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mAdapter = new LinkManAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(LinkManActivity.this, DividerItemDecoration.Companion.getVERTICAL_LIST()));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));

        addFriend = (ImageView) findViewById(R.id.begin_menu);
        addFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinkAddFriendPop popWindow = new LinkAddFriendPop(LinkManActivity.this);
                popWindow.showPopupWindow(findViewById(R.id.begin_menu));
            }
        });

        linkman_back = (ImageView) findViewById(R.id.linkman_back);
        linkman_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                // 但是它不需要被ItemDecoration设一个标题titile
                for (int i = 0; i < data.length; i++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mDatas.add(cityBean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
            }
        }, 500);
    }

}
