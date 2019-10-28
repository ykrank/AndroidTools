package com.github.ykrank.androidtools_demo

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.github.ykrank.androidtools.AppDataProvider
import com.github.ykrank.androidtools.DefaultAppDataProvider
import com.github.ykrank.androidtools.GlobalData
import com.github.ykrank.androidtools.ui.UiGlobalData
import com.github.ykrank.androidtools.util.L

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        GlobalData.init(object : DefaultAppDataProvider() {

        })
        L.init(this)

        Stetho.initializeWithDefaults(this)
    }
}