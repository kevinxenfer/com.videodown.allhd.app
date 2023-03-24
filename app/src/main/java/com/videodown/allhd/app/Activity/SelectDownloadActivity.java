package com.videodown.allhd.app.Activity;

import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.Activity.StoryDownloader.AllStoryActivity;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.AllStory.AllvideoMyDownload.AllMyDownloadedActivity;
import com.videodown.allhd.app.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class SelectDownloadActivity extends CdActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_download);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(SelectDownloadActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(SelectDownloadActivity.this, AllStoryActivity.class));
                    }
                });

            }
        });

        findViewById(R.id.downloader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(SelectDownloadActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(SelectDownloadActivity.this, AllMyDownloadedActivity.class));
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
        new MainAds().showNativeAds(this, null, null, AD_LARGE);
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