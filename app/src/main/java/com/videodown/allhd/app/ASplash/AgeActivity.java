package com.videodown.allhd.app.ASplash;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.databinding.ActivityAgeBinding;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class AgeActivity extends CdActivityBase {
    ActivityAgeBinding binding;
    boolean done = true;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done = true;
                binding.ready.setBackground(getResources().getDrawable(R.drawable.btn_select));
                binding.err.setBackground(getResources().getDrawable(R.drawable.btn_unselect));
                binding.text1.setTextColor(Color.parseColor("#FFFFFF"));
                binding.text2.setTextColor(Color.parseColor("#000000"));
            }
        });

        binding.err.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done = false;
                binding.ready.setBackground(getResources().getDrawable(R.drawable.btn_unselect));
                binding.err.setBackground(getResources().getDrawable(R.drawable.btn_select));
                binding.text1.setTextColor(Color.parseColor("#000000"));
                binding.text2.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        binding.llStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (done) {
                    new MainAds().showInterAds(AgeActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(AgeActivity.this, GenderActivity.class));
                        }
                    });
                } else {
                    Toast.makeText(AgeActivity.this, "This app only for 12+ Age", Toast.LENGTH_SHORT).show();
                }
            }
        });

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


}