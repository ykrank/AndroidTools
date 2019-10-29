package com.github.ykrank.androidtools_demo

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.github.ykrank.androidtools.DefaultAppDataProvider
import com.github.ykrank.androidtools.GlobalData
import com.github.ykrank.androidtools.util.L

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        GlobalData.init(object : DefaultAppDataProvider() {
            override val logTag: String
                get() = "AndroidToolsDemo"
            override val debug: Boolean
                get() = BuildConfig.DEBUG
            override val buildType: String
                get() = BuildConfig.BUILD_TYPE
            override val appR: Class<out Any>
                get() = R::class.java

        })
        L.init(this)

        Stetho.initializeWithDefaults(this)
    }
}