package com.videodown.allhd.app.ASplash;


import static com.google.gms.ads.AdUtils.AD_NORMAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.Activity.MainActivity;
import com.videodown.allhd.app.Activity.SelectionActivity;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.databinding.ActivityContinueBinding;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class ContinueActivity extends CdActivityBase {

    ActivityContinueBinding binding;

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
        Constant.showExitDialog(ContinueActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContinueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PowerPreference.getDefaultFile().getBoolean(Constant.LetsStartScreenOnOff, false))
                    startActivity(new Intent(ContinueActivity.this, StartActivity.class));
                else if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false) && !PowerPreference.getDefaultFile().getBoolean(Constant.AgeShow, false)) {
                    new MainAds().showInterAds(ContinueActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(ContinueActivity.this, AgeActivity.class));
                        }
                    });
                } else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff, false)) {
                    new MainAds().showInterAds(ContinueActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(ContinueActivity.this, MainActivity.class));
                        }
                    });
                } else {
                    new MainAds().showInterAds(ContinueActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(ContinueActivity.this, SelectionActivity.class));
                        }
                    });
                }

            }
        });

    }
}