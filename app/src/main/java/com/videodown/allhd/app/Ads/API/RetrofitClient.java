package com.videodown.allhd.app.Ads.API;

import com.videodown.allhd.app.Ads.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private RetrofitService myApi;

    private RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.getMain())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApi = retrofit.create(RetrofitService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public RetrofitService getMyApi() {
        return myApi;
    }
}