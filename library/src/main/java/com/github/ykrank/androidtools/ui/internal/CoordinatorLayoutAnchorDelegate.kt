package com.github.ykrank.androidtools.ui.internal

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

import com.github.ykrank.androidtools.guava.Optional

/**
 * This class represents a delegate which you can use to
 * add [FloatingActionButton] or [Snackbar]
 * to [CoordinatorLayout] with Anchor.
 */
interface CoordinatorLayoutAnchorDelegate {

    fun setupFloatingActionButton(@DrawableRes resId: Int, onClickListener: View.OnClickListener)

    /**
     * Show a [Snackbar] if current [android.app.Activity] is visible,
     * otherwise show a [android.widget.Toast].
     *
     * @param text The text to show.
     * @param length Toast length
     * @return The displayed `Optional.of(snackbar)` if we use [Snackbar] to
     * show short text, otherwise the `Optional.absent()`.
     */
    fun showToastText(text: CharSequence, length: Int = Toast.LENGTH_SHORT): Optional<Snackbar>

    /**
     * Show a short [Snackbar].
     *
     * @param resId The resource id of the string resource to show for [Snackbar].
     * @return The displayed `Optional.of(snackbar)`.
     */
    fun showShortSnackbar(@StringRes resId: Int): Optional<Snackbar>

    /**
     * Show a short [Snackbar].
     *
     * @param text text The text to show.
     * @return The displayed `Optional.of(snackbar)`.
     */
    fun showShortSnackbar(text: CharSequence): Optional<Snackbar>

    /**
     * Show a long [Snackbar].
     *
     * @param resId The resource id of the string resource to show for [Snackbar].
     * @return The displayed `Optional.of(snackbar)`.
     */
    fun showLongSnackbar(@StringRes resId: Int): Optional<Snackbar>

    /**
     * Show a [Snackbar] if current [android.app.Activity] is visible.
     *
     * @param text            The text to show.
     * @param actionResId     The action string resource to display.
     * @param onClickListener Callback to be invoked when the action is clicked.
     * @return The displayed `Optional.of(snackbar)` if current [android.app.Activity]
     * is visible, otherwise the `Optional.absent()`.
     */
    fun showLongSnackbarIfVisible(text: CharSequence, @StringRes actionResId: Int, onClickListener: View.OnClickListener): Optional<Snackbar>

    /**
     * Dismiss the [Snackbar] if [CoordinatorLayout] has Snackbar.
     */
    fun dismissSnackbarIfExist()
}
