package com.videodown.allhd.app.ASplash;


import static com.google.gms.ads.AdUtils.AD_NORMAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.Activity.SelectionActivity;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.Activity.MainActivity;
import com.videodown.allhd.app.databinding.ActivityStartBinding;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;


public class StartActivity extends CdActivityBase {
    ActivityStartBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        new MainAds().showNativeAds(this, null, null, AD_NORMAL);
        new MainAds().showBannerAds(this, null);
    }


    @Override
    public void onBackPressed() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinueScreenOnOff, false)) {
            new MainAds().showBackInterAds(this, this::finish);
        } else Constant.showExitDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MainAds().showInterAds(StartActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        if (PowerPreference.getDefaultFile().getBoolean(Constant.AgeGenderScreenOnOff, false) && !PowerPreference.getDefaultFile().getBoolean(Constant.AgeShow, false)) {
                            new MainAds().showInterAds(StartActivity.this, new InterAds.OnAdClosedListener() {
                                @Override
                                public void onAdClosed() {
                                    startActivity(new Intent(StartActivity.this, AgeActivity.class));
                                }
                            });

                        } else if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff, false)) {
                            new MainAds().showInterAds(StartActivity.this, new InterAds.OnAdClosedListener() {
                                @Override
                                public void onAdClosed() {
                                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                                }
                            });
                        } else {
                            new MainAds().showInterAds(StartActivity.this, new InterAds.OnAdClosedListener() {
                                @Override
                                public void onAdClosed() {
                                    startActivity(new Intent(StartActivity.this, SelectionActivity.class));
                                }
                            });
                        }

                    }
                });

            }
        });

        binding.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.shareTo(StartActivity.this);
            }
        });

        binding.btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.gotoRateus(StartActivity.this);
            }
        });

    }

}