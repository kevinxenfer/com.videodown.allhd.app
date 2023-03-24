package com.google.gms.ads;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.preference.PowerPreference;

public class MainAds {

    public static void changeAdColor(LinearLayout adSpace) {
        if (adSpace.getChildCount() > 0 && adSpace.getChildAt(0) instanceof CardView) {
            ((CardView) adSpace.getChildAt(0)).setCardBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeBackgroundColor, AdUtils.DEF_VALUE)));
            ((TextView) ((CardView) adSpace.getChildAt(0)).getChildAt(0)).setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeTextColor, AdUtils.DEF_TEXT)));
        }
    }

    public static void changeColor(LinearLayout adView) {

        if (adView.findViewById(R.id.adBackground) != null) {
            ((CardView) adView.findViewById(R.id.adBackground)).setCardBackgroundColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeBackgroundColor, AdUtils.DEF_VALUE)));
        }

        if (adView.findViewById(R.id.ad_call_to_action) != null) {
            TextView adCalltoButton = adView.findViewById(R.id.ad_call_to_action);
            adCalltoButton.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonTextColor, AdUtils.DEF_BUTTON_TEXT)));
            adCalltoButton.getBackground().setColorFilter(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonBackgroundColor, AdUtils.DEF_BUTTON_VALUE)), PorterDuff.Mode.SRC_ATOP);
            if (PowerPreference.getDefaultFile().getBoolean(AdUtils.NativeButtonTextOnOff, false)) {
                adCalltoButton.setText(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonText, "OPEN"));
            }
        }

        if (adView.findViewById(R.id.txtAd) != null) {
            TextView txtAd = adView.findViewById(R.id.txtAd);
            txtAd.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonTextColor, AdUtils.DEF_BUTTON_TEXT)));
            txtAd.getBackground().setColorFilter(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeButtonBackgroundColor, AdUtils.DEF_BUTTON_VALUE)), PorterDuff.Mode.SRC_ATOP);
        }

        if (adView.findViewById(R.id.ad_headline) != null) {
            TextView title = adView.findViewById(R.id.ad_headline);
            title.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeTextColor, AdUtils.DEF_TEXT)));
        }

        if (adView.findViewById(R.id.ad_body) != null) {
            TextView body = adView.findViewById(R.id.ad_body);
            body.setTextColor(Color.parseColor(PowerPreference.getDefaultFile().getString(AdUtils.NativeTextColor, AdUtils.DEF_TEXT)));
        }
    }


    public void loadAds(Activity activity) {

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {

            if (PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_INTERVAL_COUNT, 0) > 0)
                new InterAds().loadInterAds(activity);

            if (PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_BACK_COUNT, 0) > 0)
                new BackInterAds().loadInterAds(activity);

            if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) > 0)
                new OpenAds().loadOpenAd();

            loadBannerAds(activity);
            loadNative2Ads(activity);
            loadNativeAds(activity);
            loadListNativeAds(activity);
        }
    }

    // INTER ADS

    public void showSplashInterAds(Activity activity, InterAds.OnAdClosedListener listener) {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false) && PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_INTERVAL_COUNT, 0) > 0) {
            PowerPreference.getDefaultFile().putInt(AdUtils.APP_INTERVAL_COUNT, 0);
            new InterAds().showInterAds(activity, listener);
        } else {
            listener.onAdClosed();
        }
    }

    public void showInterAds(Activity activity, InterAds.OnAdClosedListener listener) {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false) && PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_INTERVAL_COUNT, 0) > 0) {
            new InterAds().showInterAds(activity, listener);
        } else {
            listener.onAdClosed();
        }
    }

    public void showBackInterAds(Activity activity, BackInterAds.OnAdClosedListener listener) {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false) && PowerPreference.getDefaultFile().getInt(AdUtils.SERVER_BACK_COUNT, 0) > 0) {
            new BackInterAds().showInterAds(activity, listener);
        } else {
            listener.onAdClosed();
        }
    }


    // OPEN ADS

    public void showOpenAds(Activity activity, OpenAds.OnAdClosedListener listener) {
        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 1) {
                if (!PowerPreference.getDefaultFile().getBoolean(AdUtils.APP_OPEN_SHOW, false)) {
                    new OpenAds().showOpenAd(activity, listener);
                } else {
                    if (listener != null) {
                        listener.onAdClosed();
                    }
                }
            } else if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 2) {
                new OpenAds().showOpenAd(activity, listener);
            } else if (PowerPreference.getDefaultFile().getInt(AdUtils.AppOpenTime, 0) == 3) {
                if (listener != null) {
                    new OpenAds().showOpenAd(activity, listener);
                }
            } else {
                if (listener != null) {
                    listener.onAdClosed();
                }
            }
        } else {
            if (listener != null) {
                listener.onAdClosed();
            }
        }
    }

    // BANNER ADS

    public void loadBannerAds(Activity activity) {
        if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneBannerNative, 0) == 1) {
            new MiniNativeAds().loadNativeAds(activity);
        }
    }

    public void showBannerAds(Activity activity, LinearLayout adLayout) {

        if (adLayout == null) {
            adLayout = activity.findViewById(R.id.adFrameMini);
        }

        if (adLayout == null)
            return;

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneBannerNative, 0) == 1) {
                new MiniNativeAds().showNativeAds(activity, adLayout);
            } else if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneBannerNative, 0) == 2) {
                new MiniBannerAds().loadBannerAds(activity, adLayout);
            } else {
                adLayout.removeAllViews();
            }
        } else {
            adLayout.removeAllViews();
        }
    }

    //LARGE NATIVE ADS 2
    public void loadNative2Ads(Activity activity) {
        if (PowerPreference.getDefaultFile().getInt(AdUtils.StaticNativeCountPerPage, 0) > 1) {
            new LargeNative2Ads().loadNativeAds(activity);
        }
    }

    public void showNative2Ads(int position, Activity activity, LinearLayout adLayout, int ad_type) {

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneAllNative, 0) > 0 && PowerPreference.getDefaultFile().getInt(AdUtils.StaticNativeCountPerPage, 0) > 0) {
                if (position < PowerPreference.getDefaultFile().getInt(AdUtils.StaticNativeCountPerPage, 0)) {
                    if (position == 0) {
                        new LargeNativeAds().showNativeAds(activity, adLayout, ad_type);
                    } else
                        new LargeNative2Ads().showNativeAds(activity, adLayout, ad_type);
                } else {
                    adLayout.removeAllViews();
                    adLayout.setVisibility(View.GONE);
                }
            } else {
                adLayout.removeAllViews();
                adLayout.setVisibility(View.GONE);
            }
        } else {
            adLayout.removeAllViews();
            adLayout.setVisibility(View.GONE);
        }
    }


    // LARGE NATIVE ADS

    public void loadNativeAds(Activity activity) {
        if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneAllNative, 0) > 0) {
            new LargeNativeAds().loadNativeAds(activity);
        }
    }

    public LinearLayout getFrameLayout(Activity activity, Dialog dialog) {
        if (dialog != null) {
            return dialog.findViewById(R.id.adFrameLarge);
        } else {
            return activity.findViewById(R.id.adFrameLarge);
        }
    }

    public void showNativeAds(Activity activity, Dialog dialog, LinearLayout adLayout, int ad_type) {

        if (adLayout == null) {
            adLayout = getFrameLayout(activity, dialog);
        }

        if (adLayout == null)
            return;

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneAllNative, 0) > 0) {
                new LargeNativeAds().showNativeAds(activity, adLayout, ad_type);
            } else {
                adLayout.removeAllViews();
            }
        } else {
            adLayout.removeAllViews();
        }
    }

    // LIST NATIVE ADS

    public void loadListNativeAds(Activity activity) {
        if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneListNative, 0) > 0) {
            new ListNativeAds().loadNativeAds(activity);
        }
    }

    public void showListNativeAds(Activity activity, LinearLayout adLayout, int ad_type) {

        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.AdsOnOff, false)) {
            if (PowerPreference.getDefaultFile().getInt(AdUtils.WhichOneListNative, 0) > 0) {
                new ListNativeAds().showListNativeAds(activity, adLayout, ad_type);
            } else {
                adLayout.removeAllViews();
            }
        } else {
            adLayout.removeAllViews();
        }
    }

}
