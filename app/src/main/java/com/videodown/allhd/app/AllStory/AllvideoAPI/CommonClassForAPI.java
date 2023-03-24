package com.videodown.allhd.app.AllStory.AllvideoAPI;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.videodown.allhd.app.Activity.StoryModel.TwitterResponse;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AllStory.story.FullDetailModel;
import com.videodown.allhd.app.AllStory.story.StoryModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class CommonClassForAPI {
    private static CommonClassForAPI CommonClassForAPI;
    private static Activity mActivity;

    public static CommonClassForAPI getInstance(Activity activity) {
        if (CommonClassForAPI == null) {
            CommonClassForAPI = new CommonClassForAPI();
        }
        mActivity = activity;
        return CommonClassForAPI;
    }

//    public void callResult(final DisposableObserver<JsonObject> disposableObserver, String str, String str2) {
//        String str3 = "";
//        if (MyUtils.isNullOrEmpty(str2)) {
//            str2 = str3;
//        }
//        if (!str.contains("reel")) {
//            str3 = "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+";
//        }
//        RestClient.getInstance(mActivity).getService().callResult(str, str2, str3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
//            public void onSubscribe(Disposable disposable) {
//            }
//
//            public void onNext(JsonObject jsonObject) {
//                disposableObserver.onNext(jsonObject);
//            }
//
//            public void onError(Throwable th) {
//                disposableObserver.onError(th);
//            }
//
//            public void onComplete() {
//                disposableObserver.onComplete();
//            }
//        });
//    }

    public void setResult(final DisposableObserver disposableObserver, String str, String str2) {
        String str3 = "";
        if (MyUtils.isNullOrEmpty(str2)) {
            str2 = str3;
        }
        if (!str.contains("reel")) {
            str3 = "Instagram 128.0.0.19.128 (Linux; Android 8.0; ANE-LX1 Build/HUAWEIANE-LX1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36";
        }
        RestClient.getInstance(mActivity).getService().callResult(str, str2, str3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(JsonObject jsonObject) {
                disposableObserver.onNext(jsonObject);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

//    public void Result(final DisposableObserver disposableObserver, String str, String str2) {
//        if (MyUtils.isNullOrEmpty(str2)) {
//            str2 = "";
//        }
//        RestClient.getInstance(mActivity).getService().callResult(str, str2, str.contains("/tv/") ? "Instagram 128.0.0.19.128 (Linux; Android 8.0; ANE-LX1 Build/HUAWEIANE-LX1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36" : "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() { // from class: video.story.ig.photo.save.insta.downloader.api.CommonAPI.2
//            @Override // io.reactivex.Observer
//            public void onSubscribe(Disposable disposable) {
//            }
//
//            public void onNext(JsonObject jsonObject) {
//                disposableObserver.onNext(jsonObject);
//            }
//
//            @Override // io.reactivex.Observer
//            public void onError(Throwable th) {
//                disposableObserver.onError(th);
//            }
//
//            @Override // io.reactivex.Observer
//            public void onComplete() {
//                disposableObserver.onComplete();
//            }
//        });
//    }


    public void callResult(final DisposableObserver disposableObserver, String str, String str2) {
        Log.w("MYRES", "str==>" + str);
//        StringRequest strReq = new StringRequest(Request.Method.GET, str, response -> {
//            try {
//                Log.w("MYRES", "response==>" + response);
//                disposableObserver.onNext(new Gson().fromJson(response, JsonObject.class));
//
//            } catch (Exception e) {
//                disposableObserver.onComplete();
//                e.printStackTrace();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                disposableObserver.onError(error);
//                Log.e("Error: ", "Error: " + error.getMessage());
//            }
//        });
//        strReq.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Application.getInstance().addToRequestQueue(strReq, "string_req");

       /* String id = str.replace("https://www.instagram.com/", "").replace("?__a=1","");
        Log.w("MYRES", "id==>" + id);

        APIServices apiService = ClientLF.getInsta3Client().create(APIServices.class);
        Call<JsonObject> call = apiService.GetStory(id,str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
                try {
                    if (response.isSuccessful()) {
                        String res = response.body().toString();
                        Log.e("MYRES", "USER REs ==>" + res);
                        disposableObserver.onNext(new Gson().fromJson(res, JsonObject.class));
                    } else {
                        Log.e("MYRES", "RES fail" + response.errorBody().string());
                    }
                } catch (Exception e2) {
                    Log.e("MYRES", "ERROR ==>" + e2.getMessage());
                    e2.printStackTrace();
                    disposableObserver.onError(e2);
                }
            }

            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable th) {
                Log.e("MYRES", "err:" + th.getMessage());
                disposableObserver.onError(th);
            }
        });*/


        if (MyUtils.isNullOrEmpty(str2)) {
            str2 = "";
        }
        Log.w("MYRES", "str2==>" + str2);
//
//        RestClient.getInstance(mActivity).getService().callResult(str, str2, str.contains("/tv/") ? "Instagram 128.0.0.19.128 (Linux; Android 8.0; ANE-LX1 Build/HUAWEIANE-LX1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36" : "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
//
//            @Override
//            public void onSubscribe(Disposable disposable) {
//            }
//
//            public void onNext(JsonObject jsonObject) {
//                disposableObserver.onNext(jsonObject);
//            }
//
//            @Override
//            public void onError(Throwable th) {
//                Log.e("FFFFFFFF", "------error----" + th.getMessage());
//                disposableObserver.onError(th);
//            }
//
//            @Override
//            public void onComplete() {
//                disposableObserver.onComplete();
//            }
//        });


//        RestClient.getInstance(mActivity).getService().callResult(str, str2,trimUserName(str) ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
//
//            @Override
//            public void onSubscribe(Disposable disposable) {
//            }
//
//            public void onNext(JsonObject jsonObject) {
//                disposableObserver.onNext(jsonObject);
//            }
//
//            @Override
//            public void onError(Throwable th) {
//                Log.e("FFFFFFFF", "------error----" + th.getMessage());
//                disposableObserver.onError(th);
//            }
//
//            @Override
//            public void onComplete() {
//                disposableObserver.onComplete();
//            }
//        });


        RestClient.getInstance(mActivity).getService().callResult(str, str2, "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(JsonObject jsonObject) {
                disposableObserver.onNext(jsonObject);
            }

            @Override
            public void onError(Throwable th) {
                Log.e("FFFFFFFF", "------error----" + th.getMessage());
                disposableObserver.onError(th);
            }

            @Override
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }





    public void callTwitterApi(final DisposableObserver disposableObserver, String str, String str2) {
        RestClient.getInstance(mActivity).getService().callTwitter(str, str2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TwitterResponse>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(TwitterResponse twitterResponse) {
                disposableObserver.onNext(twitterResponse);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getStories(final DisposableObserver disposableObserver, String str) {
        if (MyUtils.isNullOrEmpty(str)) {
            str = "";
        }
        RestClient.getInstance(mActivity).getService().getStoriesApi("https://i.instagram.com/api/v1/feed/reels_tray/", str, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StoryModel>() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(StoryModel storyModel) {
                disposableObserver.onNext(storyModel);
            }

            @Override
            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            @Override
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }




//    public void getFullDetailFeed(final DisposableObserver disposableObserver, String str, String str2) {
//        APIServices service = RestClient.getInstance(mActivity).getService();
//        service.getFullDetailInfoApi("https://i.instagram.com/api/v1/users/" + str + "/full_detail_info?max_id=", str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FullDetailModel>() {
//            @Override
//            public void onSubscribe(Disposable disposable) {
//            }
//
//            public void onNext(FullDetailModel fullDetailModel) {
//                disposableObserver.onNext(fullDetailModel);
//            }
//
//            @Override
//            public void onError(Throwable th) {
//                disposableObserver.onError(th);
//            }
//
//            @Override
//            public void onComplete() {
//                disposableObserver.onComplete();
//            }
//        });
//    }

    public void getFullDetailFeed(final DisposableObserver disposableObserver, String str, String str2) {
        APIServices service = RestClient.getInstance(mActivity).getService();
        Log.d("getFullApi", "https://i.instagram.com/api/v1/users/" + str + "/full_detail_info?max_id=");
        service.getFullDetailInfoApi("https://i.instagram.com/api/v1/users/" + str + "/full_detail_info?max_id=", str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FullDetailModel>() {
            @Override
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(FullDetailModel fullDetailModel) {
                disposableObserver.onNext(fullDetailModel);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

}
