package com.lianpos.devfoucs.contacts.utils

import android.view.View
import android.view.ViewGroup

/**
 * 通用的RecyclerView 的ItemClickListener，包含点击和长按
 * Created by wangshuai .
 * Date: 17/11/2
 */
interface OnItemClickListener<T> {
    fun onItemClick(parent: ViewGroup, view: View, t: T, position: Int)
    fun onItemLongClick(parent: ViewGroup, view: View, t: T, position: Int): Boolean
}