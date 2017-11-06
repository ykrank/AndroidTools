package com.github.ykrank.androidtools.ui

import com.github.ykrank.androidtools.widget.net.WifiActivityLifecycleCallbacks
import com.github.ykrank.androidtools.widget.track.DataTrackAgent
import com.squareup.leakcanary.RefWatcher

/**
 * Created by ykrank on 2017/11/6.
 */
interface UiDataProvider {
    val refWatcher: RefWatcher
    val actLifeCallback: WifiActivityLifecycleCallbacks
    val trackAgent: DataTrackAgent
}