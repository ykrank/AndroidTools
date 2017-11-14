package com.github.ykrank.androidtools.ui.adapter.simple

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ykrank.androidtools.GlobalData
import com.github.ykrank.androidtools.ui.adapter.delegate.item.FooterProgressItem
import com.github.ykrank.androidtools.ui.adapter.delegate.item.ProgressItem
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class SimpleAdapterDelegate internal constructor(context: Context, @param:LayoutRes private val layoutRes: Int, private val bindViewHolderCallback: BindViewHolderCallback?) : AdapterDelegate<MutableList<Any>>() {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return !ProgressItem::class.java.isInstance(items[position] ) && !FooterProgressItem::class.java.isInstance(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutRes, parent, false)
        return SimpleRecycleViewHolder(binding)
    }

    override fun onBindViewHolder(items: MutableList<Any>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        val binding = (holder as SimpleRecycleViewHolder<*>).binding
        binding.setVariable(GlobalData.provider.itemModelBRid, items[position])
        bindViewHolderCallback?.onBindViewHolder(position, binding)
    }
}