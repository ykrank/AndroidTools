package com.github.ykrank.androidtools.util

import android.content.Context

/**
 * Created by ykrank on 2017/11/5.
 */
interface ErrorParser {

    fun parse(context: Context, throwable: Throwable): String

    fun throwNewErrorIfDebug(throwable: RuntimeException)
}