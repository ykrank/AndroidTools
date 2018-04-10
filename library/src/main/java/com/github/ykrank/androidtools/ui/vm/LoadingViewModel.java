package com.github.ykrank.androidtools.ui.vm;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class LoadingViewModel extends BaseObservable {
    public static final int LOADING_FINISH = 0;

    /**
     * We show circular indeterminate {@link android.widget.ProgressBar}
     * for the first time.
     */
    public static final int LOADING_FIRST_TIME = 1;

    public static final int LOADING_SWIPE_REFRESH = 2;

    public static final int LOADING_PULL_UP_TO_REFRESH = 3;
    private int loading;

    public LoadingViewModel() {
    }

    private LoadingViewModel(Parcel source) {
        loading = source.readInt();
    }

    @LoadingDef
    public int getLoading() {
        return loading;
    }

    public void setLoading(@LoadingDef int loading) {
        this.loading = loading;
        notifyChange();
    }

    public boolean isSwipeRefresh() {
        return loading == LOADING_SWIPE_REFRESH;
    }

    public boolean isSwipeRefreshLayoutEnabled() {
        return loading != LOADING_FIRST_TIME && loading != LOADING_PULL_UP_TO_REFRESH;
    }

    public Boolean isLoadingFirstTime() {
        return loading == LOADING_FIRST_TIME;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOADING_FINISH,
            LOADING_FIRST_TIME,
            LOADING_SWIPE_REFRESH,
            LOADING_PULL_UP_TO_REFRESH
    })
    public @interface LoadingDef {
    }
}
