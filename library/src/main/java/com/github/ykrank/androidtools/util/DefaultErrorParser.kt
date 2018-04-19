package com.github.ykrank.androidtools.util

import android.content.Context
import android.util.Log
import com.github.ykrank.androidtools.BuildConfig

/**
 * Created by ykrank on 2017/11/5.
 */
object DefaultErrorParser : ErrorParser {
    override fun parse(context: Context, throwable: Throwable): String {
        return throwable.localizedMessage
    }

    override fun throwNewErrorIfDebug(throwable: RuntimeException) {
        if (BuildConfig.DEBUG) {
            throw throwable
        } else {
            L.report(throwable, Log.WARN)
        }
    }
}