package com.lianpos.devfoucs.contacts.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.homepage.activity.MakeMoneyActivity;
import com.lianpos.zxing.android.CaptureActivity;

import static android.app.Activity.RESULT_OK;

/**
 * 添加好友
 *
 * @author wangshuai
 * @create time 2017/11/01
 */
public class AddFriendPop extends PopupWindow implements OnClickListener {
    private View conentView;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    public AddFriendPop(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.begin_order_popup, null);
        this.setContentView(conentView);
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);

        conentView.findViewById(R.id.begin_cancel_order).setOnClickListener(this);
        conentView.findViewById(R.id.begin_assignment_order).setOnClickListener(this);
        conentView.findViewById(R.id.begin_same_driver).setOnClickListener(this);
    }

    private static final int REQUEST_CODE_SCAN = 0x0000;

    /**
     * PopupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, (parent.getLayoutParams().width / 2)+100, 15);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_cancel_order:
                AddFriendPop.this.dismiss();
                Intent addFriend=new Intent();
                addFriend.setClass(v.getContext(), AddPhoneNmbActivity.class);
                v.getContext().startActivity(addFriend);
                break;
            case R.id.begin_assignment_order:
                AddFriendPop.this.dismiss();
                Intent intent=new Intent();
                intent.setClass(v.getContext(), CaptureActivity.class);
                ((Activity) v.getContext()).startActivityForResult(intent,REQUEST_CODE_SCAN);
                break;
            case R.id.begin_same_driver:
                AddFriendPop.this.dismiss();
                Intent invitation = new Intent();
                invitation.setClass(v.getContext(), MakeMoneyActivity.class);
                v.getContext().startActivity(invitation);
                break;
            default:
                break;
        }
    }

}
