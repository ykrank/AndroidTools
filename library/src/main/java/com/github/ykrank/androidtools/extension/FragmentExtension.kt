package com.github.ykrank.androidtools.extension

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.view.SupportMenuInflater
import android.support.v7.widget.Toolbar

/**
 * 不依赖Activity的独立初始化Fragment布局中的Toolbar里的menu
 */
@SuppressLint("RestrictedApi")
fun Fragment.independentMenu(toolbar: Toolbar) {
    onCreateOptionsMenu(toolbar.menu, SupportMenuInflater(context))
    toolbar.setOnMenuItemClickListener {
        onOptionsItemSelected(it)
        return@setOnMenuItemClickListener true
    }
}
