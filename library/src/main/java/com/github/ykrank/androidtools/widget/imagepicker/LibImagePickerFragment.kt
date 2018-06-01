package com.github.ykrank.androidtools.widget.imagepicker

import android.app.Activity
import android.content.Intent
import com.github.ykrank.androidtools.ui.LibBaseFragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia


abstract class LibImagePickerFragment : LibBaseFragment() {
    private val pickImageRequestCode = PictureConfig.CHOOSE_REQUEST

    fun startPickImage(maxSelect: Int = 5, compress: Boolean = true) {
        ImagePicker.pickImage(this, pickImageRequestCode, maxSelect, compress)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            pickImageRequestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    afterPickImage(PictureSelector.obtainMultipleResult(data))
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    abstract fun afterPickImage(medias: List<LocalMedia>)
}