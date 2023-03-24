package com.videodown.allhd.app.Activity.StoryFragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.videodown.allhd.app.Activity.StoryModel.StatusModel;
import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.RecentAdapter;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AUtils.SharedPrefs;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RecentWappBus extends CdActivityBase implements RecentAdapter.OnCheckboxListener {
    LinearLayout actionLay;
    loadDataAsync async;
    LinearLayout deleteIV;
    LinearLayout downloadIV;
    RelativeLayout emptyLay;
    GridView imageGrid;
    RelativeLayout loaderLay;
    RecentAdapter myAdapter;
    LinearLayout sAccessBtn;
    CheckBox selectAll;
    SwipeRefreshLayout swipeToRefresh;
    ArrayList<StatusModel> f = new ArrayList<>();
    ArrayList<StatusModel> filesToDelete = new ArrayList<>();
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_whatsappbusiness);



        loaderLay = (RelativeLayout) findViewById(R.id.loaderLay);
        emptyLay = (RelativeLayout) findViewById(R.id.emptyLay);
        imageGrid = (GridView) findViewById(R.id.WorkImageGrid);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeToRefresh = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public final void onRefresh() {
                m1792x9ac105ef();
            }
        });
        actionLay = (LinearLayout) findViewById(R.id.actionLay);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.deleteIV);
        deleteIV = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1794xb6d959ad(view);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.downloadIV);
        downloadIV = linearLayout2;
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1795xc4e5838c(view);
            }
        });
        CheckBox checkBox = (CheckBox) findViewById(R.id.selectAll);
        selectAll = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                m1796xd2f1ad6b(compoundButton, z);
            }
        });
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.sAccessBtn);
        sAccessBtn = linearLayout3;
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1797xe0fdd74a(view);
            }
        });
        if (!SharedPrefs.getWBTree(RecentWappBus.this).equals("")) {
            populateGrid();
        }

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void m1792x9ac105ef() {
        if (!SharedPrefs.getWBTree(RecentWappBus.this).equals("")) {
            Iterator<StatusModel> it = filesToDelete.iterator();
            while (it.hasNext()) {
                ArrayList<StatusModel> arrayList = f;
                it.next().selected = false;
                arrayList.contains(false);
            }
            RecentAdapter recentAdapter = myAdapter;
            if (recentAdapter != null) {
                recentAdapter.notifyDataSetChanged();
            }
            filesToDelete.clear();
            selectAll.setChecked(false);
            actionLay.setVisibility(View.GONE);
            populateGrid();
        }
        swipeToRefresh.setRefreshing(false);
    }

    public void m1794xb6d959ad(View view) {
        if (!filesToDelete.isEmpty()) {
            new AlertDialog.Builder(RecentWappBus.this).setMessage(getResources().getString(R.string.delete_alert)).setCancelable(true).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() { // from class: superfast.fastdownloader.free.video.downloader.allvideodownloader.AppContent.Downloader.Fragment.RecentWappBus$$ExternalSyntheticLambda0
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    m1793xa8cd2fce(dialogInterface, i);
                }
            }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }

    public void m1793xa8cd2fce(DialogInterface dialogInterface, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<StatusModel> it = filesToDelete.iterator();
        char c = '\uffff';
        while (it.hasNext()) {
            StatusModel next = it.next();
            DocumentFile fromSingleUri = DocumentFile.fromSingleUri(RecentWappBus.this, Uri.parse(next.getFilePath()));
            if (!fromSingleUri.exists() || !fromSingleUri.delete()) {
                c = 0;
            } else {
                arrayList.add(next);
                if (c != 0) {
                    c = 1;
                } else {
                    return;
                }
            }
        }
        filesToDelete.clear();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            f.remove((StatusModel) it2.next());
        }
        myAdapter.notifyDataSetChanged();
        if (c == 0) {
            Toast.makeText(RecentWappBus.this, getResources().getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
        } else if (c == 1) {
            Toast.makeText(RecentWappBus.this, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        }
        actionLay.setVisibility(View.GONE);
        selectAll.setChecked(false);
    }


    public void m1795xc4e5838c(View view) {
        if (!filesToDelete.isEmpty()) {
            char c = '\uffff';
            ArrayList arrayList = new ArrayList();
            Iterator<StatusModel> it = filesToDelete.iterator();
            while (it.hasNext()) {
                StatusModel next = it.next();
                if (!DocumentFile.fromSingleUri(RecentWappBus.this, Uri.parse(next.getFilePath())).exists() || !MyUtils.download(RecentWappBus.this, next.getFilePath())) {
                    c = 0;
                } else {
                    arrayList.add(next);
                    if (c != 0) {
                        c = 1;
                    } else {
                        return;
                    }
                }
            }
            filesToDelete.clear();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ArrayList<StatusModel> arrayList2 = f;
                ((StatusModel) it2.next()).selected = false;
                arrayList2.contains(false);
            }
            myAdapter.notifyDataSetChanged();
            if (c == 0) {
                Toast.makeText(RecentWappBus.this, getResources().getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            } else if (c == 1) {
                Toast.makeText(RecentWappBus.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            }
            actionLay.setVisibility(View.GONE);
        }
    }


    public void m1796xd2f1ad6b(CompoundButton compoundButton, boolean z) {
        if (compoundButton.isPressed()) {
            filesToDelete.clear();
            int i = 0;
            while (true) {
                if (i >= f.size()) {
                    break;
                } else if (!f.get(i).selected) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                for (int i2 = 0; i2 < f.size(); i2++) {
                    f.get(i2).selected = true;
                    filesToDelete.add(f.get(i2));
                }
                selectAll.setChecked(true);
            } else {
                for (int i3 = 0; i3 < f.size(); i3++) {
                    f.get(i3).selected = false;
                }
                actionLay.setVisibility(View.GONE);
            }
            myAdapter.notifyDataSetChanged();
        }
    }


    @SuppressLint("WrongConstant")
    public void m1797xe0fdd74a(View view) {
        Intent intent;
        if (MyUtils.appInstalledOrNot(RecentWappBus.this, "com.whatsapp.w4b")) {
            StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
            String whatsupFolder = getWhatsupFolder();
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
            startActivityForResult(intent, REQUEST_ACTION_OPEN_DOCUMENT_TREE);
            return;
        }
        Toast.makeText(RecentWappBus.this, "Please Install WhatsApp Business For Download Status!!!!!", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        RecentAdapter recentAdapter = myAdapter;
        if (recentAdapter != null) {
            recentAdapter.onActivityResult(i, i2, intent);
        }
        if (i == 10 && i2 == 10) {
            f = new ArrayList<>();
            DocumentFile[] fromSdcard = getFromSdcard();
            for (int i3 = 0; i3 < fromSdcard.length - 1; i3++) {
                if (!fromSdcard[i3].getUri().toString().contains(".nomedia")) {
                    f.add(new StatusModel(fromSdcard[i3].getUri().toString()));
                }
            }
            RecentAdapter recentAdapter2 = new RecentAdapter(RecentWappBus.this, f, this);
            myAdapter = recentAdapter2;
            imageGrid.setAdapter((ListAdapter) recentAdapter2);
            actionLay.setVisibility(View.GONE);
            selectAll.setChecked(false);
        }
        if (i == REQUEST_ACTION_OPEN_DOCUMENT_TREE && i2 == -1) {
            Uri data = intent.getData();
//            Log.e("onActivityResult: ", "" + intent.getData());
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getContentResolver().takePersistableUriPermission(data, 3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPrefs.setWBTree(RecentWappBus.this, data.toString());
            populateGrid();
        }
    }

    public void populateGrid() {
        loadDataAsync loaddataasync = new loadDataAsync();
        async = loaddataasync;
        loaddataasync.execute(new Void[0]);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadDataAsync loaddataasync = async;
        if (loaddataasync != null) {
            loaddataasync.cancel(true);
        }
    }


    public class loadDataAsync extends AsyncTask<Void, Void, Void> {
        DocumentFile[] allFiles;

        loadDataAsync() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loaderLay.setVisibility(View.VISIBLE);
            imageGrid.setVisibility(View.GONE);
            sAccessBtn.setVisibility(View.GONE);
            emptyLay.setVisibility(View.GONE);
        }

        public Void doInBackground(Void... voidArr) {
            allFiles = null;
            f = new ArrayList<>();
            DocumentFile[] fromSdcard = getFromSdcard();
            allFiles = fromSdcard;
            Arrays.sort(fromSdcard, LoadDataWhatsappBusiness.INSTANCE);
            int i = 0;
            while (true) {
                DocumentFile[] documentFileArr = allFiles;
                if (i >= documentFileArr.length - 1) {
                    return null;
                }
                if (!documentFileArr[i].getUri().toString().contains(".nomedia")) {
                    f.add(new StatusModel(allFiles[i].getUri().toString()));
                }
                i++;
            }
        }

        public void onPostExecute(Void r4) {
            super.onPostExecute(r4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    loadDataAsync.this.m1798x166d165a();
                }
            }, 1000L);
        }

        public void m1798x166d165a() {

            if (RecentWappBus.this != null) {
                if (!(f == null || f.size() == 0)) {
                    RecentWappBus recentWappBus = RecentWappBus.this;
                    RecentWappBus recentWappBus2 = RecentWappBus.this;
                    recentWappBus.myAdapter = new RecentAdapter(RecentWappBus.this, recentWappBus2.f, RecentWappBus.this);
                    imageGrid.setAdapter((ListAdapter) myAdapter);
                    imageGrid.setVisibility(View.VISIBLE);
                }
                loaderLay.setVisibility(View.GONE);
            }
            if (f == null || f.size() == 0) {
                emptyLay.setVisibility(View.VISIBLE);
            } else {
                emptyLay.setVisibility(View.GONE);
            }

        }
    }

    public DocumentFile[] getFromSdcard() {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getApplicationContext(), Uri.parse(SharedPrefs.getWBTree(RecentWappBus.this)));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    public String getWhatsupFolder() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("Android/media/com.whatsapp.w4b/WhatsApp Business");
        sb.append(File.separator);
        sb.append("Media");
        sb.append(File.separator);
        sb.append(".Statuses");
        return new File(sb.toString()).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses" : "WhatsApp Business%2FMedia%2F.Statuses";
    }

    @Override
    public void onCheckboxListener(View view, List<StatusModel> list) {
        filesToDelete.clear();
        for (StatusModel statusModel : list) {
            if (statusModel.isSelected()) {
                filesToDelete.add(statusModel);
            }
        }
        if (filesToDelete.size() == f.size()) {
            selectAll.setChecked(true);
        }
        if (!filesToDelete.isEmpty()) {
            actionLay.setVisibility(View.VISIBLE);
            return;
        }
        selectAll.setChecked(false);
        actionLay.setVisibility(View.GONE);
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
}
