package com.lianpos.devfoucs.contacts.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import com.lianpos.activity.R
import com.lianpos.devfoucs.homepage.activity.MakeMoneyActivity
import com.lianpos.entity.JanePinBean
import com.lianpos.zxing.android.CaptureActivity
import io.realm.Realm
import io.realm.RealmResults

/**
 * 添加好友
 *
 * @author wangshuai
 * @create time 2017/11/01
 */
class AddFriendPop(context: Activity) : PopupWindow(), OnClickListener {
    private val conentView: View
    internal lateinit var realm: Realm
    var ywUserId = ""
    internal var guests: RealmResults<JanePinBean>? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        conentView = inflater.inflate(R.layout.begin_order_popup, null)
        this.contentView = conentView
        this.width = LayoutParams.WRAP_CONTENT
        this.height = LayoutParams.WRAP_CONTENT
        this.isFocusable = true
        this.isOutsideTouchable = true
        this.update()
        val dw = ColorDrawable(0)
        this.setBackgroundDrawable(dw)

        conentView.findViewById(R.id.begin_cancel_order).setOnClickListener(this)
        conentView.findViewById(R.id.begin_assignment_order).setOnClickListener(this)
        conentView.findViewById(R.id.begin_same_driver).setOnClickListener(this)
    }

    /**
     * PopupWindow
     *
     * @param parent
     */
    fun showPopupWindow(parent: View) {
        if (!this.isShowing) {
            this.showAsDropDown(parent, parent.layoutParams.width / 2 + 100, 15)
        } else {
            this.dismiss()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.begin_cancel_order -> {
                this@AddFriendPop.dismiss()
                val addFriend = Intent()
                addFriend.setClass(v.context, AddPhoneNmbActivity::class.java)
                v.context.startActivity(addFriend)
            }
            R.id.begin_assignment_order -> {
                this@AddFriendPop.dismiss()
                val intent = Intent()
                intent.setClass(v.context, CaptureActivity::class.java)
                (v.context as Activity).startActivityForResult(intent, REQUEST_CODE_SCAN)
            }
            R.id.begin_same_driver -> {
                this@AddFriendPop.dismiss()
                val invitation = Intent()
                invitation.setClass(v.context, MakeMoneyActivity::class.java)
                v.context.startActivity(invitation)
            }
            else -> {
            }
        }
    }

    companion object {
        private val DECODED_CONTENT_KEY = "codedContent"
        private val DECODED_BITMAP_KEY = "codedBitmap"

        private val REQUEST_CODE_SCAN = 0x0000
    }

}
