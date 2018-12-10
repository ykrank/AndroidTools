package com.github.ykrank.androidtools.util;

import android.app.Application;

import com.github.ykrank.androidtools.GlobalData;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeaksUtilEx {

    public static RefWatcher install(Application application) {
        if (GlobalData.provider.getDebug()) {
            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                    //exclude InputMethodManager
                    .clazz("android.view.inputmethod.InputMethodManager")
                    .build();
            return LeakCanary.refWatcher(application)
                    .listenerServiceClass(DisplayLeakService.class)
                    .excludedRefs(excludedRefs)
                    .buildAndInstall();
        } else {
            return LeakCanary.install(application);
        }
    }
}
