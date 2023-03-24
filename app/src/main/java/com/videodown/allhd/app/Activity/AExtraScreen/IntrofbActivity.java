package com.videodown.allhd.app.Activity.AExtraScreen;

import static com.google.gms.ads.AdUtils.AD_NORMAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.Activity.StoryDownloader.FacebookSelectionActivity;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

public class IntrofbActivity extends CdActivityBase {
    AppCompatTextView txt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introfb);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_next = findViewById(R.id.txt_next);
        String CopyValue = getIntent().getStringExtra("CopyIntent");

        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MainAds().showInterAds(IntrofbActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(IntrofbActivity.this, FacebookSelectionActivity.class);
                        intent.putExtra("CopyIntent", CopyValue);
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
        new MainAds().showNativeAds(this, null, null, AD_NORMAL);
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