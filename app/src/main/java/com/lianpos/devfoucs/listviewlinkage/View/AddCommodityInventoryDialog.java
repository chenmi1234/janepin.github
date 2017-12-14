package com.lianpos.devfoucs.listviewlinkage.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.entity.JanePinBean;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 一个按钮的dialog
 * Created by wangshuai on 2017/10/31 0031.
 */

public class AddCommodityInventoryDialog extends Dialog {

    private TextView yes;//确定按钮
    private TextView addShopBtn; //取消按钮
    private TextView messageTv;//消息提示文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private onNoOnclickListener noOnclickListener;//确定按钮被点击了的监听器
    private EditText dialog_unit_price, jianyi_price_edittext;
    //    private Realm realm = null;
    private EditText addShopDialogNumber;
    Realm realm;
    private TextView dialogTitle;
    private Spinner spinner2;
    private EditText addShopDialogStock, price_inventory;
    String invenTiaoma = "";

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

    public AddCommodityInventoryDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_commodity_inventory_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String showTitle = "";
        for (JanePinBean guest : guests) {
            showTitle = guest.AddShopInventoryName;
            invenTiaoma = guest.AddShopInventoryTiaoma;
        }
        dialogTitle.setText(showTitle);

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
                    if (addShopDialogStock.getText().toString().equals("") || price_inventory.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "库存、售价不可以为空", Toast.LENGTH_SHORT).show();
                    } else {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                        janePinBean.AddShopInventoryName = dialogTitle.getText().toString();
                        janePinBean.AddShopInventoryTiaoma = invenTiaoma;
                        janePinBean.AddShopInventoryStock = addShopDialogStock.getText().toString();
                        janePinBean.AddShopInventoryUnit = "箱";
                        janePinBean.AddShopDInventoryPrice = price_inventory.getText().toString();
                        realm.commitTransaction();
                        noOnclickListener.onNoClick();
                    }
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
        yes = (TextView) findViewById(R.id.dialogNumber);
        addShopBtn = (TextView) findViewById(R.id.dialogDanwei);
        dialog_unit_price = (EditText) findViewById(R.id.dialog_unit_price);
        jianyi_price_edittext = (EditText) findViewById(R.id.jianyi_price_edittext);
        addShopDialogNumber = (EditText) findViewById(R.id.addShopDialogNumber);
        dialogTitle = (TextView) findViewById(R.id.dialogTitle);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        addShopDialogStock = (EditText) findViewById(R.id.addShopDialogStock);
        price_inventory = (EditText) findViewById(R.id.price_inventory);
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