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

public class RecentWapp extends CdActivityBase implements RecentAdapter.OnCheckboxListener {
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
    ArrayList<StatusModel> statusModelArrayList = new ArrayList<>();
    ArrayList<StatusModel> filesToDelete = new ArrayList<>();
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_fragment);


        loaderLay = (RelativeLayout) findViewById(R.id.loaderLay);
        emptyLay = (RelativeLayout) findViewById(R.id.emptyLay);
        imageGrid = (GridView) findViewById(R.id.WorkImageGrid);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeToRefresh = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public final void onRefresh() {
                m1785x536fc2d1();
            }
        });
        actionLay = (LinearLayout) findViewById(R.id.actionLay);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.deleteIV);
        deleteIV = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1787x158837d3(view);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.downloadIV);
        downloadIV = linearLayout2;
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1788xf6947254(view);
            }
        });
        CheckBox checkBox = (CheckBox) findViewById(R.id.selectAll);
        selectAll = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                m1789xd7a0acd5(compoundButton, z);
            }
        });
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.sAccessBtn);
        sAccessBtn = linearLayout3;
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                m1790xb8ace756(view);
            }
        });
        String vwff = SharedPrefs.getWATree(RecentWapp.this);
//        Log.e("MyNjwhfu", "---1111----->" + vwff + "<");
        if (!vwff.equals("")) {
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

    public void m1785x536fc2d1() {
        if (!SharedPrefs.getWATree(RecentWapp.this).equals("")) {
            Iterator<StatusModel> it = filesToDelete.iterator();
            while (it.hasNext()) {
                ArrayList<StatusModel> arrayList = statusModelArrayList;
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


    public void m1787x158837d3(View view) {
        if (!filesToDelete.isEmpty()) {
            new AlertDialog.Builder(RecentWapp.this).setMessage(getResources().getString(R.string.delete_alert)).setCancelable(true).setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() { // from class: superfast.fastdownloader.free.video.downloader.allvideodownloader.AppContent.Downloader.Fragment.RecentWapp$$ExternalSyntheticLambda0
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    m1786x347bfd52(dialogInterface, i);
                }
            }).setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }


    public void m1786x347bfd52(DialogInterface dialogInterface, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<StatusModel> it = filesToDelete.iterator();
        char c = '\uffff';
        while (it.hasNext()) {
            StatusModel next = it.next();
            DocumentFile fromSingleUri = DocumentFile.fromSingleUri(RecentWapp.this, Uri.parse(next.getFilePath()));
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
            statusModelArrayList.remove((StatusModel) it2.next());
        }
        myAdapter.notifyDataSetChanged();
        if (c == 0) {
            Toast.makeText(RecentWapp.this, getResources().getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
        } else if (c == 1) {
            Toast.makeText(RecentWapp.this, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        }
        actionLay.setVisibility(View.GONE);
        selectAll.setChecked(false);
    }


    public void m1788xf6947254(View view) {
        if (!filesToDelete.isEmpty()) {
            char c = '\uffff';
            ArrayList arrayList = new ArrayList();
            Iterator<StatusModel> it = filesToDelete.iterator();
            while (it.hasNext()) {
                StatusModel next = it.next();
                if (DocumentFile.fromSingleUri(RecentWapp.this, Uri.parse(next.getFilePath())).exists()) {
//                    Log.e("again: ", new File(next.getFilePath()).getAbsolutePath());
                    if (MyUtils.download(RecentWapp.this, next.getFilePath())) {
                        arrayList.add(next);
                        if (c != 0) {
                            c = 1;
                        } else {
                            return;
                        }
                    }
                }
                c = 0;
            }
            filesToDelete.clear();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ArrayList<StatusModel> arrayList2 = statusModelArrayList;
                ((StatusModel) it2.next()).selected = false;
                arrayList2.contains(false);
            }
            myAdapter.notifyDataSetChanged();
            if (c == 0) {
                Toast.makeText(RecentWapp.this, getResources().getString(R.string.save_error), Toast.LENGTH_SHORT).show();
            } else if (c == 1) {

                Toast.makeText(RecentWapp.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            }
            actionLay.setVisibility(View.GONE);
            selectAll.setChecked(false);
        }
    }


    public void m1789xd7a0acd5(CompoundButton compoundButton, boolean z) {
        if (compoundButton.isPressed()) {
            filesToDelete.clear();
            int i = 0;
            while (true) {
                if (i >= statusModelArrayList.size()) {
                    break;
                } else if (!statusModelArrayList.get(i).selected) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                for (int i2 = 0; i2 < statusModelArrayList.size(); i2++) {
                    statusModelArrayList.get(i2).selected = true;
                    filesToDelete.add(statusModelArrayList.get(i2));
                }
                selectAll.setChecked(true);
            } else {
                for (int i3 = 0; i3 < statusModelArrayList.size(); i3++) {
                    statusModelArrayList.get(i3).selected = false;
                }
                actionLay.setVisibility(View.GONE);
            }
            myAdapter.notifyDataSetChanged();
        }
    }


    @SuppressLint("WrongConstant")
    public void m1790xb8ace756(View view) {
        Intent intent;
        if (MyUtils.appInstalledOrNot(RecentWapp.this, "com.whatsapp")) {
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
        Toast.makeText(RecentWapp.this, "Please Install WhatsApp For Download Status!!!!!", 0).show();
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
            statusModelArrayList = new ArrayList<>();
            DocumentFile[] fromSdcard = getFromSdcard();
            allFiles = fromSdcard;
            Arrays.sort(fromSdcard, loadDataWhatsapp.INSTANCE);
            int i = 0;
            while (true) {
                DocumentFile[] documentFileArr = allFiles;
                if (i >= documentFileArr.length - 1) {
                    return null;
                }
                if (!documentFileArr[i].getUri().toString().contains(".nomedia")) {
                    statusModelArrayList.add(new StatusModel(allFiles[i].getUri().toString()));
                }
                i++;
            }
        }


        public void onPostExecute(Void r4) {
            super.onPostExecute(r4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public final void run() {
                    loadDataAsync.this.m1791x9f37e756();
                }
            }, 1000L);
        }

        public void m1791x9f37e756() {
            if (RecentWapp.this != null) {
                if (!(statusModelArrayList == null || statusModelArrayList.size() == 0)) {
                    RecentWapp recentWapp = RecentWapp.this;
                    RecentWapp recentWapp2 = RecentWapp.this;
                    recentWapp.myAdapter = new RecentAdapter(RecentWapp.this, recentWapp2.statusModelArrayList, RecentWapp.this);
                    imageGrid.setAdapter((ListAdapter) myAdapter);
                    imageGrid.setVisibility(View.VISIBLE);
                }
                loaderLay.setVisibility(View.GONE);
            }
            if (statusModelArrayList == null || statusModelArrayList.size() == 0) {
                emptyLay.setVisibility(View.VISIBLE);
            } else {
                emptyLay.setVisibility(View.GONE);
            }
        }
    }


    public DocumentFile[] getFromSdcard() {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getApplicationContext(), Uri.parse(SharedPrefs.getWATree(RecentWapp.this)));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    public String getWhatsupFolder() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("Android/media/com.whatsapp/WhatsApp");
        sb.append(File.separator);
        sb.append("Media");
        sb.append(File.separator);
        sb.append(".Statuses");
        return new File(sb.toString()).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses" : "WhatsApp%2FMedia%2F.Statuses";
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
            statusModelArrayList = new ArrayList<>();
            DocumentFile[] fromSdcard = getFromSdcard();
            for (int i3 = 0; i3 < fromSdcard.length - 1; i3++) {
                if (!fromSdcard[i3].getUri().toString().contains(".nomedia")) {
                    statusModelArrayList.add(new StatusModel(fromSdcard[i3].getUri().toString()));
                }
            }
            RecentAdapter recentAdapter2 = new RecentAdapter(this, statusModelArrayList, this);
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
            SharedPrefs.setWATree(RecentWapp.this, data.toString());
            populateGrid();
        }
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

    @Override

    public void onCheckboxListener(View view, List<StatusModel> list) {
        filesToDelete.clear();
        for (StatusModel statusModel : list) {
            if (statusModel.isSelected()) {
                filesToDelete.add(statusModel);
            }
        }
        if (filesToDelete.size() == statusModelArrayList.size()) {
            selectAll.setChecked(true);
        }
        if (!filesToDelete.isEmpty()) {
            actionLay.setVisibility(View.VISIBLE);
            return;
        }
        selectAll.setChecked(false);
        actionLay.setVisibility(View.GONE);
    }

}
