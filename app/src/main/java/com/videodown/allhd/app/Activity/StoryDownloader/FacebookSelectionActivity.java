package com.videodown.allhd.app.Activity.StoryDownloader;

import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class FacebookSelectionActivity extends CdActivityBase {

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_selection);

        String extractLinks = AllStoryActivity.extractLinks(getIntent().getStringExtra("CopyIntent"));

        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(FacebookSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(FacebookSelectionActivity.this, FacebookActivity.class);
                        intent.putExtra("CopyIntent", extractLinks);
                        startActivity(intent);
                    }
                });
            }
        });

        findViewById(R.id.facebookbrowser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainAds().showInterAds(FacebookSelectionActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(FacebookSelectionActivity.this, FacebookViaBrowserActivity.class));
                    }
                });
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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