package com.github.ykrank.androidtools.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import android.view.View
import com.github.ykrank.androidtools.R
import com.github.ykrank.androidtools.ui.internal.CoordinatorLayoutAnchorDelegate
import com.github.ykrank.androidtools.util.L
import com.github.ykrank.androidtools.widget.track.event.page.FragmentEndEvent
import com.github.ykrank.androidtools.widget.track.event.page.FragmentStartEvent
import java.lang.ref.WeakReference

/**
 * Created by ykrank on 2017/10/27.
 */
abstract class LibBaseFragment : androidx.fragment.app.Fragment() {
    protected var mCoordinatorLayoutAnchorDelegate: CoordinatorLayoutAnchorDelegate? = null
    protected var mRetrySnackbar: WeakReference<Snackbar>? = null
    protected var mUserVisibleHint = false

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCoordinatorLayoutAnchorDelegate = context as CoordinatorLayoutAnchorDelegate
    }

    @CallSuper
    override fun onDetach() {
        mCoordinatorLayoutAnchorDelegate = null
        super.onDetach()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.leaveMsg("${this.javaClass.simpleName} onCreate")
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        UiGlobalData.provider?.trackAgent?.post(FragmentStartEvent(this))
    }

    @CallSuper
    override fun onPause() {
        UiGlobalData.provider?.trackAgent?.post(FragmentEndEvent(this))
        super.onPause()
    }

    @CallSuper
    override fun onDestroy() {
        L.leaveMsg("${this.javaClass.simpleName} onDestroy")

        super.onDestroy()
        UiGlobalData.provider?.refWatcher?.watch(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mUserVisibleHint = isVisibleToUser

        // see http://stackoverflow.com/a/9779971
        if (isVisible && !isVisibleToUser) {
            // dismiss retry Snackbar when current Fragment hid
            // because this Snackbar is unrelated to other Fragments
            dismissRetrySnackbarIfExist()
        }
    }

    fun showRetrySnackbar(text: CharSequence, onClickListener: View.OnClickListener) {
        mCoordinatorLayoutAnchorDelegate?.let {
            val snackbar = it.showLongSnackbarIfVisible(
                    text, R.string.snackbar_action_retry, onClickListener)
            if (snackbar.isPresent) {
                mRetrySnackbar = WeakReference(snackbar.get())
            }
        }
    }

    protected fun showShortSnackbar(text: CharSequence?) {
        text?.let { mCoordinatorLayoutAnchorDelegate?.showShortSnackbar(it) }
    }

    protected fun showShortSnackbar(@StringRes resId: Int) {
        mCoordinatorLayoutAnchorDelegate?.showShortSnackbar(resId)
    }

    protected fun showShortText(@StringRes resId: Int) {
        mCoordinatorLayoutAnchorDelegate?.showToastText(getString(resId))
    }

    protected fun showLongSnackbar(@StringRes resId: Int) {
        mCoordinatorLayoutAnchorDelegate?.showLongSnackbar(resId)
    }

    protected fun dismissRetrySnackbarIfExist() {
        mRetrySnackbar?.let {
            val snackbar = it.get()
            if (snackbar != null && snackbar.isShownOrQueued) {
                snackbar.dismiss()
            }
            mRetrySnackbar = null
        }
    }

    protected fun leavePageMsg(msg:String){
        L.leaveMsg(msg)
    }
}