package com.google.gms.ads;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.Objects;


public class MiniNativeAds {

    private static ArrayList<NativeAd> gNativeAd = new ArrayList<>();
    private static ArrayList<NativeAd> gNativeAd2 = new ArrayList<>();

    private boolean isNativeAdLoaded = false;
    private boolean isNativeAdLoaded2 = false;

    public void loadNativeAds(Activity activity) {
        final String nativeAdstr = PowerPreference.getDefaultFile().getString(AdUtils.NATIVEID, "123");
        if (!isNativeAdLoaded && !nativeAdstr.equalsIgnoreCase("--")) {

            AdLoader adLoader = new AdLoader.Builder(activity, nativeAdstr).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd2) {
                    gNativeAd.clear();
                    gNativeAd.add(nativeAd2);
                    isNativeAdLoaded = true;
                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError errorCode) {
                    AdUtils.showLog(errorCode.toString());
                    loadNativeAds2(activity);
                    isNativeAdLoaded = false;
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder()
                    .setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build())
                    .build()).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            loadNativeAds2(activity);
        }
    }

    public void loadNativeAds2(Activity activity) {
        final String nativeAdstr = PowerPreference.getDefaultFile().getString(AdUtils.NATIVEID2, "123");
        if (!isNativeAdLoaded2 && !nativeAdstr.equalsIgnoreCase("--")) {

            AdLoader adLoader = new AdLoader.Builder(activity, nativeAdstr).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd2) {
                    gNativeAd2.clear();
                    gNativeAd2.add(nativeAd2);
                    isNativeAdLoaded2 = true;
                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError errorCode) {
                    AdUtils.showLog(errorCode.toString());
                    isNativeAdLoaded2 = false;
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder()
                    .setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build())
                    .build()).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

    public void showNativeAds(Activity activity, LinearLayout adLayout) {

        MainAds.changeAdColor(adLayout);

        LinearLayout adView;

        if (gNativeAd.size() > 0) {

            adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.layout_native_tiny, null);

            MainAds.changeColor(adView);

            NativeAd lovalNative = gNativeAd.get(0);

            populateUnifiedNativeAdView(lovalNative, adView.findViewById(R.id.uadview));

            adLayout.removeAllViews();
            adLayout.addView(adView);

            isNativeAdLoaded = false;
            loadNativeAds(activity);

        } else if (gNativeAd2.size() > 0) {

            adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.layout_native_tiny, null);

            MainAds.changeColor(adView);

            NativeAd lovalNative = gNativeAd2.get(0);

            populateUnifiedNativeAdView(lovalNative, adView.findViewById(R.id.uadview));

            adLayout.removeAllViews();
            adLayout.addView(adView);

            isNativeAdLoaded2 = false;
            loadNativeAds(activity);

        } else {
            showNativeAds1(activity, adLayout);
        }
    }

    public void showNativeAds1(Activity activity, LinearLayout adLayout) {
        final String nativeAdstr = PowerPreference.getDefaultFile().getString(AdUtils.NATIVEID, "123");

        if (!nativeAdstr.equalsIgnoreCase("--")) {
            AdLoader adLoader = new AdLoader.Builder(activity, nativeAdstr).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd2) {
                    LinearLayout adView;

                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.layout_native_tiny, null);

                    MainAds.changeColor(adView);

                    populateUnifiedNativeAdView(nativeAd2, adView.findViewById(R.id.uadview));

                    adLayout.removeAllViews();
                    adLayout.addView(adView);

                    loadNativeAds(activity);
                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError errorCode) {
                    AdUtils.showLog(errorCode.toString());
                    showNativeAds2(activity, adLayout);
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder()
                    .setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build())
                    .build()).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            showNativeAds2(activity, adLayout);
        }
    }

    public void showNativeAds2(Activity activity, LinearLayout adLayout) {

        final String nativeAdstr = PowerPreference.getDefaultFile().getString(AdUtils.NATIVEID2, "123");

        if (!nativeAdstr.equalsIgnoreCase("--")) {
            AdLoader adLoader = new AdLoader.Builder(activity, nativeAdstr).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd2) {
                    LinearLayout adView;

                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.layout_native_tiny, null);

                    MainAds.changeColor(adView);

                    populateUnifiedNativeAdView(nativeAd2, adView.findViewById(R.id.uadview));

                    adLayout.removeAllViews();
                    adLayout.addView(adView);

                    loadNativeAds(activity);
                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError errorCode) {
                    AdUtils.showLog(errorCode.toString());
                    showQureka(activity, adLayout);
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder()
                    .setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build())
                    .build()).build();

            adLoader.loadAd(new AdRequest.Builder().build());

        } else {
            showQureka(activity, adLayout);
        }
    }


    public void showQureka(Activity activity, LinearLayout adLayout) {
        adLayout.removeAllViews();
        loadNativeAds(activity);
    }

    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        if (adView.findViewById(R.id.ad_media) != null) {
            MediaView mediaView = adView.findViewById(R.id.ad_media);
            mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            adView.setMediaView(mediaView);

        }
        if (adView.findViewById(R.id.ad_headline) != null)
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));

        if (adView.findViewById(R.id.ad_body) != null)
            adView.setBodyView(adView.findViewById(R.id.ad_body));

        if (adView.findViewById(R.id.ad_call_to_action) != null)
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        if (adView.findViewById(R.id.ad_app_icon) != null)
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));


        if (nativeAd.getStarRating() == null) {
            if (adView.getStarRatingView() != null)
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.GONE);
        } else {
            if (adView.getStarRatingView() != null) {
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.VISIBLE);
                ((RatingBar) adView.getStarRatingView()).setRating(Float.parseFloat(String.valueOf(nativeAd.getStarRating())));
            }
        }

        if (nativeAd.getHeadline() == null) {
            if (adView.getHeadlineView() != null)
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.GONE);
        } else {
            if (adView.getHeadlineView() != null) {
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            }
        }

        if (nativeAd.getBody() == null) {
            if (adView.getBodyView() != null)
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.GONE);
        } else {
            if (adView.getBodyView() != null) {
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        }

        if (nativeAd.getCallToAction() == null) {
            if (adView.getCallToActionView() != null)
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            if (adView.getCallToActionView() != null) {
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
                if (PowerPreference.getDefaultFile().getBoolean(AdUtils.NativeButtonTextOnOff, false)) {
                    ((TextView) adView.getCallToActionView()).setText(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonText, "OPEN"));
                } else {
                    ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                }
            }
        }

        if (nativeAd.getIcon() == null) {
            if (adView.getIconView() != null)
                Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            if (adView.getIconView() != null) {
                ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        }

        adView.setNativeAd(nativeAd);
    }
}
