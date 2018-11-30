package com.github.ykrank.androidtools.ui.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import com.github.ykrank.androidtools.BuildConfig
import com.github.ykrank.androidtools.guava.Objects
import com.github.ykrank.androidtools.guava.Preconditions
import com.github.ykrank.androidtools.ui.adapter.delegate.FooterProgressAdapterDelegate
import com.github.ykrank.androidtools.ui.adapter.delegate.ProgressAdapterDelegate
import com.github.ykrank.androidtools.ui.adapter.delegate.item.FooterProgressItem
import com.github.ykrank.androidtools.ui.adapter.delegate.item.ProgressItem
import com.github.ykrank.androidtools.ui.adapter.model.SameItem
import com.github.ykrank.androidtools.util.L
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

abstract class LibBaseRecyclerViewAdapter : ListDelegationAdapter<MutableList<Any>> {

    constructor(context: Context) : this(context, false)

    /**
     * Only use when you sure inner model implements [StableIdModel]
     */
    constructor(context: Context, stableId: Boolean) {
        setHasStableIds(stableId)
        items = arrayListOf()
        delegatesManager.addDelegate(VIEW_TYPE_PROGRESS, ProgressAdapterDelegate(context))
        addAdapterDelegate(FooterProgressAdapterDelegate(context))
    }

    protected fun addAdapterDelegate(adapterDelegate: AdapterDelegate<MutableList<Any>>) {
        Preconditions.checkArgument(delegatesManager.getViewType(adapterDelegate) != VIEW_TYPE_PROGRESS)
        delegatesManager.addDelegate(adapterDelegate)
    }

    fun setHasProgress(hasProgress: Boolean) {
        if (hasProgress) {
            items.clear()
            items.add(ProgressItem())
            notifyDataSetChanged()
        } else {
            // we do not need to clear list if we have already changed
            // data set or we have no ProgressItem to been cleared
            if (items.size == 1 && items[0] is ProgressItem) {
                items.clear()
                notifyDataSetChanged()
            }
        }
    }

    fun showFooterProgress() {
        addItem(FooterProgressItem())
        notifyItemInserted(items.size)
    }

    fun hideFooterProgress() {
        val position = itemCount - 1
        Preconditions.checkState(getItem(position) is FooterProgressItem)
        removeItem(position)
        notifyItemRemoved(position)
    }

    /**
     * diff new dataSet with old, and dispatch update.\n
     * must another object with old.

     * @param newData     new data set
     * *
     * @param detectMoves [DiffUtil.calculateDiff]
     * *
     * @see DiffUtil
     */
    fun diffNewDataSet(newData: List<Any>, detectMoves: Boolean) {
        if (items === newData) {
            refreshDataSet(newData, detectMoves)
            L.throwNewErrorIfDebug(IllegalArgumentException("must set new data set"))
        }
        val diffResult = DiffUtil.calculateDiff(
                BaseDiffCallback(items, newData), detectMoves)
        items = newData.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * refresh new dataSet.if same object, just notifyDataSetChanged, else [.diffNewDataSet]

     * @param newData     new data set
     * *
     * @param detectMoves [.diffNewDataSet]
     */
    fun refreshDataSet(newData: List<Any>, detectMoves: Boolean) {
        if (items !== newData) {
            diffNewDataSet(newData, detectMoves)
        } else {
            notifyDataSetChanged()
        }
    }

    /**
     * swap to new dataSet.only notifyDataSetChanged

     * @param newData new data set
     */
    fun swapDataSet(newData: List<Any>) {
        items = newData.toMutableList()
        notifyDataSetChanged()
    }

    val dataSet: List<Any>
        get() = getItems()

    fun getItem(position: Int): Any? {
        return items[position]
    }

    fun addItem(`object`: Any) {
        items.add(`object`)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
    }

    override fun getItemId(position: Int): Long {
        if (!hasStableIds()) {
            return super.getItemId(position)
        }

        val d = items[position]
        if (d is StableIdModel) {
            return d.stableId
        }
        if (BuildConfig.DEBUG) {
            throw IllegalStateException("Item must implements StableIdModel if stable id")
        }
        return Objects.hashCode(d).toLong()
    }

    class BaseDiffCallback(var oldData: List<*>, var newData: List<*>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldD = oldData[oldItemPosition]
            val newD = newData[newItemPosition]
            if (oldD != null && oldD is SameItem) {
                return oldD.isSameItem(newD)
            }
            return oldD == newD
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }

    companion object {

        private val VIEW_TYPE_PROGRESS = 0
    }
}
