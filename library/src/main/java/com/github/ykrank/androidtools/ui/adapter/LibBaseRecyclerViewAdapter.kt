package com.github.ykrank.androidtools.ui.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import com.github.ykrank.androidtools.BuildConfig
import com.github.ykrank.androidtools.guava.Objects
import com.github.ykrank.androidtools.guava.Preconditions
import com.github.ykrank.androidtools.ui.adapter.delegate.FooterAdapterDelegate
import com.github.ykrank.androidtools.ui.adapter.delegate.FooterProgressAdapterDelegate
import com.github.ykrank.androidtools.ui.adapter.delegate.ProgressAdapterDelegate
import com.github.ykrank.androidtools.ui.adapter.delegate.item.FooterProgressItem
import com.github.ykrank.androidtools.ui.adapter.delegate.item.ProgressItem
import com.github.ykrank.androidtools.ui.adapter.model.DiffSameItem
import com.github.ykrank.androidtools.util.L
import com.github.ykrank.androidtools.util.RxJavaUtil
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class LibBaseRecyclerViewAdapter : ListDelegationAdapter<MutableList<Any>> {

    var updateDispose: Disposable? = null

    /**
     * Whether diffutil calculateDiffing
     */
    private var differing: Boolean = false

    constructor(context: Context) : this(context, true)

    /**
     * Only use when you sure inner model implements [StableIdModel]
     */
    constructor(context: Context, stableId: Boolean) {
        setHasStableIds(stableId)
        items = arrayListOf()
        delegatesManager.addDelegate(VIEW_TYPE_PROGRESS, ProgressAdapterDelegate(context))
        addAdapterDelegate(FooterProgressAdapterDelegate(context))
        addAdapterDelegate(FooterAdapterDelegate(context))
    }

    protected fun addAdapterDelegate(adapterDelegate: AdapterDelegate<MutableList<Any>>) {
        Preconditions.checkArgument(delegatesManager.getViewType(adapterDelegate) != VIEW_TYPE_PROGRESS)
        delegatesManager.addDelegate(adapterDelegate)
    }

    fun setHasProgress(hasProgress: Boolean) {
        if (hasProgress) {
            //If diffing, post a task to it
            if (differing) {
                diffNewDataSet(listOf(ProgressItem()), false)
            } else {
                clear()
                addItem(ProgressItem())
                notifyDataSetChanged()
            }
        } else {
            // we do not need to clear list if we have already changed
            // data set or we have no ProgressItem to been cleared or differing
            if (!differing && items.size == 1 && items[0] is ProgressItem) {
                clear()
                notifyDataSetChanged()
            }
        }
    }

    fun showFooterProgress() {
        addItem(FooterProgressItem())
        notifyItemInserted(items.size)
    }

    fun hideFooterProgress() {
        if (differing) {
            return
        }
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
    fun diffNewDataSet(newData: List<Any>, detectMoves: Boolean, callback: (() -> Unit)? = null) {
        if (items === newData) {
            refreshDataSet(newData, detectMoves)
            L.throwNewErrorIfDebug(IllegalArgumentException("must set new data set"))
            return
        }
        RxJavaUtil.disposeIfNotNull(updateDispose)

        differing = true
        updateDispose = Single.just(BaseDiffCallback(items.toList(), newData))
                .map { DiffUtil.calculateDiff(it, detectMoves) }
                .compose(RxJavaUtil.iOSingleTransformer())
                .doFinally { differing = false }
                .subscribe({
                    items = newData.toMutableList()
                    it.dispatchUpdatesTo(this)
                    callback?.invoke()
                }, L::report)
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
        if (L.showLog()) {
            check(!differing)
        }
        items.add(`object`)
    }

    fun clear() {
        if (L.showLog()) {
            check(!differing)
        }
        items.clear()
    }

    fun removeItem(position: Int) {
        if (L.showLog()) {
            check(!differing)
        }
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
            val oldD = oldData.getOrNull(oldItemPosition)
            val newD = newData.getOrNull(newItemPosition)
            if (oldD != null && oldD is DiffSameItem) {
                return oldD.isSameItem(newD)
            }
            return oldD == newD
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldD = oldData.getOrNull(oldItemPosition)
            val newD = newData.getOrNull(newItemPosition)
            if (oldD != null && oldD is DiffSameItem) {
                return oldD.isSameContent(newD)
            }
            return oldD == newD
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldD = oldData.getOrNull(oldItemPosition)
            val newD = newData.getOrNull(newItemPosition)
            if (oldD != null && oldD is DiffSameItem) {
                return oldD.getChangePayload(newD)
            }
            return null
        }
    }

    companion object {

        private val VIEW_TYPE_PROGRESS = 0
    }
}
