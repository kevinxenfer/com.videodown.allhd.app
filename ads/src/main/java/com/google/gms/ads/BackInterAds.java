package com.google.gms.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.preference.PowerPreference;

import java.util.Objects;

public class BackInterAds {

    public static Activity mActivity;

    public static InterstitialAd mInterstitialAd, mInterstitialAd2;
    public static OnAdClosedListener mOnAdClosedListener;

    public Dialog mLoadingDialog;

    protected void ShowProgress(Activity activity) {
        if (mLoadingDialog == null && !activity.isFinishing()) {
            mLoadingDialog = showScreenDataLoader(activity);
        }
        if (!activity.isFinishing() && mLoadingDialog != null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    public static Dialog showScreenDataLoader(Activity mActivity) {
        Dialog d = new Dialog(mActivity, R.style.NormalDialog);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(d.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        d.setContentView(R.layout.dialog_ad_loader);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = -2;
        lp.height = -2;
        d.show();
        d.getWindow().setAttributes(lp);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, false)) {
                    mActivity.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });

        return d;
    }

    protected void HideProgress(Activity activity) {
        if (!activity.isFinishing() && mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }


    public interface OnAdClosedListener {
        public void onAdClosed();
    }

    public void loadInterAds(Activity context) {
        final String interAd = PowerPreference.getDefaultFile().getString(AdUtils.INTERID, "123");

        if (mInterstitialAd == null && !interAd.equalsIgnoreCase("--")) {
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, interAd, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    mInterstitialAd = null;
                    loadInterAds2(context);
                }
            });
        } else {
            loadInterAds2(context);
        }
    }

    public void loadInterAds2(Activity context) {
        final String interAd = PowerPreference.getDefaultFile().getString(AdUtils.INTERID2, "123");
        if (mInterstitialAd2 == null && !interAd.equalsIgnoreCase("--")) {
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, interAd, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd2 = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    mInterstitialAd2 = null;
                }
            });
        }

    }


    public void showInterAds(Activity context, OnAdClosedListener onAdClosedListener) {
        mActivity = context;
        mOnAdClosedListener = onAdClosedListener;

        int custGCount = PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_BACK_COUNT);
        int appGCount = PowerPreference.getDefaultFile().getInt(AdUtils.APP_BACK_COUNT);

        if (custGCount != 0 && appGCount % custGCount == 0) {
            appGCount++;
            PowerPreference.getDefaultFile().putInt(AdUtils.APP_BACK_COUNT, appGCount);
            watchAds(context, onAdClosedListener);
        } else {
            appGCount++;
            PowerPreference.getDefaultFile().putInt(AdUtils.APP_BACK_COUNT, appGCount);
            if (mOnAdClosedListener != null)
                mOnAdClosedListener.onAdClosed();
        }
    }


    public void watchAds(Activity context, OnAdClosedListener onAdClosedListener) {
        mActivity = context;
        mOnAdClosedListener = onAdClosedListener;
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {

                    if (mOnAdClosedListener != null) {
                        mOnAdClosedListener.onAdClosed();
                    }

                    mInterstitialAd = null;
                    loadInterAds(context);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    AdUtils.showLog(adError.toString());

                    if (mOnAdClosedListener != null)
                        mOnAdClosedListener.onAdClosed();

                    mInterstitialAd = null;
                    loadInterAds(context);

                }

                @Override
                public void onAdShowedFullScreenContent() {

                }
            });

            if (PowerPreference.getDefaultFile().getBoolean(AdUtils.ShowDialogBeforeAds, false)) {
                ShowProgress(context);
                new Handler().postDelayed(() -> {
                    HideProgress(context);
                    mInterstitialAd.show(context);
                }, (long) (PowerPreference.getDefaultFile().getDouble(AdUtils.DialogTimeInSec, 1) * 1000L));

            } else {
                mInterstitialAd.show(context);
            }

        } else if (mInterstitialAd2 != null) {

            mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {

                    if (mOnAdClosedListener != null) {
                        mOnAdClosedListener.onAdClosed();
                    }

                    mInterstitialAd2 = null;
                    loadInterAds(context);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    AdUtils.showLog(adError.toString());

                    if (mOnAdClosedListener != null)
                        mOnAdClosedListener.onAdClosed();

                    mInterstitialAd2 = null;
                    loadInterAds(mActivity);

                }

                @Override
                public void onAdShowedFullScreenContent() {

                }
            });

            if (PowerPreference.getDefaultFile().getBoolean(AdUtils.ShowDialogBeforeAds, false)) {
                ShowProgress(context);
                new Handler().postDelayed(() -> {
                    HideProgress(context);
                    mInterstitialAd2.show(context);
                }, (long) (PowerPreference.getDefaultFile().getDouble(AdUtils.DialogTimeInSec, 1) * 1000L));
            } else {
                mInterstitialAd2.show(context);
            }
        } else {
            showInterAds1(context);
        }
    }


    public void showInterAds1(Activity context) {

        final String interAd = PowerPreference.getDefaultFile().getString(AdUtils.INTERID, "123");
        if (!interAd.equalsIgnoreCase("--")) {
            AdRequest adRequest = new AdRequest.Builder().build();

            ShowProgress(context);

            InterstitialAd.load(context, interAd, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    HideProgress(context);
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            showInterAds2(context);
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (mOnAdClosedListener != null)
                                mOnAdClosedListener.onAdClosed();

                            loadInterAds(context);
                        }
                    });
                    interstitialAd.show(context);

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    showInterAds2(context);
                }
            });
        } else {
            showInterAds2(context);
        }
    }

    public void showInterAds2(Activity context) {

        final String interAd = PowerPreference.getDefaultFile().getString(AdUtils.INTERID2, "123");
        if (!interAd.equalsIgnoreCase("--")) {
            AdRequest adRequest = new AdRequest.Builder().build();

            ShowProgress(context);

            InterstitialAd.load(context, interAd, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    HideProgress(context);
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            showQureka(context);
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (mOnAdClosedListener != null)
                                mOnAdClosedListener.onAdClosed();

                            loadInterAds(context);
                        }
                    });
                    interstitialAd.show(context);

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    HideProgress(context);
                    showQureka(context);
                }
            });
        } else {
            showQureka(context);
        }
    }

    public void showQureka(Activity context) {
        if (mOnAdClosedListener != null)
            mOnAdClosedListener.onAdClosed();

        loadInterAds(context);
    }

}
