package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.videodown.allhd.app.AUtils.MyUtils.hideProgressDialog;
import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;

import com.videodown.allhd.app.Activity.StoryModel.TwitterResponse;
import com.bumptech.glide.Glide;
import com.videodown.allhd.app.AllStory.AllvideoAPI.CommonClassForAPI;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.SharePrefs;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.InterAds;
import com.google.gms.ads.MainAds;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;

public class TwitterActivity extends CdActivityBase {
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
    private DisposableObserver<TwitterResponse> observer = new DisposableObserver<TwitterResponse>() {
        public void onNext(TwitterResponse twitterResponse) {
            hideProgressDialog(TwitterActivity.this);
            try {
                VideoUrl = twitterResponse.getVideos().get(0).getUrl();
                if (twitterResponse.getVideos().get(0).getType().equals("image")) {
                    String str = TwitterActivity.this.VideoUrl;
                    String str2 = com.videodown.allhd.app.AUtils.MyUtils.RootDirectoryTwitter;
                    TwitterActivity twitterActivity = TwitterActivity.this;
                    com.videodown.allhd.app.AUtils.MyUtils.startDownload(str, str2, twitterActivity, twitterActivity.getFilenameFromURL(twitterActivity.VideoUrl, "image"));
                    TwitterActivity.this.etText.setText("");
                    return;
                }
                VideoUrl = twitterResponse.getVideos().get(twitterResponse.getVideos().size() - 1).getUrl();
                String str3 = TwitterActivity.this.VideoUrl;
                String str4 = com.videodown.allhd.app.AUtils.MyUtils.RootDirectoryTwitter;
                TwitterActivity twitterActivity2 = TwitterActivity.this;
                com.videodown.allhd.app.AUtils.MyUtils.startDownload(str3, str4, twitterActivity2, twitterActivity2.getFilenameFromURL(twitterActivity2.VideoUrl, "mp4"));
                TwitterActivity.this.etText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
                TwitterActivity twitterActivity3 = TwitterActivity.this;
                com.videodown.allhd.app.AUtils.MyUtils.setToast(twitterActivity3, twitterActivity3.getResources().getString(R.string.no_media_on_tweet));
            }
        }

        public void onError(Throwable th) {
            hideProgressDialog(TwitterActivity.this);
            th.printStackTrace();
        }

        public void onComplete() {
            hideProgressDialog(TwitterActivity.this);
        }
    };
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
        setContentView(R.layout.activity_twitter);

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
        com.videodown.allhd.app.AUtils.MyUtils.createFileFolder();
        initViews();
        imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.twitterrrr));
        tvAppName.setText(getResources().getString(R.string.twitter_app_name));
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
                TwitterActivity.this.onBackPressed();
            }
        });
//       imInfo.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                TwitterActivity.this.counter++;
//                if (TwitterActivity.this.counter % 2 == 0) {
//                    TwitterActivity.this.LLHowToLayout.setVisibility(View.VISIBLE);
//                } else {
//                    TwitterActivity.this.LLHowToLayout.setVisibility(View.GONE);
//                }
//            }
//        });
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.tw1)).into(this.imHowto1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.insta2)).into(this.imHowto2);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.tw3)).into(this.imHowto3);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.tw4)).into(this.imHowto4);
        tvHowTo1.setText(getResources().getString(R.string.open_twitter));
        tvHowTo3.setText(getResources().getString(R.string.open_twitter));
        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.ISSHOWHOWTOTWITTER).booleanValue()) {
            SharePrefs.getInstance(this).putBoolean(SharePrefs.ISSHOWHOWTOTWITTER, true);
            this.LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            this.LLHowToLayout.setVisibility(View.GONE);
        }
        this.loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {

                new MainAds().showInterAds(TwitterActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {

                        String obj = TwitterActivity.this.etText.getText().toString();
                        if (obj.equals("")) {
                            TwitterActivity twitterActivity = TwitterActivity.this;
                            com.videodown.allhd.app.AUtils.MyUtils.setToast(twitterActivity, twitterActivity.getResources().getString(R.string.enter_url));
                        } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                            TwitterActivity twitterActivity2 = TwitterActivity.this;
                            com.videodown.allhd.app.AUtils.MyUtils.setToast(twitterActivity2, twitterActivity2.getResources().getString(R.string.enter_valid_url));
                        } else {
                            com.videodown.allhd.app.AUtils.MyUtils.showProgressDialog(TwitterActivity.this);
                            TwitterActivity.this.GetTwitterData();
                        }

                    }
                });


            }
        });
        this.tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                TwitterActivity.this.PasteText();
            }
        });
        this.imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                com.videodown.allhd.app.AUtils.MyUtils.OpenApp(TwitterActivity.this, "com.twitter.android");
            }
        });
    }

    /* access modifiers changed from: private */
    public void GetTwitterData() {
        try {
            com.videodown.allhd.app.AUtils.MyUtils.createFileFolder();
            if (new URL(this.etText.getText().toString()).getHost().contains("twitter.com")) {
                Long tweetId = getTweetId(this.etText.getText().toString());
                if (tweetId != null) {
                    callGetTwitterData(String.valueOf(tweetId));
                    return;
                }
                return;
            }
            com.videodown.allhd.app.AUtils.MyUtils.setToast(this, getResources().getString(R.string.enter_url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long getTweetId(String str) {
        try {
            return Long.valueOf(Long.parseLong(str.split("\\/")[5].split("\\?")[0]));
        } catch (Exception e) {
            Log.d("TAG", "getTweetId: " + e.getLocalizedMessage());
            return null;
        }
    }


    public void PasteText() {
        try {
            this.etText.setText("");
            String stringExtra = getIntent().getStringExtra("CopyIntent");
            if (stringExtra.equals("")) {
                if (!this.clipBoard.hasPrimaryClip()) {
                    return;
                }
                if (this.clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = this.clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains("twitter.com")) {
                        this.etText.setText(itemAt.getText().toString());
                    }
                } else if (this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("twitter.com")) {
                    this.etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("twitter.com")) {
                this.etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetTwitterData(String str) {
        try {
            if (!new com.videodown.allhd.app.AUtils.MyUtils(this).isNetworkAvailable()) {
                com.videodown.allhd.app.AUtils.MyUtils.setToast(this, getResources().getString(R.string.no_net_conn));
            } else if (this.commonClassForAPI != null) {
                com.videodown.allhd.app.AUtils.MyUtils.showProgressDialog(this);
                this.commonClassForAPI.callTwitterApi(this.observer, "https://twittervideodownloaderpro.com/twittervideodownloadv2/index.php", str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilenameFromURL(String str, String str2) {
        if (str2.equals("image")) {
            try {
                return new File(new URL(str).getPath()).getName() + "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return System.currentTimeMillis() + ".jpg";
            }
        } else {
            try {
                return new File(new URL(str).getPath()).getName() + "";
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
                return System.currentTimeMillis() + ".mp4";
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