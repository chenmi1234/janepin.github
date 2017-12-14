package com.lianpos.devfoucs.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lianpos.activity.R;
import com.lianpos.entity.JanePinBean;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 一个按钮的dialog
 * Created by wangshuai on 2017/10/31 0031.
 */

public class AddCommodityDialog extends Dialog {

    private TextView yes;//确定按钮
    private TextView addShopBtn; //取消按钮
    private TextView messageTv;//消息提示文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onNoOnclickListener noOnclickListener;//确定按钮被点击了的监听器
    private EditText dialog_unit_price;
    private Realm realm = null;
    private EditText addShopDialogNumber;
    private TextView billUnitDialog;
    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    public AddCommodityDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_commodity_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });

        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                    janePinBean.AddShopDialogNumber = addShopDialogNumber.getText().toString();
                    janePinBean.AddShopDialogPrice = dialog_unit_price.getText().toString();
                    realm.commitTransaction();
                    noOnclickListener.onNoClick();
                }
            }
        });

        dialog_unit_price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        dialog_unit_price.setText(s);
                        dialog_unit_price.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    dialog_unit_price.setText(s);
                    dialog_unit_price.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        dialog_unit_price.setText(s.subSequence(0, 1));
                        dialog_unit_price.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }


        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了message
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
//        if (yesStr != null) {
//            yes.setText(yesStr);
//        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        messageTv = (TextView) findViewById(R.id.message);
        yes = (TextView) findViewById(R.id.dialogNumber);
        addShopBtn = (TextView) findViewById(R.id.dialogDanwei);
        dialog_unit_price = (EditText) findViewById(R.id.dialog_unit_price);
        addShopDialogNumber = (EditText) findViewById(R.id.addShopDialogNumber);
        billUnitDialog = (TextView) findViewById(R.id.billUnitDialog);
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onNoOnclickListener {
        public void onNoClick();
    }
}