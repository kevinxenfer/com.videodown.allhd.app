package com.videodown.allhd.app.Ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.io.File;
import java.net.URLConnection;

import com.videodown.allhd.app.R;
import com.videodown.allhd.app.databinding.DialogCigRateBinding;
import com.videodown.allhd.app.databinding.ExitDailogBinding;

import pl.droidsonroids.gif.GifImageView;
import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.VpnException;

public class Constant {

    private static int qurekaCounter = 0;

    public static void gotoRateus(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static void gotoPolicyTerms(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.black));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.POLICY, "https://1064.win.qureka.com/")));
        } catch (Exception e) {
            // Constant.showLog(e.toString());
        }
    }

    public static void shareTo(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            Log.d("TAG", "share: ");
        }
    }

    public static boolean isPackageInstalled(Activity activity, String packageName) {
        try {
            activity.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public static void showRateDialog(Activity activity, boolean isAds) {

        try {

            Dialog dialog = new Dialog(activity);
            DialogCigRateBinding rateBinding = DialogCigRateBinding.inflate(activity.getLayoutInflater());
            dialog.setContentView(rateBinding.getRoot());

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                    if (isAds && PowerPreference.getDefaultFile().getBoolean(AdUtils.ExitDialogNativeOnOff, true)) {
                        new MainAds().showNativeAds(activity, dialog, null, AdUtils.AD_LARGE);
                    } else {
                        rateBinding.includedAd.adFrameLarge.setVisibility(View.GONE);
                    }
                }
            });

            rateBinding.btnExit.setOnClickListener(view -> {
                dialog.dismiss();
                Constant.stopVpn();
                new Handler().postDelayed(activity::finishAffinity, 500);
            });

            rateBinding.btnCancel.setOnClickListener(view -> {
                dialog.dismiss();
            });

            dialog.show();

        } catch (Exception e) {
            Constant.showLog(e.getMessage());
        }
    }
    public static void showLog(String message) {
        Log.e("errorLog", message);
    }

    public static void stopVpn() {
        try {
            if (PowerPreference.getDefaultFile().getBoolean(Constant.VPN_CONNECTED, true)) {
                UnifiedSdk.getInstance().getVpn().stop(TrackingConstants.GprReasons.M_UI, new CompletableCallback() {
                    @Override
                    public void complete() {
                        logout();
                    }

                    @Override
                    public void error(@NonNull VpnException e) {
                        logout();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout() {

        try {
            UnifiedSdk.getInstance().getBackend().logout(new CompletableCallback() {
                @Override
                public void complete() {
                }

                @Override
                public void error(@NonNull VpnException e) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Dialog mLoadingDialog;

    public static void showLoader(Context mActivity) {

        try {
            mLoadingDialog = new Dialog(mActivity);
            mLoadingDialog.setContentView(R.layout.dialog_loader);
            mLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismissLoader() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
    }

    public static final String ExtraScreenOnOff = "GbStatusOptionOnOff";

    public static final String whatPath = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia";
    public static final String busiPath = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia";

    public static final String whatPath3 = "Android/media/com.whatsapp/WhatsApp/Media";
    public static final String busiPath3 = "Android/media/com.whatsapp.w4b/WhatsApp Business/Media";

    public static final String wPath = "wPath";
    public static final String wbPath = "wbPath";

    public static final String OPTIONS1 = "OPTIONS1";
    public static final String OPTIONS2 = "OPTIONS2";
    public static String cPath = "cPath";

    public static final String CATEGORY = "CATEGORY";

    public static final String WHAT_OPTIONS = "WHAT_OPTIONS";
    public static final String SAVE_OPTIONS = "SAVE_OPTIONS";
    public static final String MENU_OPTIONS = "MENU_OPTIONS";

    public static final String WHAT = "WHAT";
    public static final String WBUS = "WBUS";
    public static final String AgeShow = "AgeShow";

    public static native String getMain();

    public static final String POLICY = "PolicyLink";
    public static final String CustomAdsOnOff = "CustomAdsOnOff";
    public static final String FULL_SCREEN = "FullScreenOnOff";
    public static final String PolicyOnOff = "PolicyOnOff";
    public static final String LetsStartScreenOnOff = "LetsStartScreenOnOff";
    public static final String ContinueScreenOnOff = "ContinueScreenOnOff";
    public static final String AppBackgroundColor = "AppBackgroundColor";
    public static final String OneSignalAppId = "OneSignalAppId";


    public static final String VpnDialogTime = "VpnDialogTime";

    public static final String VpnOnOff = "VpnOnOff";
    public static String Vpnaccept = "Vpnaccept";
    public static final String VpnDialog = "VpnDialog";
    public static String VPN_CONNECTED = "VPN_CONNECTED";

    public static String Default_Vpn_Country = "default_vpn_country";
    public static String Default_Country_code = "default_country_code";
    public static String Default_Vpn_img = "default_vpn_img";

    public static String VpnCountryMain = "VpnCountryMain";


    public static String mAds = "mAds";
    public static String isPolicyChecked = "isPolicyChecked";
    public static String ageGanderChecked = "ageGanderChecked";
    public static final String AgeGenderScreenOnOff = "AgeGenderScreenOnOff";
    public static final String ExtraInnerScreenOnOff = "ExtraInnerScreenOnOff";

    public static final String NextScreenOnOff = "NextScreenOnOff";


    public static void Log(String message) {
        Log.e("errorLog", message);
    }


    public static boolean checkInternet(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void gotoTerms(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.POLICY, "https://1064.win.qureka.com/")));
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }


    public static void showToast(String message) {
        Toast.makeText(Application.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isImageFile(String str) {
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        return guessContentTypeFromName != null && guessContentTypeFromName.startsWith("image");
    }

    public static boolean isVideoFile(String str) {
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        return guessContentTypeFromName != null && guessContentTypeFromName.startsWith("video");
    }

    public static void mediaScanner(Context context, String str, String str2, String str3) {
        try {
            MediaScannerConnection.scanFile(context, new String[]{str + new File(str2).getName()}, new String[]{str3}, new MediaScannerConnection.MediaScannerConnectionClient() {
                public void onMediaScannerConnected() {
                }

                public void onScanCompleted(String str, Uri uri) {
                }
            });
        } catch (Exception e) {
            Log.d("whatserror", "mediaScanner: " + e);
            e.printStackTrace();
        }
    }

//    public static void setToolBar(Activity activity, String title) {
//        ((TextView) activity.findViewById(R.id.txtTitle)).setText(title);
//
//        if (PowerPreference.getDefaultFile().getBoolean(AdUtils.QurekaOnOff,true)){
//            activity.findViewById(R.id.qurekatool).setVisibility(View.VISIBLE);
//            activity.findViewById(R.id.qurekatool).setOnClickListener(view -> {
//                gotoAds(activity);
//            });
//        }
//
//
//
//        activity.findViewById(R.id.ivBack).setOnClickListener(v -> {
//            activity.onBackPressed();
//        });
//
//    }

    public static void showExitDialog(Activity activity) {
        try {
            Dialog dialog = new Dialog(activity);
            ExitDailogBinding binding = ExitDailogBinding.inflate(activity.getLayoutInflater());
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                }
            });
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.FULL_SCREEN, true)) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                    if (PowerPreference.getDefaultFile().getBoolean(AdUtils.ExitDialogNativeOnOff, true)) {
                        new MainAds().showNativeAds(activity, dialog, binding.includedAd.adFrameLarge, AdUtils.AD_NORMAL);
                    } else {
                        binding.includedAd.adFrameLarge.setVisibility(View.GONE);
                        binding.includedAd.adSpaceLarge.setVisibility(View.GONE);
                    }
                }
            });
            binding.btnExit.setOnClickListener(view -> {
                dialog.dismiss();
                Constant.stopVpn();
                new Handler().postDelayed(activity::finishAffinity, 500);
            });
            binding.btnCancel.setOnClickListener(view -> {
                dialog.dismiss();
            });
            dialog.show();
        } catch (Exception e) {
            Constant.Log(e.getMessage());
        }
    }
}
