package com.videodown.allhd.app.AllStory.AllvideoMyDownload;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videodown.allhd.app.AllStory.AllStoryAdepter.FileListAdapter;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.FullViewActivity;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.FileListClickInterface;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Mitron_downloads_Activity extends AppCompatActivity implements FileListClickInterface {
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;
    public ArrayList<File> fileArrayList;
    FileListAdapter fileListAdapter;
    ImageView img_back;
    RecyclerView.LayoutManager mLayoutManager;
    File mediaPath = new File(Environment.getExternalStorageDirectory() + "/Download/StatusSaver/Mitron");
    public RecyclerView rvFileList;
    private LinearLayout sAccessBtn;
    public SwipeRefreshLayout swiperefresh;
    public TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitron_downloads);


        this.sAccessBtn = (LinearLayout) findViewById(R.id.sAccessBtn);
        this.rvFileList = (RecyclerView) findViewById(R.id.rv_fileList);
        this.swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        this.tvNoResult = (TextView) findViewById(R.id.tv_NoResult);
        ImageView imageView = (ImageView) findViewById(R.id.img_back);
        this.img_back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Mitron_downloads_Activity.this.onBackPressed();
            }
        });
        MyUtils.createFileFolder();
        getAllFiles();
        this.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                Mitron_downloads_Activity.this.getAllFiles();
                Mitron_downloads_Activity.this.swiperefresh.setRefreshing(false);
            }
        });
        this.sAccessBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                StorageManager storageManager = (StorageManager) Mitron_downloads_Activity.this.getSystemService("storage");
                String whatsupFolder = Mitron_downloads_Activity.this.getWhatsupFolder();
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
                Mitron_downloads_Activity mitron_downloads_Activity = Mitron_downloads_Activity.this;
                mitron_downloads_Activity.startActivityForResult(intent, mitron_downloads_Activity.REQUEST_ACTION_OPEN_DOCUMENT_TREE);
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
            this.sAccessBtn.setVisibility(View.GONE);
            this.swiperefresh.setVisibility(View.VISIBLE);
//            return;
//        }
//        this.swiperefresh.setVisibility(View.GONE);
    }

    public String getWhatsupFolder() {
        this.mediaPath.isDirectory();
        return "Download%2FStatusSaver%2FMitron";
    }


    public void getAllFiles() {
        this.fileArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        this.mLayoutManager = gridLayoutManager;
        this.rvFileList.setLayoutManager(gridLayoutManager);
        File[] listFiles = this.mediaPath.listFiles();
        if (listFiles != null) {
            this.fileArrayList.addAll(Arrays.asList(listFiles));
            FileListAdapter fileListAdapter2 = new FileListAdapter(this, this.fileArrayList, this);
            this.fileListAdapter = fileListAdapter2;
            this.rvFileList.setAdapter(fileListAdapter2);
            if (listFiles.length > 0) {
                this.tvNoResult.setVisibility(View.GONE);
                this.swiperefresh.setVisibility(View.VISIBLE);
                return;
            }
            this.swiperefresh.setVisibility(View.GONE);
            this.tvNoResult.setVisibility(View.VISIBLE);
            return;
        }
        this.swiperefresh.setVisibility(View.GONE);
        this.tvNoResult.setVisibility(View.VISIBLE);
    }

    public void getPosition(final int i, File file) {

        Intent intent = new Intent(this, FullViewActivity.class);
        intent.putExtra("ImageDataFile", this.fileArrayList);
        intent.putExtra("Position", i);
        startActivity(intent);
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