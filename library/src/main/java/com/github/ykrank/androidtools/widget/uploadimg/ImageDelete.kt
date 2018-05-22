package com.github.ykrank.androidtools.widget.uploadimg

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.ykrank.androidtools.util.L
import org.jsoup.Jsoup

@JsonIgnoreProperties(ignoreUnknown = true)
class ImageDelete {

    /**
     * msg: "Clear file success"
     */
    var msg: String? = null

    val success: Boolean get() = msg == "File delete success." || msg == "File already deleted."

    override fun toString(): String {
        return "ImageDelete(msg=$msg)"
    }


    companion object {
        fun fromHtml(html: String): ImageDelete {
            val model = ImageDelete()
            try {
                val document = Jsoup.parse(html)
                model.msg = document.selectFirst("div.container")?.text()?.trim()
            } catch (e: Exception) {
                L.report(e)
            }

            return model
        }
    }
}