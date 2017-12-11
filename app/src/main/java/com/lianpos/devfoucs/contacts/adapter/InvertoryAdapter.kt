package com.lianpos.devfoucs.contacts.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lianpos.activity.R
import com.lianpos.devfoucs.contacts.model.InventoryBean

/**
 * Created by wangshuai .
 * Date: 17/11/02
 */

open class InvertoryAdapter(protected var mContext: Context, protected var mDatas: List<InventoryBean>?) : RecyclerView.Adapter<InvertoryAdapter.ViewHolder>() {
    protected var mInflater: LayoutInflater
    var mOnLongItemClickListener: OnRecyclerViewLongItemClickListener? = null//长按

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    fun getDatas(): List<InventoryBean>? {
        return mDatas
    }

    fun setDatas(datas: List<InventoryBean>): InvertoryAdapter {
        mDatas = datas
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvertoryAdapter.ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.inventory_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: InvertoryAdapter.ViewHolder, position: Int) {
        val inventoryBean = mDatas!![position]
        holder.inName.text = inventoryBean.inName
        holder.tiaoma.text = inventoryBean.tiaoma
        holder.inNumber.text = inventoryBean.inNumber
        holder.inUnit.text = inventoryBean.unit
        holder.tvInNumber.text = inventoryBean.inventory
        holder.tvInUnit.text = inventoryBean.inventoryunit
        holder.content.setOnClickListener { }
        holder.content.setOnLongClickListener { v ->
            if (mOnLongItemClickListener != null) {
                mOnLongItemClickListener!!.onLongItemClick(v, position)
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return if (mDatas != null) mDatas!!.size else 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var inName: TextView
        internal var tiaoma: TextView
        internal var inNumber: TextView
        internal var inUnit: TextView
        internal var tvInNumber: TextView
        internal var tvInUnit: TextView
        internal var content: View

        init {
            inName = itemView.findViewById(R.id.tv_name) as TextView
            tiaoma = itemView.findViewById(R.id.tv_tiaoma) as TextView
            inNumber = itemView.findViewById(R.id.tv_number) as TextView
            inUnit = itemView.findViewById(R.id.tv_unit) as TextView
            tvInNumber = itemView.findViewById(R.id.tv_inventory_number) as TextView
            tvInUnit = itemView.findViewById(R.id.tv_inventory_unit) as TextView
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
