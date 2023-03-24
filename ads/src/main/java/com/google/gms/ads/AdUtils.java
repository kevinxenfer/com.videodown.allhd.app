package com.google.gms.ads;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.preference.PowerPreference;

public class AdUtils {

    public static int AD_NORMAL = 1;
    public static int AD_LARGE = 2;

    public static final String activitySplash = "activitySplash";

    public static final String activityMain = "activityMain";
    public static final String activityPolicy = "activityPolicy";

    public static final String FULL_SCREEN = "FullScreenOnOff";
    public static final String APP_OPEN_SHOW = "APP_OPEN_SHOW";

    public static String DEF_VALUE = "#d7fff1";
    public static String DEF_BUTTON_VALUE = "#000000";
    public static String DEF_BUTTON_TEXT = "#FFFFFF";
    public static String DEF_TEXT = "#000000";

    public static String BANNERID = "GoogleBannerAds";
    public static String OPENAD = "GoogleAppOpenAds";
    public static String INTERID = "GoogleInterAds";
    public static String NATIVEID = "GoogleNativeAds";
    public static String NATIVE2ID = "GoogleNative2Ads";

    public static String BANNERID2 = "Google2BannerAds";
    public static String OPENAD2 = "Google2AppOpenAds";
    public static String INTERID2 = "Google2InterAds";
    public static String NATIVEID2 = "Google2NativeAds";
    public static String NATIVE2ID2 = "Google2Native2Ads";

    public static String APPID = "AppID";

    public static final String WhichOneSplashAppOpen = "WhichOneSplashAppOpen";
    public static final String WhichOneBannerNative = "WhichOneBannerNative";
    public static final String WhichOneAllNative = "WhichOneAllNative";
    public static final String WhichOneListNative = "WhichOneListNative";
    public static final String ListNativeAfterCount = "ListNativeAfterCount";
    public static final String StaticNativeCountPerPage = "StaticNativeCountPerPage";

    public static final String SERVER_INTERVAL_COUNT = "InterIntervalCount";
    public static final String APP_INTERVAL_COUNT = "APP_INTERVAL_COUNT";

    public static final String SERVER_BACK_COUNT = "BackInterIntervalCount";
    public static final String APP_BACK_COUNT = "APP_BACK_COUNT";

    public static final String AdsOnOff = "AdsOnOff";
    public static final String AppOpenTime = "AppOpenTime";

    public static final String NativeButtonTextOnOff = "NativeButtonTextOnOff";
    public static final String NativeButtonText = "NativeButtonText";

    public static final String ExitDialogNativeOnOff = "ExitDialogNativeOnOff";

    public static final String NativeBackgroundColor = "NativeBackgroundColor";
    public static final String NativeTextColor = "NativeTextColor";
    public static final String NativeButtonBackgroundColor = "NativeButtonBackgroundColor";
    public static final String NativeButtonTextColor = "NativeButtonTextColor";

    public static final String ShowDialogBeforeAds = "ShowDialogBeforeAds";
    public static final String DialogTimeInSec = "DialogTimeInSec";


    public static void showLog(String message) {
        Log.e("errorLog", message);
    }
}
