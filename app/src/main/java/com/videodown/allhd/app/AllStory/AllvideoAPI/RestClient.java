package com.videodown.allhd.app.AllStory.AllvideoAPI;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static Activity mActivity;
    private static final RestClient restClient = new RestClient();
    private static Retrofit retrofit;

    public static RestClient getInstance(Activity activity) {
        mActivity = activity;
        return restClient;
    }


    private RestClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder().readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).addInterceptor(new Interceptor() {

            @Override
            public final Response intercept(Chain chain) throws IOException {
                Response response = null;
                try {
                    response = chain.proceed(chain.request());

                    Log.e("InstaRes", "-----ok-------" + response);


                    if (response.code() == 200) {
                        try {

                            JSONObject jSONObject = new JSONObject(response.body().string());
//
                            Log.e("InstaRes", "-----ok--1111-----");

                            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), String.valueOf(jSONObject))).build();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("InstaRes", "-----ok--2222-----" + response);
                    }
                } catch (SocketTimeoutException e2) {
                    e2.printStackTrace();
                }

//                Log.e("InstaRes","-----ok--333-----"+response);

                return response;
            }
        }).addInterceptor(httpLoggingInterceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://www.instagram.com/").addConverterFactory(GsonConverterFactory.create(new Gson())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(build).build();
        }
    }


    public APIServices getService() {
        return (APIServices) retrofit.create(APIServices.class);
    }
}
