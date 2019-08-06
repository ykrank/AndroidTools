package com.github.ykrank.androidtools.ui.internal

import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.Toast
import com.google.common.base.Optional
import com.github.ykrank.androidtools.ui.UiGlobalData
import android.R
import android.R.id
import android.widget.TextView



abstract class CoordinatorLayoutAnchorDelegateBaseImpl(private val mCoordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout) : CoordinatorLayoutAnchorDelegate {

    private val actLifeCallback = UiGlobalData.provider?.actLifeCallback

    override fun showToastText(text: CharSequence, length: Int): Optional<Snackbar> {
        if (actLifeCallback?.isAppVisible == true) {
            return showShortSnackbar(text)
        } else {
            Toast.makeText(mCoordinatorLayout.context.applicationContext, text,
                    Toast.LENGTH_SHORT).show()
            return Optional.absent()
        }
    }

    override fun showShortSnackbar(@StringRes resId: Int): Optional<Snackbar> {
        return showShortSnackbar(mCoordinatorLayout.resources.getText(resId))
    }

    override fun showShortSnackbar(text: CharSequence): Optional<Snackbar> {
        val snackbar = Snackbar.make(mCoordinatorLayout, text, Snackbar.LENGTH_SHORT)
        val textView = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = SNACK_BAR_MAX_LINE
        snackbar.show()
        return Optional.of(snackbar)
    }

    override fun showLongSnackbar(@StringRes resId: Int): Optional<Snackbar> {
        val snackbar = Snackbar.make(mCoordinatorLayout, resId, Snackbar.LENGTH_LONG)
        val textView = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.maxLines = SNACK_BAR_MAX_LINE
        snackbar.show()
        return Optional.of(snackbar)
    }

    override fun showLongSnackbarIfVisible(text: CharSequence, @StringRes actionResId: Int, onClickListener: View.OnClickListener): Optional<Snackbar> {
        if (actLifeCallback?.isAppVisible == true) {
            val snackbar = Snackbar.make(mCoordinatorLayout, text, Snackbar.LENGTH_LONG)
            val textView = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = SNACK_BAR_MAX_LINE
            snackbar.setAction(actionResId, onClickListener)
            snackbar.show()
            return Optional.of(snackbar)
        }
        return Optional.absent()
    }

    override fun dismissSnackbarIfExist() {
        throw UnsupportedOperationException()
    }

    companion object{
        const val SNACK_BAR_MAX_LINE = 5
    }
}
