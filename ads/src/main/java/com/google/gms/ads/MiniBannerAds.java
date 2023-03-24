package com.google.gms.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.preference.PowerPreference;

public class MiniBannerAds {

    private AdSize getAdSize(Activity activity, LinearLayout adLayout) {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adLayout.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }


    public void loadBannerAds(Activity activity, LinearLayout adLayout) {
        final String Ad = PowerPreference.getDefaultFile().getString(AdUtils.BANNERID, "123");
        MainAds.changeAdColor(adLayout);

        if (!Ad.equalsIgnoreCase("--")) {

            AdView adView = new AdView(activity);
            adView.setAdSize(getAdSize(activity, adLayout));
            adView.setAdUnitId(Ad);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    adLayout.removeAllViews();
                    adLayout.addView(adView);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    loadBannerAds2(activity, adLayout);
                }
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } else {
            loadBannerAds2(activity, adLayout);
        }

    }

    public void loadBannerAds2(Activity activity, LinearLayout adLayout) {

        final String Ad = PowerPreference.getDefaultFile().getString(AdUtils.BANNERID2, "123");

        if (!Ad.equalsIgnoreCase("--")) {

            AdView adView2 = new AdView(activity);
            adView2.setAdSize(getAdSize(activity, adLayout));
            adView2.setAdUnitId(Ad);

            adView2.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    adLayout.removeAllViews();
                    adLayout.addView(adView2);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdUtils.showLog(loadAdError.toString());
                    showQureka(activity, adLayout);
                }
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            adView2.loadAd(adRequest);

        } else {
            showQureka(activity, adLayout);
        }
    }

    public void showQureka(Activity activity, LinearLayout adLayout) {
        adLayout.removeAllViews();
    }
}
