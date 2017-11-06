package com.github.ykrank.androidtools.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by ykrank on 2017/6/4.
 */
fun Context.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (message != null) {
        Toast.makeText(this, message, duration).show()
    }
}

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}