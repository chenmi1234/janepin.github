package com.lianpos.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.contacts.adapter.CityAdapter;
import com.lianpos.devfoucs.contacts.decoration.DividerItemDecoration;
import com.lianpos.devfoucs.contacts.model.CityBean;
import com.lianpos.devfoucs.contacts.ui.AddFriendPop;
import com.lianpos.devfoucs.view.TwoButtonWarningDialog;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 联系人
 *
 * @author wangshuai
 * @create time 2017/10/27
 */
public class DynamicFragment extends Fragment {
    private static final String TAG = "zxt";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private SwipeDelMenuAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();
    private ImageView addFriend;
    private SuspensionDecoration mDecoration;
    private TextView title;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    // 两个按钮的dialog
    private TwoButtonWarningDialog twoButtonDialog;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dynamic, null);
        mRv = (RecyclerView) rootView.findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        addFriend = (ImageView) rootView.findViewById(R.id.begin_menu);
        addFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddFriendPop popWindow = new AddFriendPop(getActivity());
                popWindow.showPopupWindow(rootView.findViewById(R.id.begin_menu));
            }
        });
        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter.setOnLongItemClickListener(new CityAdapter.OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, final int position) {
                twoButtonDialog = new TwoButtonWarningDialog(getActivity());
                twoButtonDialog.setYesOnclickListener(new TwoButtonWarningDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        mDatas.remove(position);
                        mAdapter.notifyDataSetChanged();
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.setNoOnclickListener(new TwoButtonWarningDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        twoButtonDialog.dismiss();
                    }
                });
                twoButtonDialog.show();

            }
        });

        //使用indexBar
        mTvSideBarHint = (TextView) rootView.findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) rootView.findViewById(R.id.indexBar);//IndexBar

        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));

        title = (TextView) rootView.findViewById(R.id.title);
        title.setText("联系人");

        return rootView;
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                for (int i = 0; i < data.length; i++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mDatas.add(cityBean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                        .setNeedRealIndex(true)//设置需要真实的索引
                        .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                        .setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
            }
        }, 1);
    }

    /**
     * 和CityAdapter 一模一样，只是修改了 Item的布局
     * Created by wangshuai .
     * Date: 17/11/1
     */

    private class SwipeDelMenuAdapter extends CityAdapter {

        public SwipeDelMenuAdapter(DynamicFragment mContext, List<CityBean> mDatas) {
            super(mContext.getContext(), mDatas);
        }

        @Override
        public SwipeDelMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.item_city_swipe, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.itemView.findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((SwipeMenuLayout) holder.itemView).quickClose();
//                    mDatas.remove(holder.getAdapterPosition());
//                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
//                            .setNeedRealIndex(true)//设置需要真实的索引
//                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
//                            .setmSourceDatas(mDatas)//设置数据
//                            .invalidate();
                    callPhone("18842669964");
//                    notifyDataSetChanged();
                }
            });
        }
    }

    public void callPhone(String str) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                title.setText("解码结果： \n" + content);

            }
        }
    }

}
