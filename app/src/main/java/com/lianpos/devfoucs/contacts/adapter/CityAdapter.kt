package com.lianpos.devfoucs.contacts.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.lianpos.activity.R
import com.lianpos.devfoucs.contacts.model.CityBean
import com.lianpos.devfoucs.homepage.activity.IWantBillingActivity
import com.lianpos.devfoucs.linkman.ui.LinkManActivity
import com.lianpos.devfoucs.login.activity.LoginActivity
import com.lianpos.devfoucs.login.activity.RegisterActivity

/**
 * Created by wangshuai .
 * Date: 17/11/02
 */

open class CityAdapter(protected var mContext: Context, protected var mDatas: List<CityBean>?) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    protected var mInflater: LayoutInflater
    var mOnLongItemClickListener: OnRecyclerViewLongItemClickListener? = null//长按

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    fun getDatas(): List<CityBean>? {
        return mDatas
    }

    fun setDatas(datas: List<CityBean>): CityAdapter {
        mDatas = datas
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_city_link, parent, false))
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val cityBean = mDatas!![position]
        holder.tvCity.text = cityBean.city
        holder.content.setOnClickListener { }
        holder.content.setOnLongClickListener { v ->
            if (mOnLongItemClickListener != null) {
                mOnLongItemClickListener!!.onLongItemClick(v, position)
            }
            true
        }
        holder.avatar.text = "18842535353"
        holder.tvSupermarket.text = "十七超市"
    }

    override fun getItemCount(): Int {
        return if (mDatas != null) mDatas!!.size else 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvCity: TextView
        internal var avatar: TextView
        internal var tvSupermarket: TextView
        internal var content: View

        init {
            tvCity = itemView.findViewById(R.id.tvCity) as TextView
            tvSupermarket = itemView.findViewById(R.id.tvSupermarket) as TextView
            avatar = itemView.findViewById(R.id.tvPhone) as TextView
            content = itemView.findViewById(R.id.content)
        }
    }

    interface OnRecyclerViewLongItemClickListener {
        fun onLongItemClick(view: View, position: Int)
    }

    fun setOnLongItemClickListener(listener: OnRecyclerViewLongItemClickListener) {
        this.mOnLongItemClickListener = listener
    }
}
