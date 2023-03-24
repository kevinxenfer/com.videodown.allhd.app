package com.videodown.allhd.app.AVideo.Videoplayer;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AVideo.VideoAdepter.AllVideoListVideoAdapter;
import com.videodown.allhd.app.AVideo.VideoModel.MediaQuery;
import com.videodown.allhd.app.AVideo.VideoModel.VideoItem;
import com.videodown.allhd.app.Activity.Other.PreferenceUtil;
import com.videodown.allhd.app.R;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllVideoListActivity extends CdActivityBase {
    AppCompatImageView img_back;
    AppCompatTextView toolbar_txt;
    private List<VideoItem> hdvideoplayer_list;
    AllVideoListVideoAdapter hdvideoplayer_mVideoAdapter;
    private MediaQuery hdvideoplayer_mediaQuery;
    public RecyclerView hdvideoplayer_recyclerView;
    private PreferenceUtil hdvideoplayer_util;
    private int hdvideoplayer_widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video_list);

        img_back = findViewById(R.id.img_back);
        toolbar_txt = findViewById(R.id.toolbar_txt);

        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        hdvideoplayer_widthPixels = displayMetrics.widthPixels;
        toolbar_txt.setText("All Videos");
        hdvideoplayer_util = PreferenceUtil.getInstance(getApplicationContext());
        ((SwipeRefreshLayout) findViewById(R.id.swipeToRefresh)).setEnabled(false);
        hdvideoplayer_mediaQuery = new MediaQuery(getApplicationContext());

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onResume() {
        super.onResume();
        loadVideoList();
        new MainAds().showBannerAds(this, null);

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadVideoList() {
        Boolean valueOf = Boolean.valueOf(this.hdvideoplayer_util.getViewType());
        int sortOrder = this.hdvideoplayer_util.getSortOrder();
        boolean listAsc = this.hdvideoplayer_util.getListAsc();
        List<VideoItem> allVideoWithName = this.hdvideoplayer_mediaQuery.getAllVideoWithName();
        this.hdvideoplayer_list = allVideoWithName;
        if (sortOrder == 0) {
            if (listAsc) {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDISPLAY_NAME().compareTo(videoItem2.getDISPLAY_NAME());
                    }
                });
            } else {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDISPLAY_NAME().compareTo(videoItem.getDISPLAY_NAME());
                    }
                });
            }
        } else if (sortOrder == 1) {
            if (listAsc) {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDATE().compareTo(videoItem2.getDATE());
                    }
                });
            } else {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDATE().compareTo(videoItem.getDATE());
                    }
                });
            }
        } else if (sortOrder == 2) {
            if (listAsc) {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return Long.valueOf(videoItem.getVideoSize()).compareTo(Long.valueOf(videoItem2.getVideoSize()));
                    }
                });
            } else {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return Long.valueOf(videoItem2.getVideoSize()).compareTo(Long.valueOf(videoItem.getVideoSize()));
                    }
                });
            }
        } else if (sortOrder == 3) {
            if (listAsc) {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem.getDURATION().compareTo(videoItem2.getDURATION());
                    }
                });
            } else {
                Collections.sort(allVideoWithName, new Comparator<VideoItem>() {
                    public int compare(VideoItem videoItem, VideoItem videoItem2) {
                        return videoItem2.getDURATION().compareTo(videoItem.getDURATION());
                    }
                });
            }
        }
        hdvideoplayer_recyclerView = findViewById(R.id.recv);

        hdvideoplayer_recyclerView.setHasFixedSize(true);
        hdvideoplayer_recyclerView.setNestedScrollingEnabled(false);


        if (valueOf.booleanValue()) {
            hdvideoplayer_mVideoAdapter = new AllVideoListVideoAdapter(this, this.hdvideoplayer_list, this.hdvideoplayer_widthPixels, true);
            hdvideoplayer_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            hdvideoplayer_recyclerView.setAdapter(this.hdvideoplayer_mVideoAdapter);
            return;
        }
        hdvideoplayer_mVideoAdapter = new AllVideoListVideoAdapter(this, this.hdvideoplayer_list, this.hdvideoplayer_widthPixels, false);

//        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (hdvideoplayer_mVideoAdapter.getItemViewType(position) == 0) return 1;
//                else return 2;
//            }
//        });
//
//        hdvideoplayer_recyclerView.setLayoutManager(manager);

        hdvideoplayer_recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        hdvideoplayer_recyclerView.setAdapter(this.hdvideoplayer_mVideoAdapter);

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