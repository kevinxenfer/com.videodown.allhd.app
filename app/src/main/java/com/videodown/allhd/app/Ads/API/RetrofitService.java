package com.videodown.allhd.app.Ads.API;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("update.php")
    Call<String> data(@Field("data") String JsonObject);

}
