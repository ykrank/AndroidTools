package com.github.ykrank.androidtools.widget.uploadimg

class ImageDelete {


    /**
     * code : success
     */
    var code: String? = null
    /**
     * msg: "Clear file success"
     */
    var msg: String? = null

    val success:Boolean = code == "success"

    override fun toString(): String {
        return "ImageDelete(code=$code, msg=$msg)"
    }


}