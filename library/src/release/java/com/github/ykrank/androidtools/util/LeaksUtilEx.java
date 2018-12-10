package com.github.ykrank.androidtools.util;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeaksUtilEx {

    public static RefWatcher install(Application application) {
        return LeakCanary.install(application);
    }
}
