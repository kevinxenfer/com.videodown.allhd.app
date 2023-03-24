package com.videodown.allhd.app.Activity;

import static com.google.gms.ads.AdUtils.AD_NORMAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class MainActivity extends CdActivityBase {

    AppLangSessionManager appLangSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.appLangSessionManager = new AppLangSessionManager(this);

        findViewById(R.id.img_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(MainActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this, SelectionActivity.class));
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AD_NORMAL);
        new MainAds().showBannerAds(this, null);

    }

    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinueScreenOnOff, false)
                || PowerPreference.getDefaultFile().getBoolean(Constant.LetsStartScreenOnOff, false)
                || PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false))
            new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    finish();
                }
            });
        else Constant.showExitDialog(MainActivity.this);
    }

}
