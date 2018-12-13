package com.github.ykrank.androidtools.ui

import android.content.SharedPreferences
import android.support.annotation.CallSuper
import android.support.v14.preference.PreferenceFragment
import com.github.ykrank.androidtools.util.L
import com.github.ykrank.androidtools.widget.track.event.page.LocalFragmentEndEvent
import com.github.ykrank.androidtools.widget.track.event.page.LocalFragmentStartEvent

/**
 * A helper class for registering/unregistering
 * [android.content.SharedPreferences.OnSharedPreferenceChangeListener].
 */
abstract class LibBasePreferenceFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var pageMsgLeaved = false

    @CallSuper
    override fun onStart() {
        super.onStart()

        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        UiGlobalData.provider?.trackAgent?.post(LocalFragmentStartEvent(this))
        if (!pageMsgLeaved){
            L.leaveMsg(this.javaClass.simpleName)
            pageMsgLeaved = true
        }
    }

    override fun onPause() {
        UiGlobalData.provider?.trackAgent?.post(LocalFragmentEndEvent(this))
        super.onPause()
    }

    protected fun leavePageMsg(msg:String){
        pageMsgLeaved = true
        L.leaveMsg(msg)
    }
}
