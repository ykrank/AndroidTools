package com.github.ykrank.androidtools.widget.uploadimg

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.ykrank.androidtools.util.L
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

class ImageUploadManager(private val _okHttpClient: OkHttpClient? = null) {

    private val okHttpClient: OkHttpClient by lazy {
        _okHttpClient ?: OkHttpClient.Builder().build()
    }

    val uploadApiService: ImageUploadApiService by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ImageUploadApiService::class.java)
    }

    /**
     * Force upload to sm.ms
     */
    fun forceUploadSmms(imageFile: File): Single<String> {
        val imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
        return uploadApiService.postSmmsImage(imageRequestBody)
    }

    /**
     * Use glide to compress file
     */
    fun compressFile(context: Context, imageFile: File) {
        L.d("Origin file: ${imageFile.absolutePath}")
        Glide.with(context)
                .download(imageFile)
                .apply(RequestOptions.overrideOf(300, 300))
                .into(object : SimpleTarget<File>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        L.d("Load file: ${resource.absolutePath}")
                    }

                })
    }
}