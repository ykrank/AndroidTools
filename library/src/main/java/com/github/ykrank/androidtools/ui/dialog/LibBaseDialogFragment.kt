package com.github.ykrank.androidtools.ui.dialog

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import com.github.ykrank.androidtools.ui.UiGlobalData
import com.github.ykrank.androidtools.widget.track.DataTrackAgent
import com.github.ykrank.androidtools.widget.track.event.page.FragmentEndEvent
import com.github.ykrank.androidtools.widget.track.event.page.FragmentStartEvent
import java.lang.ref.WeakReference

/**
 * Created by ykrank on 2016/12/28.
 */

abstract class LibBaseDialogFragment : DialogFragment() {
    val trackAgent: DataTrackAgent = UiGlobalData.provider.trackAgent

    protected var mRetrySnackbar: WeakReference<Snackbar>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        trackAgent.post(FragmentStartEvent(this))
    }

    override fun onPause() {
        trackAgent.post(FragmentEndEvent(this))
        super.onPause()
    }
}
