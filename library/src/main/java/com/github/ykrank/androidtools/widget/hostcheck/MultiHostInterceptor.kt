package com.github.ykrank.androidtools.widget.hostcheck

import com.github.ykrank.androidtools.util.L
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Self-adaption multi host
 * Created by ykrank on 2017/3/29.
 */

open class MultiHostInterceptor<T : BaseHostUrl>(private val baseHostUrl: T, private val mergeHttpUrl: (originHttpUrl: HttpUrl, baseHostUrl: T) -> HttpUrl) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val originHttpUrl = originRequest.url()

        val newHttpUrl = mergeHttpUrl.invoke(originHttpUrl, baseHostUrl)

        var newRequest = originRequest
        if (originHttpUrl !== newHttpUrl) {
            val builder = originRequest.newBuilder()
            builder.url(newHttpUrl)
            builder.header("host", newHttpUrl.host())
            newRequest = builder.build()
        }

        val response: Response = proceedRequest(chain, newRequest, {
            if (newRequest != originRequest) {
                return proceedRequest(chain, originRequest, { throw OkHttpException(it) })
            } else {
                throw OkHttpException(it)
            }
        })

        return response
    }

    @Throws(IOException::class)
    private inline fun proceedRequest(chain: Interceptor.Chain, request: Request, except: (Exception) -> Response): Response {
        return try {
            chain.proceed(request)
        } catch (e: Exception) {
            if (e is IOException) {
                //Normal exception
                throw e
            } else {
                //Route error or other
                L.leaveMsg("request:" + request)
                L.report(e)
                except.invoke(e)
            }
        }
    }
}

class OkHttpException : IOException {
    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(cause: Throwable) : super(cause)
}