package com.videodown.allhd.app.Activity.StoryDownloader;

import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.videodown.allhd.app.AllStory.AllStoryAdepter.ShowImagesAdapter;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

public class FullViewActivity extends CdActivityBase {
    public int Position = 0;
    AppLangSessionManager appLangSessionManager;
    public ArrayList<File> fileArrayList;
    public ImageView imClose;
    public FloatingActionButton imDelete;
    public FloatingActionButton imShare;
    public FloatingActionButton imWhatsappShare;
    public LinearLayout lnrFooter;
    ShowImagesAdapter showImagesAdapter;
    public ViewPager vpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);


        imClose =  findViewById(R.id.im_close);
        imDelete =  findViewById(R.id.imDelete);
        imShare =  findViewById(R.id.imShare);
        imWhatsappShare =  findViewById(R.id.imWhatsappShare);
        lnrFooter =  findViewById(R.id.lnr_footer);
        vpView =  findViewById(R.id.vp_view);
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
        if (getIntent().getExtras() != null) {
            fileArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position", 0);
        }
        initViews();

        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void initViews() {
        ShowImagesAdapter showImagesAdapter2 = new ShowImagesAdapter(this, this.fileArrayList, this);
        showImagesAdapter = showImagesAdapter2;
        vpView.setAdapter(showImagesAdapter2);
        vpView.setCurrentItem(this.Position);
        vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                FullViewActivity.this.Position = i;
                PrintStream printStream = System.out;
                printStream.println("Current position==" + FullViewActivity.this.Position);
            }
        });
        imDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FullViewActivity.this);
                builder.setPositiveButton((CharSequence) FullViewActivity.this.getResources().getString(R.string.yes), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (FullViewActivity.this.fileArrayList.get(FullViewActivity.this.Position).delete()) {
                            FullViewActivity.this.deleteFileAA(FullViewActivity.this.Position);
                        }
                    }
                });
                builder.setNegativeButton((CharSequence) FullViewActivity.this.getResources().getString(R.string.no), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle(FullViewActivity.this.getResources().getString(R.string.do_u_want_to_dlt));
                create.show();
            }
        });
        imShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (FullViewActivity.this.fileArrayList.get(FullViewActivity.this.Position).getName().contains(".mp4")) {
                    Log.d("SSSSS", "onClick: " + FullViewActivity.this.fileArrayList.get(FullViewActivity.this.Position) + "");
                    FullViewActivity fullViewActivity = FullViewActivity.this;
                    MyUtils.shareVideo(fullViewActivity, fullViewActivity.fileArrayList.get(FullViewActivity.this.Position).getPath());
                    return;
                }
                FullViewActivity fullViewActivity2 = FullViewActivity.this;
                MyUtils.shareImage(fullViewActivity2, fullViewActivity2.fileArrayList.get(FullViewActivity.this.Position).getPath());
            }
        });
       imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (FullViewActivity.this.fileArrayList.get(FullViewActivity.this.Position).getName().contains(".mp4")) {
                    FullViewActivity fullViewActivity = FullViewActivity.this;
                    MyUtils.shareImageVideoOnWhatsapp(fullViewActivity, fullViewActivity.fileArrayList.get(FullViewActivity.this.Position).getPath(), true);
                    return;
                }
                FullViewActivity fullViewActivity2 = FullViewActivity.this;
                MyUtils.shareImageVideoOnWhatsapp(fullViewActivity2, fullViewActivity2.fileArrayList.get(FullViewActivity.this.Position).getPath(), false);
            }
        });
        this.imClose.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                FullViewActivity.this.onBackPressed();
            }
        });
    }

    public void onResume() {
        super.onResume();
        new MainAds().showBannerAds(this, null);

    }

    public void deleteFileAA(int i) {
        this.fileArrayList.remove(i);
        this.showImagesAdapter.notifyDataSetChanged();
        MyUtils.setToast(this, getResources().getString(R.string.file_deleted));
        if (this.fileArrayList.size() == 0) {
            onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }

    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }
}