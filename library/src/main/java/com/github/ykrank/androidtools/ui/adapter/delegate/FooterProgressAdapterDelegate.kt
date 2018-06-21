package com.github.ykrank.androidtools.ui.adapter.delegate

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.ykrank.androidtools.R
import com.github.ykrank.androidtools.ui.adapter.delegate.item.FooterProgressItem


class FooterProgressAdapterDelegate(context: Context) : LibBaseAdapterDelegate<FooterProgressItem,
        FooterProgressAdapterDelegate.FooterProgressViewHolder>(context, FooterProgressItem::class.java) {

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return FooterProgressViewHolder(mLayoutInflater.inflate(R.layout.item_footer_progress,
                parent, false))
    }

    override fun onBindViewHolderData(t: FooterProgressItem, position: Int, holder: FooterProgressViewHolder, payloads: List<Any>) {

    }

    class FooterProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
