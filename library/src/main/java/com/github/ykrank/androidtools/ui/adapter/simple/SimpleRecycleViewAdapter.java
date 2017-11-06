package com.github.ykrank.androidtools.ui.adapter.simple;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.github.ykrank.androidtools.ui.adapter.BaseRecyclerViewAdapter;
import com.github.ykrank.androidtools.ui.adapter.delegate.item.ProgressItem;
import com.github.ykrank.androidtools.ui.adapter.delegate.FooterProgressAdapterDelegate;


/**
 * Simple adapter, just one type item, or {@link ProgressItem}.
 * Layout databinding variable name should be only "model".
 * Created by ykrank on 2017/3/22.
 */

public class SimpleRecycleViewAdapter extends BaseRecyclerViewAdapter {

    public SimpleRecycleViewAdapter(@NonNull Context context, @LayoutRes int layoutRes) {
        this(context, layoutRes, null);
    }

    public SimpleRecycleViewAdapter(@NonNull Context context, @LayoutRes int layoutRes, BindViewHolderCallback bindViewHolderCallback) {
        super(context);
        addAdapterDelegate(new SimpleAdapterDelegate(context, layoutRes, bindViewHolderCallback));
        addAdapterDelegate(new FooterProgressAdapterDelegate(context));
    }


}
