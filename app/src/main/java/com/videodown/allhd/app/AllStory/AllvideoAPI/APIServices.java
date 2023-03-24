package com.videodown.allhd.app.AllStory.AllvideoAPI;

import com.videodown.allhd.app.Activity.StoryModel.TwitterResponse;
import com.videodown.allhd.app.AllStory.story.FullDetailModel;
import com.videodown.allhd.app.AllStory.story.StoryModel;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIServices {
    @GET
    Observable<JsonObject> callResult(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @FormUrlEncoded
    @POST
    Observable<JsonObject> callSnackVideo(@Url String str, @Field("shortKey") String str2, @Field("os") String str3, @Field("sig") String str4, @Field("client_key") String str5);

    @FormUrlEncoded
    @POST
    Observable<TwitterResponse> callTwitter(@Url String str, @Field("id") String str2);

    @GET
    Observable<FullDetailModel> getFullDetailInfoApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<StoryModel> getStoriesApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);


    @GET("/{id}?__a=1")
    Call<JsonObject> GetStory(@Path("id") String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);


}
