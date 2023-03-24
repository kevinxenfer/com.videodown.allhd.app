package com.videodown.allhd.app.AUtils;

import android.content.Context;
import android.util.Log;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientLF {
    private static CookieManager getCookies(Map<String, String> map) {
        CookieManager cookieManager = new CookieManager();
        URI create = URI.create("https://www.instagram.com/");
        for (String str : map.keySet()) {
            HttpCookie httpCookie = new HttpCookie(str, (String) map.get(str));
            httpCookie.setDomain("www.instagram.com");
            httpCookie.setPath("/");
            httpCookie.setSecure(true);
            cookieManager.getCookieStore().add(create, httpCookie);
        }
        return cookieManager;
    }

    public static Retrofit getInsta2Client(Context context) {
        if (retroClientInstaNet2App == null) {
//            HashMap<String, String> cookiesHash = (HashMap<String, String>) Prefs.getMap(INSTA_COOK);
            HashMap<String, String> cookiesHash =new HashMap<>();
            cookiesHash.put("ig_cb", "1");
            Log.e("TAG", "cookiesHash =>" + cookiesHash.toString());

            CookieManager cookieManager = getCookies(cookiesHash);
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(50000, TimeUnit.SECONDS);
            builder.readTimeout(50000, TimeUnit.SECONDS);
            builder.writeTimeout(50000, TimeUnit.SECONDS);

            builder.addInterceptor(logging);


//            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            builder.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                    .addHeader("user-agent", System.getProperty("http.agent") + "MobileApplication/Webview/Android")
                    .addHeader("accept-language", "en-US")
                    .addHeader("X-Instagram-AJAX", "8541abe490e3").build()));


            retroClientInstaNet2App = new Retrofit.Builder().baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build()).build();
        }
        return retroClientInstaNet2App;
    }

    public static Retrofit getInsta3Client() {
        if (retroClientInstaNet2AppData == null) {
//            HashMap<String, String> cookiesHash = (HashMap<String, String>) Prefs.getMap(INSTA_COOK);
            HashMap<String, String> cookiesHash = new HashMap<>();
            cookiesHash.put("ig_cb", "1");
            Log.e("TAG", "cookiesHash =>" + cookiesHash.toString());

            CookieManager cookieManager = getCookies(cookiesHash);
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(50000, TimeUnit.SECONDS);
            builder.readTimeout(50000, TimeUnit.SECONDS);
            builder.writeTimeout(50000, TimeUnit.SECONDS);

            builder.addInterceptor(logging);


//            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            builder.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                    .addHeader("user-agent", System.getProperty("http.agent") + "MobileApplication/Webview/Android")
                    .addHeader("accept-language", "en-US")
                    .addHeader("X-Instagram-AJAX", "8541abe490e3").build()));


            retroClientInstaNet2AppData = new Retrofit.Builder().baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(FACTORY)
                    .client(builder.build()).build();
        }
        return retroClientInstaNet2AppData;
    }

    public static Retrofit getProfileClient() {
        if (retroClientInstaNet2AppNEW == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(50000, TimeUnit.SECONDS);
            builder.readTimeout(50000, TimeUnit.SECONDS);
            builder.writeTimeout(50000, TimeUnit.SECONDS);

            builder.addInterceptor(logging);

            builder.addInterceptor(chain -> {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            });
            OkHttpClient httpClient = builder.build();
            retroClientInstaNet2AppNEW = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retroClientInstaNet2AppNEW;
    }

    public static Retrofit retroClientInstaNet2App = null;
    public static Retrofit retroClientInstaNet2AppNEW = null;
    public static Retrofit retroClientInstaNet2AppData = null;
}