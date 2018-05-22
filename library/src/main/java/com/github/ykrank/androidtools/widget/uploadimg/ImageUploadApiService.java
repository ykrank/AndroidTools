package com.github.ykrank.androidtools.widget.uploadimg;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ImageUploadApiService {

    @Headers({
            "Accept:*/*",
            "Accept-Language:zh-CN,zh;q=0.8",
            "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"
    })
    @Multipart
    @POST("https://sm.ms/api/upload")
    Single<ImageUpload> postSmmsImage(@Part MultipartBody.Part image);

    @Headers({
            "Accept:*/*",
            "Accept-Language:zh-CN,zh;q=0.8",
            "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"
    })
    @GET
    Single<String> deldSmmsImage(@Url String url);
}
