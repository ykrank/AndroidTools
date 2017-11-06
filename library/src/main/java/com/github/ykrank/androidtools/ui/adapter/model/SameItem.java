package com.github.ykrank.androidtools.ui.adapter.model;

/**
 * Created by ykrank on 2016/10/27.
 */

public interface SameItem {

    /**
     * whether two object of this is same item, use in recycleView to show animate
     *
     * @return same or not
     */
    boolean isSameItem(Object other);
}
