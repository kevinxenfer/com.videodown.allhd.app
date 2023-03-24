package com.videodown.allhd.app.AUtils.XAWebFB;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @POST("search")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> checkVideoUrl(@Body RequestBody requestBody);

    @GET("/")
    Call<ResponseBody> faceBookVideo(@Header("X-Mashape-Key") String str, @Header("Accept") String str2);

    @GET("json")
    Call<ResponseBody> getLocation();

    @GET("?__a=1")
    Call<ResponseBody> instaVideoUrl();

    @POST("search")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> searchVideoUrl(@Body RequestBody requestBody);

    @GET("/")
    Call<ResponseBody> twitterVideoUrl();

    @GET("config")
    Call<ResponseBody> vimeoVideoUrl();
}
