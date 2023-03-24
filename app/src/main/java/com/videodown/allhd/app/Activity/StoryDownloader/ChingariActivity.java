package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.videodown.allhd.app.AllStory.AllvideoAPI.CommonClassForAPI;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AUtils.SharePrefs;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class ChingariActivity extends CdActivityBase {
    public LinearLayout LLHowToLayout;
    public LinearLayout LLHowToOne;
    public LinearLayout RLDownloadLayout;
    public RelativeLayout RLEdittextLayout;
    public RelativeLayout RLTopLayout;
    public String VideoUrl;
    AppLangSessionManager appLangSessionManager;
    private ClipboardManager clipBoard;
    CommonClassForAPI commonClassForAPI;
    int counter = 0;
    public EditText etText;
    public ImageView imAppIcon;
    public ImageView imBack;
    public ImageView imHowto1;
    public ImageView imHowto2;
    public ImageView imHowto3;
    public ImageView imHowto4;
    public ImageView imInfo;
    public LinearLayout lnrMain;
    public LinearLayoutCompat loginBtn1;
    public TextView tvAppName;
    public TextView tvHowTo1;
    public TextView tvHowTo2;
    public TextView tvHowTo3;
    public TextView tvHowTo4;
    public TextView tvHowToHeadOne;
    public TextView tvHowToHeadTwo;
    public LinearLayoutCompat tvPaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chin);

        RLDownloadLayout = findViewById(R.id.RLDownloadLayout);
        RLEdittextLayout = findViewById(R.id.RLEdittextLayout);
        etText = findViewById(R.id.et_text);
        imAppIcon = findViewById(R.id.imAppIcon);
        imBack = findViewById(R.id.imBack);
        imInfo = findViewById(R.id.imInfo);
        lnrMain = findViewById(R.id.lnr_main);
        loginBtn1 = findViewById(R.id.login_btn1);
        tvAppName = findViewById(R.id.tvAppName);
        tvPaste = findViewById(R.id.tv_paste);
        LLHowToLayout = findViewById(R.id.LLHowToLayout);
        LLHowToOne = findViewById(R.id.LLHowToOne);
        imHowto1 = findViewById(R.id.im_howto1);
        imHowto2 = findViewById(R.id.im_howto2);
        imHowto3 = findViewById(R.id.im_howto3);
        imHowto4 = findViewById(R.id.im_howto4);
        tvHowTo1 = findViewById(R.id.tvHowTo1);
        tvHowTo2 = findViewById(R.id.tvHowTo2);
        tvHowTo3 = findViewById(R.id.tvHowTo3);
        tvHowTo4 = findViewById(R.id.tvHowTo4);
        tvHowToHeadOne = findViewById(R.id.tvHowToHeadOne);
        tvHowToHeadTwo = findViewById(R.id.tvHowToHeadTwo);

        commonClassForAPI = CommonClassForAPI.getInstance(this);
        MyUtils.createFileFolder();
        initViews();
        tvAppName.setText(getResources().getString(R.string.chingari_app_name));
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
    }

    public void onResume() {
        super.onResume();
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        PasteText();

        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);

    }

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChingariActivity.this.onBackPressed();
            }
        });

        RequestManager with = Glide.with((FragmentActivity) this);
        Integer valueOf = Integer.valueOf(R.drawable.sc1);
        with.load(valueOf).into(this.imHowto1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.sc2)).into(this.imHowto2);
        Glide.with((FragmentActivity) this).load(valueOf).into(this.imHowto3);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.chi2)).into(this.imHowto4);
        tvHowToHeadOne.setVisibility(View.GONE);
        LLHowToOne.setVisibility(View.GONE);
        tvHowToHeadTwo.setText(getResources().getString(R.string.how_to_download));
        tvHowTo1.setText(getResources().getString(R.string.open_chingari));
        tvHowTo3.setText(getResources().getString(R.string.cop_link_from_chingari));
        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.ISSHOWHOWTOCHINGARI).booleanValue()) {
            SharePrefs.getInstance(this).putBoolean(SharePrefs.ISSHOWHOWTOCHINGARI, true);
            LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            LLHowToLayout.setVisibility(View.GONE);
        }
        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {

                new MainAds().showInterAds(ChingariActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {


                        String obj = ChingariActivity.this.etText.getText().toString();
                        if (obj.equals("")) {
                            MyUtils.setToast(ChingariActivity.this, ChingariActivity.this.getResources().getString(R.string.enter_url));
                        } else if (!Patterns.WEB_URL.matcher(obj.trim()).matches()) {
                            MyUtils.setToast(ChingariActivity.this, ChingariActivity.this.getResources().getString(R.string.enter_valid_url));
                        } else {
                            MyUtils.showProgressDialog(ChingariActivity.this);
                            getChingariData();
                        }

                    }
                });


            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ChingariActivity.this.PasteText();
            }
        });
        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(ChingariActivity.this, "io.chingari.app");
            }
        });
    }


    public void getChingariData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(this.etText.getText().toString()).getHost().contains("chingari.io")) {
                MyUtils.showProgressDialog(this);
                new CallGetChingariData().execute(new String[]{this.etText.getText().toString()});
                return;
            }
            MyUtils.setToast(this, getResources().getString(R.string.enter_url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void PasteText() {
        try {
            etText.setText("");
            String stringExtra = getIntent().getStringExtra("CopyIntent");
            if (stringExtra.equals("")) {
                if (!clipBoard.hasPrimaryClip()) {
                    return;
                }
                if (clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains("chingari.io")) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("chingari.io")) {
                    etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("chingari.io")) {
                etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CallGetChingariData extends AsyncTask<String, Void, Document> {
        Document chingariDoc;
        String strHTML;

        CallGetChingariData() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Document doInBackground(String... strArr) {

            try {

                Connection connection = Jsoup.connect(strArr[0]);
                connection.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

                chingariDoc = connection.get();

                strHTML = chingariDoc.text();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            return this.chingariDoc;
        }

        public void onPostExecute(Document document) {
            MyUtils.hideProgressDialog(ChingariActivity.this);
            try {
                VideoUrl = document.select("meta[property=og:video]").last().attr("content");

                if (!VideoUrl.equals("")) {
                    String str = VideoUrl;
                    MyUtils.startDownload(str, MyUtils.ROOTDIRECTORYCHINGARI, ChingariActivity.this, "chingari_" + System.currentTimeMillis() + ".mp4");
                    VideoUrl = "";
                    etText.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
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