package com.github.ykrank.androidtools

import com.github.ykrank.androidtools.util.ResourceUtil

/**
 * Created by ykrank on 2017/11/5.
 */

object GlobalData {
    lateinit var provider: AppDataProvider
    var recycleViewLoadingId: Int = 0
    var recycleViewErrorId: Int = 0

    /**
     * 初始化全局参数
     */
    fun init(appProvider: AppDataProvider) {
        this.provider = appProvider
    }

    private fun initResource(appProvider: AppDataProvider) {
        val loadingId = ResourceUtil.getIdByName(appProvider.appR, "drawable", "recycleview_loading")
        val errorId = ResourceUtil.getIdByName(appProvider.appR, "drawable", "recycleview_error_symbol")
        if (loadingId != 0) {
            recycleViewLoadingId = loadingId
        }
        if (errorId != 0) {
            recycleViewErrorId = errorId
        }
    }
}
