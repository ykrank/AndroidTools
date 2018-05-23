package com.github.ykrank.androidtools.widget.uploadimg

import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ykrank.androidtools.databinding.FragmentUploadedImageBinding
import com.github.ykrank.androidtools.extension.toast
import com.github.ykrank.androidtools.util.L
import com.github.ykrank.androidtools.util.RxJavaUtil
import com.github.ykrank.androidtools.widget.imagepicker.LibImagePickerFragment
import com.luck.picture.lib.entity.LocalMedia
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ExecutorScheduler
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

open class LibImageUploadFragment : LibImagePickerFragment() {

    private lateinit var binding: FragmentUploadedImageBinding
    private lateinit var adapter: ImageUploadAdapter

    private lateinit var imageUploadManager: ImageUploadManager

    private val images = arrayListOf<ModelImageUpload>()
    private val modelAdd = ModelImageUploadAdd()

    //Call onCreateView
    open val imageClickListener: ((View, ModelImageUpload) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUploadedImageBinding.inflate(inflater, container, false)

        adapter = ImageUploadAdapter(this, imageClickListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        if (savedInstanceState != null) {
            images.clear()
            images.addAll(savedInstanceState.getParcelableArrayList(Extras_Upload_Images))
            images.forEach {
                //初始化下载状态
                if (it.state != ModelImageUpload.STATE_DONE) {
                    it.state = ModelImageUpload.STATE_INIT
                }
            }
        } else {
            images.clear()
        }
        refreshDataSet()

        imageUploadManager = ImageUploadManager(provideOkHttpClient())

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Extras_Upload_Images, images)
    }

    private fun refreshDataSet() {
        val dataset = mutableListOf<Any>()
        dataset.addAll(images)
        dataset.add(modelAdd)
        adapter.refreshDataSet(dataset, true)
    }

    override fun afterPickImage(medias: List<LocalMedia>) {
        medias.map { ModelImageUpload(it) }
                .apply {
                    //仅添加不同路径的图片
                    val pathSet = hashSetOf<String>()
                    images.forEach { pathSet.add(it.path) }
                    this.forEach {
                        if (!pathSet.contains(it.path)) {
                            images.add(it)
                            pathSet.add(it.path)
                        }
                    }

                    refreshDataSet()
                    uploadPickedImage()
                }
    }

    open fun provideOkHttpClient(): OkHttpClient? {
        return null
    }

    private fun uploadPickedImage() {
        Observable.fromIterable(images)
                .filter { it.state == ModelImageUpload.STATE_INIT }
                .flatMapSingle { model ->
                    model.state = ModelImageUpload.STATE_UPLOADING
                    imageUploadManager.forceUploadSmms(File(model.path))
                            .map { Pair(model, it) }
                }
                .subscribeOn(uploadScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    L.d(it.second.toString())
                    if (it.second.success) {
                        it.first.state = ModelImageUpload.STATE_DONE
                        it.first.url = it.second.data?.url
                        it.first.deleteUrl = it.second.data?.delete

                        adapter.dataSet.indexOf(it.first).also { index ->
                            if (index >= 0) {
                                adapter.notifyItemChanged(index)
                            } else {
                                //If image removed from list, remove it from server
                                delPickedImage(it.first)
                            }
                        }
                    } else {
                        it.first.state = ModelImageUpload.STATE_ERROR
                        context?.toast(it.second.code)
                        L.report(ImageUploadError("Upload image error: ${it.first}, ${it.second}"))
                    }
                }, L::report)

    }

    @MainThread
    fun delPickedImage(model: ModelImageUpload?) {
        if (model != null) {
            val deleteUrl = model.deleteUrl
            if (deleteUrl == null) {
                removeUploadedImage(model)
            } else {
                imageUploadManager.delUploadedSmms(deleteUrl)
                        .compose(RxJavaUtil.iOSingleTransformer())
                        .doAfterTerminate {
                            RxJavaUtil.workInMainThread {
                                removeUploadedImage(model)
                            }
                        }
                        .subscribe({
                            context?.toast(it.msg)
                            if (!it.success) {
                                L.report(ImageUploadError("Delete image error: $model, $it"))
                            }
                        }, L::report)
            }
        }
    }

    @MainThread
    private fun removeUploadedImage(model: ModelImageUpload) {
        images.remove(model)
        adapter.dataSet.indexOf(model).also {
            if (it >= 0) {
                adapter.removeItem(it)
                adapter.notifyItemRemoved(it)
            }
        }
    }

    companion object {
        val TAG = LibImageUploadFragment::class.java.name

        val Extras_Upload_Images = "extras_upload_images"

        val uploadExecutor = ThreadPoolExecutor(1, 3, 1, TimeUnit.SECONDS, LinkedBlockingDeque(32))
        val uploadScheduler = ExecutorScheduler(uploadExecutor)
    }
}