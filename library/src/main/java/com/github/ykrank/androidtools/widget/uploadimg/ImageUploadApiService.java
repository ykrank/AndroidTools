package com.github.ykrank.androidtools.widget.uploadimg;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageUploadApiService {

    @Multipart
    @POST("https://sm.ms/api/upload")
    Single<String> postSmmsImage(@Part(value = "smfile") RequestBody image);
}
