package com.github.ykrank.androidtools

import com.github.ykrank.androidtools.util.DefaultErrorParser
import com.github.ykrank.androidtools.util.ErrorParser

/**
 * Created by ykrank on 2017/11/6.
 */
open class DefaultAppDataProvider : AppDataProvider {
    override val errorParser: ErrorParser?
        get() = DefaultErrorParser
    override val logTag: String
        get() = "AndroidToolsDefault"
    override val debug: Boolean
        get() = BuildConfig.DEBUG
    override val buildType: String
        get() = BuildConfig.BUILD_TYPE
    override val itemModelBRid: Int
        get() = BR.model
    override val recycleViewLoadingImgId: Int
        get() = R.drawable.loading
    override val recycleViewErrorImgId: Int
        get() = R.drawable.recycleview_error_symbol
    override val appR: Class<out Any>
        get() = R::class.java
}