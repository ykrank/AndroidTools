package com.github.ykrank.androidtools.ui.adapter.delegate

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.github.ykrank.androidtools.R
import com.github.ykrank.androidtools.databinding.ItemProgressBinding
import com.github.ykrank.androidtools.ui.adapter.delegate.item.ProgressItem


class ProgressAdapterDelegate(context: Context) : LibBaseAdapterDelegate<ProgressItem, ProgressAdapterDelegate.ProgressViewHolder>(context, ProgressItem::class.java) {

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProgressViewHolder(DataBindingUtil.inflate(mLayoutInflater, R.layout.item_progress, parent, false))
    }

    override fun onBindViewHolderData(t: ProgressItem, position: Int, holder: ProgressViewHolder, payloads: List<Any>) {

    }

    class ProgressViewHolder(private val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root)
}
