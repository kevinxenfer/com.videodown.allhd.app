package com.videodown.allhd.app.AllStory.AllvideoMyDownload;


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

public class AllMyDownloadedActivity extends CdActivityBase {
    RelativeLayout img_insta, img_fb, img_wtsup, img_twitter, img_sharechat, img_chingari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_my_downloaded);

        img_insta = findViewById(R.id.img_instagram);
        img_fb = findViewById(R.id.img_facebbok);
        img_wtsup = findViewById(R.id.img_whatspp);
        img_twitter = findViewById(R.id.img_twitter);
        img_sharechat = findViewById(R.id.img_sharechat);
        img_chingari = findViewById(R.id.img_chingari);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        img_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, Download_InstaActivity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        img_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, FB_downloads_Activity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        img_wtsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, WhatsApp_downloads_Activity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        img_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, Twitter_downloads_Activity.class);
                        startActivity(intent);

                    }
                });


            }
        });

        img_sharechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, Sharechat_downloades_Activity.class);
                        startActivity(intent);

                    }
                });


            }
        });


        img_chingari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(AllMyDownloadedActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(AllMyDownloadedActivity.this, Chingari_downloads_Activity.class);
                        startActivity(intent);

                    }
                });


            }
        });



    }

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
        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }
}