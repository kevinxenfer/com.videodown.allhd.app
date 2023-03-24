package com.google.gms.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.preference.PowerPreference;

import java.util.Objects;

public class OpenAds implements LifecycleObserver, android.app.Application.ActivityLifecycleCallbacks {

    @SuppressLint("StaticFieldLeak")
    public static OpenAds mAppAds;

    public static AppOpenAd appOpenAd1 = null;
    public static AppOpenAd appOpenAd2 = null;

    public static Dialog mDialog = null;
    public static final CustomApplication Application = CustomApplication.getInstance();
    public static Activity currentActivity;

    private boolean isOpenAdLoaded = false;
    private boolean isOpenAdLoaded2 = false;

    public static boolean isShowingAd = false;

    public interface OnAdClosedListener {
        public void onAdClosed();
    }

    public OpenAds() {
        if (mAppAds == null) {
            mAppAds = this;
            Application.registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        }
    }

    public void loadOpenAd() {
        final String appOpenAd = PowerPreference.getDefaultFile().getString(AdUtils.OPENAD, "123");

        if (!isOpenAdLoaded && !appOpenAd.equalsIgnoreCase("--")) {
            AppOpenAd.AppOpenAdLoadCallback loadCallback1 = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(AppOpenAd ad) {
                    appOpenAd1 = ad;
                    isOpenAdLoaded = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    AdUtils.showLog(loadAdError.toString());
                    appOpenAd1 = null;
                    isOpenAdLoaded = false;
                    loadOpenAd2();
                }
            };

            AdRequest request = getAdRequest();
            AppOpenAd.load(Application, appOpenAd, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback1);
        } else {
            loadOpenAd2();
        }
    }

    public void loadOpenAd2() {
        final String appOpenAd = PowerPreference.getDefaultFile().getString(AdUtils.OPENAD2, "123");
        if (!isOpenAdLoaded2 && !appOpenAd.equalsIgnoreCase("--")) {
            AppOpenAd.AppOpenAdLoadCallback loadCallback1 = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(AppOpenAd ad) {
                    appOpenAd2 = ad;
                    isOpenAdLoaded2 = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    AdUtils.showLog(loadAdError.toString());
                    appOpenAd2 = null;
                    isOpenAdLoaded2 = false;
                }
            };

            AdRequest request = getAdRequest();
            AppOpenAd.load(Application, appOpenAd, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback1);
        }

    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public void showOpenAd(Activity activity, OnAdClosedListener onAdClosedListener) {

        if (onAdClosedListener != null) {
            showOpenAds1(activity, onAdClosedListener);
        } else if (appOpenAd1 != null && !isShowingAd) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, false)) {
                                activity.getWindow().getDecorView().setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            }

                            appOpenAd1 = null;
                            isShowingAd = false;

                            isOpenAdLoaded = false;
                            loadOpenAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            AdUtils.showLog(adError.toString());
                            appOpenAd1 = null;
                            isShowingAd = false;

                            isOpenAdLoaded = false;
                            loadOpenAd();

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                            if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 1) {
                                PowerPreference.getDefaultFile().putBoolean(AdUtils.APP_OPEN_SHOW, true);
                            }
                        }
                    };

            appOpenAd1.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd1.show(activity);

        } else if (appOpenAd2 != null && !isShowingAd) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, false)) {
                                activity.getWindow().getDecorView().setSystemUiVisibility(
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                            }

                            appOpenAd2 = null;
                            isShowingAd = false;

                            isOpenAdLoaded2 = false;
                            loadOpenAd();

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            AdUtils.showLog(adError.toString());
                            appOpenAd2 = null;
                            isShowingAd = false;

                            isOpenAdLoaded2 = false;
                            loadOpenAd();

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                            if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 1) {
                                PowerPreference.getDefaultFile().putBoolean(AdUtils.APP_OPEN_SHOW, true);
                            }
                        }
                    };

            appOpenAd2.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd2.show(activity);
        } else {
            showOpenAds1(activity, onAdClosedListener);
        }
    }

    public void showOpenAds1(Activity activity, OnAdClosedListener onAdClosedListener) {
        final String appOpenAd = PowerPreference.getDefaultFile().getString(AdUtils.OPENAD, "123");

        if (!appOpenAd.equalsIgnoreCase("--")) {
            AppOpenAd.AppOpenAdLoadCallback loadCallback1 = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(AppOpenAd ad) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    isShowingAd = false;
                                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, false)) {
                                        activity.getWindow().getDecorView().setSystemUiVisibility(
                                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                                    }

                                    loadOpenAd();

                                    if (onAdClosedListener != null)
                                        onAdClosedListener.onAdClosed();

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    isShowingAd = false;
                                    showOpenAds2(activity, onAdClosedListener);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    isShowingAd = true;
                                    if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 1) {
                                        PowerPreference.getDefaultFile().putBoolean(AdUtils.APP_OPEN_SHOW, true);
                                    }
                                }
                            };

                    ad.setFullScreenContentCallback(fullScreenContentCallback);
                    ad.show(activity);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    AdUtils.showLog(loadAdError.toString());
                    showOpenAds2(activity, onAdClosedListener);
                }
            };

            AdRequest request = getAdRequest();
            AppOpenAd.load(Application, appOpenAd, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback1);
        } else {
            showOpenAds2(activity, onAdClosedListener);
        }
    }

    public void showOpenAds2(Activity activity, OnAdClosedListener onAdClosedListener) {
        final String appOpenAd = PowerPreference.getDefaultFile().getString(AdUtils.OPENAD2, "123");

        if (!appOpenAd.equalsIgnoreCase("--")) {
            AppOpenAd.AppOpenAdLoadCallback loadCallback1 = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(AppOpenAd ad) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    isShowingAd = false;
                                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, false)) {
                                        activity.getWindow().getDecorView().setSystemUiVisibility(
                                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                                    }

                                    loadOpenAd();

                                    if (onAdClosedListener != null)
                                        onAdClosedListener.onAdClosed();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    isShowingAd = false;
                                    showQurekaDialog(activity, onAdClosedListener);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    isShowingAd = true;
                                    if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 1) {
                                        PowerPreference.getDefaultFile().putBoolean(AdUtils.APP_OPEN_SHOW, true);
                                    }
                                }
                            };

                    ad.setFullScreenContentCallback(fullScreenContentCallback);
                    ad.show(activity);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    AdUtils.showLog(loadAdError.toString());
                    showQurekaDialog(activity, onAdClosedListener);
                }
            };

            AdRequest request = getAdRequest();
            AppOpenAd.load(Application, appOpenAd, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback1);
        } else {
            showQurekaDialog(activity, onAdClosedListener);
        }
    }


    public void showQurekaDialog(Activity activity, OnAdClosedListener onAdClosedListener) {
        if (onAdClosedListener != null)
            onAdClosedListener.onAdClosed();
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {

            if (currentActivity != null && !currentActivity.getClass().getName().equalsIgnoreCase(PowerPreference.getDefaultFile().getString(AdUtils.activitySplash)) &&
                    !currentActivity.getClass().getName().equalsIgnoreCase(PowerPreference.getDefaultFile().getString(AdUtils.activityMain))) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                } else {
                    new MainAds().showOpenAds(currentActivity, null);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }


}
