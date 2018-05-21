package com.github.ykrank.androidtools_demo

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.ykrank.androidtools.widget.uploadimg.LibImageUploadFragment
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ImageUploadFragment : LibImageUploadFragment() {

    override fun provideOkHttpClient(): OkHttpClient? {
        return OkHttpClient.Builder()
                .connectTimeout(17, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }
}