package com.github.ykrank.androidtools.ui.adapter.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.ykrank.androidtools.ui.adapter.delegate.item.FooterProgressItem;
import com.github.ykrank.androidtools.R;

import java.util.List;


public final class FooterProgressAdapterDelegate extends BaseAdapterDelegate<FooterProgressItem,
        FooterProgressAdapterDelegate.FooterProgressViewHolder> {

    public FooterProgressAdapterDelegate(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected Class<FooterProgressItem> getTClass() {
        return FooterProgressItem.class;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new FooterProgressViewHolder(mLayoutInflater.inflate(R.layout.item_footer_progress,
                parent, false));
    }

    @Override
    public void onBindViewHolderData(FooterProgressItem footerProgressItem, int position, @NonNull FooterProgressViewHolder holder, @NonNull List<Object> payloads) {

    }

    static final class FooterProgressViewHolder extends RecyclerView.ViewHolder {

        public FooterProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
