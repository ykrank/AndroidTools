package com.github.ykrank.androidtools.util;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.github.ykrank.androidtools.GlobalData;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class LeaksUtil {

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

    /**
     * Dialog memory leaks because of Message bug
     * Must call in onDestroy() in DialogFragment because DismissListener call in onDestroyView()
     *
     * @param dialog
     */
    public static void clearDialogLeaks(@NonNull Dialog dialog) {
        dialog.setOnCancelListener(null);
        dialog.setOnDismissListener(null);
        dialog.setOnShowListener(null);
        if (dialog instanceof AlertDialog) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, null, null, null);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, null, null, null);
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, null, null, null);
        }
    }
}
