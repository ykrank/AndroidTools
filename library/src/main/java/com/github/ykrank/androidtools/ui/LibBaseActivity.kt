package com.github.ykrank.androidtools.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.ykrank.androidtools.guava.Optional
import com.github.ykrank.androidtools.ui.internal.CoordinatorLayoutAnchorDelegate
import com.github.ykrank.androidtools.ui.internal.DrawerLayoutDelegate
import com.github.ykrank.androidtools.ui.internal.DrawerLayoutOp
import com.github.ykrank.androidtools.util.L
import com.github.ykrank.androidtools.widget.track.event.page.ActivityEndEvent
import com.github.ykrank.androidtools.widget.track.event.page.ActivityStartEvent
import java.lang.ref.WeakReference

/**
 * Created by ykrank on 2017/10/27.
 */
abstract class LibBaseActivity : AppCompatActivity(), CoordinatorLayoutAnchorDelegate, DrawerLayoutOp {

    private var mCoordinatorLayoutAnchorDelegate: CoordinatorLayoutAnchorDelegate? = null
    private var mDrawerLayoutDelegate: DrawerLayoutDelegate? = null
    private var mSnackbar: WeakReference<Snackbar>? = null
    protected open val mDrawerIndicatorEnabled = true

    private var pageMsgLeaved = false

    @CallSuper
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupCoordinatorLayoutAnchorDelegate()
    }

    @CallSuper
    override fun setContentView(view: View) {
        super.setContentView(view)
        setupCoordinatorLayoutAnchorDelegate()
    }

    @CallSuper
    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        setupCoordinatorLayoutAnchorDelegate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerLayoutDelegate = findDrawerLayoutDelegate()
        mDrawerLayoutDelegate?.setDrawerIndicatorEnabled(mDrawerIndicatorEnabled)
        mDrawerLayoutDelegate?.onPostCreate()
    }

    override fun onResume() {
        super.onResume()
        UiGlobalData.provider?.trackAgent?.post(ActivityStartEvent(this))
        if (!pageMsgLeaved) {
            L.leaveMsg(this.javaClass.simpleName)
            pageMsgLeaved = true
        }
    }

    override fun onPause() {
        super.onPause()
        UiGlobalData.provider?.trackAgent?.post(ActivityEndEvent(this))
    }

    override fun onDestroy() {
        mDrawerLayoutDelegate?.onDestroy()
        mDrawerLayoutDelegate = null

        super.onDestroy()
    }

    open fun findDrawerLayoutDelegate(): DrawerLayoutDelegate? {
        return null
    }

    override fun openDrawer() {
        mDrawerLayoutDelegate?.openDrawer()
    }

    private fun setupCoordinatorLayoutAnchorDelegate() {
        mCoordinatorLayoutAnchorDelegate = findCoordinatorLayoutAnchorDelegate()
    }

    open fun findCoordinatorLayoutAnchorDelegate(): CoordinatorLayoutAnchorDelegate? {
        return null
    }

    override fun setupFloatingActionButton(@DrawableRes resId: Int, onClickListener: View.OnClickListener) {
        mCoordinatorLayoutAnchorDelegate?.setupFloatingActionButton(resId, onClickListener)
    }

    fun showShortToast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun showShortToast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToastText(text: CharSequence, length: Int): Optional<Snackbar> {
        return saveSnackbarWeakReference(mCoordinatorLayoutAnchorDelegate?.showToastText(text))
    }

    override fun showShortSnackbar(@StringRes resId: Int): Optional<Snackbar> {
        return saveSnackbarWeakReference(mCoordinatorLayoutAnchorDelegate?.showShortSnackbar(resId))
    }

    override fun showShortSnackbar(text: CharSequence): Optional<Snackbar> {
        return saveSnackbarWeakReference(mCoordinatorLayoutAnchorDelegate?.showShortSnackbar(text))
    }

    override fun showLongSnackbar(@StringRes resId: Int): Optional<Snackbar> {
        return saveSnackbarWeakReference(mCoordinatorLayoutAnchorDelegate?.showLongSnackbar(resId))
    }

    override fun showLongSnackbarIfVisible(text: CharSequence, @StringRes actionResId: Int, onClickListener: View.OnClickListener): Optional<Snackbar> {
        return saveSnackbarWeakReference(mCoordinatorLayoutAnchorDelegate?.showShortSnackbar(text))
    }

    override fun dismissSnackbarIfExist() {
        mSnackbar?.let {
            val snackbar = it.get()
            if (snackbar != null && snackbar.isShownOrQueued) {
                snackbar.dismiss()
            }
            mSnackbar = null
        }
    }

    private fun saveSnackbarWeakReference(snackbar: Optional<Snackbar>?): Optional<Snackbar> {
        if (snackbar == null) {
            return Optional.absent()
        }
        if (snackbar.isPresent) {
            mSnackbar = WeakReference(snackbar.get())
        }
        return snackbar
    }

    protected fun leavePageMsg(msg: String) {
        pageMsgLeaved = true
        L.leaveMsg(msg)
    }

}