package com.videodown.allhd.app.AllStory.AllvideoMyDownload;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.FileListAdapter;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.FileListClickInterface;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.Activity.StoryDownloader.FullViewActivity;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Chingari_downloads_Activity extends CdActivityBase implements FileListClickInterface {
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;
    public ArrayList<File> fileArrayList;
    FileListAdapter fileListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    File mediaPath = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Chingari");
    public RecyclerView rvFileList;
    private LinearLayout sAccessBtn;
    public SwipeRefreshLayout swiperefresh;
    public TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chingari_downloads);


        sAccessBtn = findViewById(R.id.sAccessBtn);
        rvFileList = findViewById(R.id.rv_fileList);
        swiperefresh = findViewById(R.id.swiperefresh);
        tvNoResult = findViewById(R.id.tv_NoResult);


        MyUtils.createFileFolder();


        getAllFiles();

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Chingari_downloads_Activity.this.onBackPressed();
            }
        });

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                Chingari_downloads_Activity.this.getAllFiles();
                Chingari_downloads_Activity.this.swiperefresh.setRefreshing(false);
            }
        });
        sAccessBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                StorageManager storageManager = (StorageManager) Chingari_downloads_Activity.this.getSystemService("storage");
                String whatsupFolder = Chingari_downloads_Activity.this.getWhatsupFolder();
                if (Build.VERSION.SDK_INT >= 29) {
                    intent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String replace = ((Uri) intent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
                    intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + whatsupFolder));
                } else {
                    intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                    intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + whatsupFolder));
                }
                intent.addFlags(2);
                intent.addFlags(1);
                intent.addFlags(128);
                intent.addFlags(64);
                Chingari_downloads_Activity chingari_downloads_Activity = Chingari_downloads_Activity.this;
                chingari_downloads_Activity.startActivityForResult(intent, chingari_downloads_Activity.REQUEST_ACTION_OPEN_DOCUMENT_TREE);
            }
        });
        populateGrid();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.REQUEST_ACTION_OPEN_DOCUMENT_TREE && i2 == -1) {
            Uri data = intent.getData();
            Log.e("onActivityResult: ", "" + intent.getData());
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getContentResolver().takePersistableUriPermission(data, 3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Prefences.setWATree(this, data.toString());
            populateGrid();
        }
    }

    private void populateGrid() {
//        if (!Prefences.getWATree(this).equals("")) {
        sAccessBtn.setVisibility(View.GONE);
        swiperefresh.setVisibility(View.VISIBLE);
//            return;
//        }
//        swiperefresh.setVisibility(View.GONE);
    }

    public String getWhatsupFolder() {
        mediaPath.isDirectory();
        return "Download%2FStatusSaver%2FChingari";
    }


    public void getAllFiles() {
        fileArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager = gridLayoutManager;
        rvFileList.setLayoutManager(gridLayoutManager);
        File[] listFiles = this.mediaPath.listFiles();
        if (listFiles != null) {
            fileArrayList.addAll(Arrays.asList(listFiles));
            FileListAdapter fileListAdapter2 = new FileListAdapter(this, this.fileArrayList, this);
            fileListAdapter = fileListAdapter2;
            rvFileList.setAdapter(fileListAdapter2);
            if (listFiles.length > 0) {
                tvNoResult.setVisibility(View.GONE);
                swiperefresh.setVisibility(View.VISIBLE);
                return;
            }
            swiperefresh.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            return;
        }
        swiperefresh.setVisibility(View.GONE);
        tvNoResult.setVisibility(View.VISIBLE);
    }

    public void getPosition(final int i, File file) {

        new MainAds().showInterAds(Chingari_downloads_Activity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(Chingari_downloads_Activity.this, FullViewActivity.class);
                intent.putExtra("ImageDataFile", fileArrayList);
                intent.putExtra("Position", i);
                startActivity(intent);
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