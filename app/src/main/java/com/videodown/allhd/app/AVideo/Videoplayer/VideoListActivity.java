package com.videodown.allhd.app.AVideo.Videoplayer;



import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AVideo.VideoAdepter.MVideoAdapter;
import com.videodown.allhd.app.AVideo.VideoModel.MediaQuery;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoListActivity extends CdActivityBase {
    private String hdvideoplayer_id;
    private List<VideoItem> hdvideoplayer_list;
    public MVideoAdapter hdvideoplayer_mVideoAdapter;
    private MediaQuery hdvideoplayer_mediaQuery;
    public RecyclerView hdvideoplayer_recyclerView;
    private SearchView hdvideoplayer_searchView;
    private PreferenceUtil hdvideoplayer_util;
    private int hdvideoplayer_widthPixels;
    private AppCompatTextView toolbar_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);


        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        hdvideoplayer_widthPixels = displayMetrics.widthPixels;
        toolbar_txt = findViewById(R.id.toolbar_txt);
        ((AppCompatImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoListActivity.this.onBackPressed();
            }
        });
        String stringExtra = getIntent().getStringExtra("name");
        Log.e("name ", " : " + stringExtra);
        toolbar_txt.setText(stringExtra);
        hdvideoplayer_util = PreferenceUtil.getInstance(getApplicationContext());
        ((SwipeRefreshLayout) findViewById(R.id.swipeToRefresh)).setEnabled(false);
        this.hdvideoplayer_id = getIntent().getStringExtra("id");
        loadVideoList();
    }

    private void loadVideoList() {
        this.hdvideoplayer_mediaQuery = new MediaQuery(getApplicationContext());
        Boolean valueOf = Boolean.valueOf(this.hdvideoplayer_util.getViewType());
        int sortOrder = this.hdvideoplayer_util.getSortOrder();
        boolean listAsc = this.hdvideoplayer_util.getListAsc();
        List<VideoItem> allVideo = this.hdvideoplayer_mediaQuery.getAllVideo(this.hdvideoplayer_id, this.hdvideoplayer_util.getSortOrder());
        this.hdvideoplayer_list = allVideo;
        if (sortOrder == 0) {
            if (listAsc) {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDISPLAY_NAME().compareTo(videoItem2.getDISPLAY_NAME());
                    }
                });
            } else {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDISPLAY_NAME().compareTo(videoItem.getDISPLAY_NAME());
                    }
                });
            }
        } else if (sortOrder == 1) {
            if (listAsc) {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDATE().compareTo(videoItem2.getDATE());
                    }
                });
            } else {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDATE().compareTo(videoItem.getDATE());
                    }
                });
            }
        } else if (sortOrder == 2) {
            if (listAsc) {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return Long.valueOf(videoItem.getVideoSize()).compareTo(Long.valueOf(videoItem2.getVideoSize()));
                    }
                });
            } else {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return Long.valueOf(videoItem2.getVideoSize()).compareTo(Long.valueOf(videoItem.getVideoSize()));
                    }
                });
            }
        } else if (sortOrder == 3) {
            if (listAsc) {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDURATION().compareTo(videoItem2.getDURATION());
                    }
                });
            } else {
                Collections.sort(allVideo, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDURATION().compareTo(videoItem.getDURATION());
                    }
                });
            }
        }
        hdvideoplayer_recyclerView= findViewById(R.id.recv);

        hdvideoplayer_recyclerView.setHasFixedSize(true);
        if (valueOf.booleanValue()) {
            hdvideoplayer_mVideoAdapter = new MVideoAdapter(this, this.hdvideoplayer_list, this.hdvideoplayer_widthPixels, true);
            hdvideoplayer_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            hdvideoplayer_recyclerView.setAdapter(this.hdvideoplayer_mVideoAdapter);
            return;
        }
        hdvideoplayer_mVideoAdapter = new MVideoAdapter(this, this.hdvideoplayer_list, this.hdvideoplayer_widthPixels, false);
        hdvideoplayer_recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        hdvideoplayer_recyclerView.setAdapter(this.hdvideoplayer_mVideoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new MainAds().showBannerAds(this, null);

    }

    public void onBackPressed() {

        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }
}