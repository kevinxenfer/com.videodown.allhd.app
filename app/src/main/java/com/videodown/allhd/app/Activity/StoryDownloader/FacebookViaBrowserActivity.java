package com.videodown.allhd.app.Activity.StoryDownloader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.XAWebFB.BrowserFragment;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.io.File;

public class FacebookViaBrowserActivity extends CdActivityBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_via_browser);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new BrowserFragment()).commit();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public static void showFileExistPopUpDialog(String fileName, String filePath, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle((CharSequence) "File Exist!");
        builder.setMessage((CharSequence) "File already exist you want to re-download it?");
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) null);
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public final void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        playFile(activity, filePath);
                        return;
                    }
                });
                alertDialog.getButton(-2).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public static void playFile(Context context, String mLocalUri) {
        try {
            context.startActivity(Intent.createChooser(playFileIntent(context, mLocalUri), "Play With"));
        } catch (Exception e) {
            Toast.makeText(context, "No App Found", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("WrongConstant")
    public static Intent playFileIntent(Context context, String mLocalUri) {
        String contain = mLocalUri;
        Uri uri = Uri.parse(mLocalUri);
        Intent intent = new Intent("android.intent.action.VIEW");
        if (contain.contains(".zip") || contain.contains(".rar")) {
            intent.setDataAndType(uri, "application/x-wav");
        } else if (contain.contains(".wav") || contain.contains(".mp3")) {
            Uri fileUri = FileProvider.getUriForFile(context, "com.mydownloader.mysocialvideos.app.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(1);
            intent.setDataAndType(fileUri, "audio/*");
        } else if (contain.contains(".gif")) {
            intent.setDataAndType(uri, "image/gif");
        } else if (contain.contains(".jpg") || contain.contains(".jpeg") || contain.contains(".png")) {
            intent.setDataAndType(uri, "image/jpeg");
        } else if (contain.contains(".txt")) {
            intent.setDataAndType(uri, "text/plain");
        } else if (contain.contains(".3gp") || contain.contains(".mpg") || contain.contains(".mpeg") || contain.contains(".mpe") || contain.contains(".mp4") || contain.contains(".avi")) {
            Uri fileUri2 = FileProvider.getUriForFile(context, "com.mydownloader.mysocialvideos.app.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(1);
            intent.setDataAndType(fileUri2, "video/mp4");
        } else {
            intent.setDataAndType(uri, "*/*");
        }
        intent.addFlags(268435456);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showBannerAds(this, null);

    }

    @Override
    public void onBackPressed() {

        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }

}