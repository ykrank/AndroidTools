package com.github.ykrank.androidtools.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.ykrank.androidtools.GlobalData;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by AdminYkrank on 2016/4/20.
 * 对Log的包装
 */
public class L {
    static AtomicBoolean init = new AtomicBoolean(false);
    static boolean showLog = false;

    public static void init(@NonNull Context context) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(getLogTag())   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return showLog();
            }
        });
    }

    public static void setUser(final String id, final String name) {
        CrashReport.setUserId("id:" + id + ",name:" + name);
    }

    public static boolean showLog() {
        if (!init.get()) {
            showLog = GlobalData.provider.getDebug() || "alpha".equals(GlobalData.provider.getBuildType());
        }
        return showLog;
    }

    public static void l(String msg) {
        if (showLog()) {
            Log.i(getLogTag(), msg);
            BuglyLog.d("LogMsg", msg);
        }
    }

    public static void print(String msg) {
        if (showLog()) {
            Log.d(getLogTag(), msg);
        }
    }

    public static void print(Throwable e) {
        if (showLog() && e != null) {
            e.printStackTrace();
        }
    }

    private static String getLogTag() {
        return GlobalData.provider.getLogTag();
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void d(Throwable e) {
        Logger.d(e.getMessage());
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    public static void w(Throwable e) {
        Logger.e(e, null);
    }

    public static void e(String msg) {
        e(null, msg, null);
    }

    public static void e(Throwable e) {
        e(null, "error", e);
    }

    public static void e(String msg, Throwable tr) {
        e(null, msg, tr);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        BuglyLog.e(getLogTag() + tag, msg, tr);
        if (tr != null) {
            tr.printStackTrace();
        }
        if (showLog() && tr != null) {
            CrashReport.postCatchedException(tr);
        }
    }

    public static void report(Throwable tr) {
        report(tr, Log.WARN);
    }

    public static void report(Throwable tr, int severity) {
        CrashReport.postCatchedException(tr);
        BuglyLog.e(getLogTag(), "Report error", tr);
    }

    public static void report(String msg, Throwable tr) {
        leaveMsg(msg);
        report(tr);
    }

    public static void leaveMsg(String msg) {
        leaveMsg("MSG", msg);
    }

    public static void leaveMsg(String tag, String msg) {
        BuglyLog.i(tag, msg);
    }

    public static void leaveMsg(Throwable tr) {
        BuglyLog.e("MSG", "Error", tr);
    }

    public static void test() {
        throw new RuntimeException("Just test");
    }
}
