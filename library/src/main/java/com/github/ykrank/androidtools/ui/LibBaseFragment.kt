package com.github.ykrank.androidtools.ui

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
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
abstract class LibBaseFragment : Fragment() {
    protected var mCoordinatorLayoutAnchorDelegate: CoordinatorLayoutAnchorDelegate? = null
    protected var mRetrySnackbar: WeakReference<Snackbar>? = null
    protected var mUserVisibleHint = false
    private var pageMsgLeaved = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCoordinatorLayoutAnchorDelegate = context as CoordinatorLayoutAnchorDelegate
    }

    override fun onDetach() {
        mCoordinatorLayoutAnchorDelegate = null
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        UiGlobalData.provider?.trackAgent?.post(FragmentStartEvent(this))
        if (!pageMsgLeaved){
            L.leaveMsg(this.javaClass.simpleName)
            pageMsgLeaved = true
        }
    }

    override fun onPause() {
        UiGlobalData.provider?.trackAgent?.post(FragmentEndEvent(this))
        super.onPause()
    }

    override fun onDestroy() {
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
        pageMsgLeaved = true
        L.leaveMsg(msg)
    }
}