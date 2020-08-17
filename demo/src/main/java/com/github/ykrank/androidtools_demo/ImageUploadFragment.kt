package com.github.ykrank.androidtools_demo

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.ykrank.androidtools.widget.uploadimg.ImageUploadManager
import com.github.ykrank.androidtools.widget.uploadimg.LibImageUploadFragment
import com.github.ykrank.androidtools.widget.uploadimg.SmmsImageUploadManager
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ImageUploadFragment : LibImageUploadFragment() {

    override fun provideImageUploadManager(): ImageUploadManager {
        return SmmsImageUploadManager(_okHttpClient = OkHttpClient.Builder()
                .connectTimeout(17, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build())
    }
}