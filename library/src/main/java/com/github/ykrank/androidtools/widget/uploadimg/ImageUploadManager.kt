package com.github.ykrank.androidtools.widget.uploadimg

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ImageUploadManager(private val _okHttpClient: OkHttpClient? = null) {

    private val okHttpClient: OkHttpClient by lazy {
        _okHttpClient ?: OkHttpClient.Builder()
                .connectTimeout(17, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private val uploadApiService: ImageUploadApiService by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://sm.ms/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ImageUploadApiService::class.java)
    }

    /**
     * Force upload to sm.ms
     */
    fun forceUploadSmms(imageFile: File): Single<ImageUpload> {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile)
        val body = MultipartBody.Part.createFormData("smfile", imageFile.name, requestFile)
        return uploadApiService.postSmmsImage(body)
    }

    fun delUploadedSmms(url:String): Single<ImageDelete> {
        return uploadApiService.deldSmmsImage(url)
                .map { ImageDelete.fromHtml(it) }
    }
}