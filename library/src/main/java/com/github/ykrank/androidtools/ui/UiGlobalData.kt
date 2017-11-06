package com.github.ykrank.androidtools.ui

/**
 * Created by ykrank on 2017/11/6.
 */
object UiGlobalData {
    lateinit var provider: UiDataProvider
    lateinit var R:RProvider

    /**
     * 初始化全局参数
     */
    fun init(uiProvider: UiDataProvider, R:RProvider) {
        this.provider = uiProvider
        this.R = R
    }
}