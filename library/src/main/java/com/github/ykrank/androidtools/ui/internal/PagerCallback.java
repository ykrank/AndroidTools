package com.github.ykrank.androidtools.ui.internal;

public interface PagerCallback {

    /**
     * A callback to set actual total pages
     * which used for {@link android.support.v4.view.PagerAdapter}。
     */
    void setTotalPages(int totalPages);
}
