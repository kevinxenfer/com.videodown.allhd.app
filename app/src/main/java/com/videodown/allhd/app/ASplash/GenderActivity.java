package com.videodown.allhd.app.ASplash;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.Activity.MainActivity;
import com.videodown.allhd.app.Activity.SelectionActivity;
import com.videodown.allhd.app.Ads.VPN.VPNActivity;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.databinding.ActivityGenderBinding;

public class GenderActivity extends CdActivityBase {

    ActivityGenderBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);
    }

    @Override
    public void onBackPressed() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.ContinueScreenOnOff, false)
                || PowerPreference.getDefaultFile().getBoolean(Constant.LetsStartScreenOnOff, false)) {
            new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                    finish();
                }
            });

        } else Constant.showExitDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(GenderActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        PowerPreference.getDefaultFile().putBoolean(Constant.AgeShow, true);
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.NextScreenOnOff, false))
                            startActivity(new Intent(GenderActivity.this, MainActivity.class));
                        else startActivity(new Intent(GenderActivity.this, SelectionActivity.class));

                    }
                });
            }
        });

        binding.female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.female.setBackground(getResources().getDrawable(R.drawable.btn_select));
                binding.male.setBackground(getResources().getDrawable(R.drawable.btn_unselect));
                binding.tfemale.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tmale.setTextColor(Color.parseColor("#000000"));
                binding.maletic.setVisibility(View.INVISIBLE);
                binding.femaletic.setVisibility(View.VISIBLE);
            }
        });

        binding.male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.female.setBackground(getResources().getDrawable(R.drawable.btn_unselect));
                binding.male.setBackground(getResources().getDrawable(R.drawable.btn_select));
                binding.tfemale.setTextColor(Color.parseColor("#000000"));
                binding.tmale.setTextColor(Color.parseColor("#FFFFFF"));
                binding.maletic.setVisibility(View.VISIBLE);
                binding.femaletic.setVisibility(View.INVISIBLE);
            }
        });

    }
}