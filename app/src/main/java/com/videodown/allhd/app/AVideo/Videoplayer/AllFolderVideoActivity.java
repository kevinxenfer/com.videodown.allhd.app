package com.videodown.allhd.app.AVideo.Videoplayer;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class AllFolderVideoActivity extends CdActivityBase {
    RelativeLayout img_allfolder, img_allvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_folder_video);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        img_allfolder = findViewById(R.id.img_allfolder);
        img_allvideo = findViewById(R.id.img_allvideo);

        img_allfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllFolderVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(AllFolderVideoActivity.this, DashActivitiy.class));
                    }
                });
            }
        });


        img_allvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllFolderVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(AllFolderVideoActivity.this, AllVideoListActivity.class));
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