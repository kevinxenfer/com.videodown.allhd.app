package com.videodown.allhd.app.Activity.StoryDownloader;




import static com.google.gms.ads.AdUtils.AD_LARGE;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class ShareChatActivity extends CdActivityBase {
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
        setContentView(R.layout.activity_share);

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
        imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.sharechat));
        tvAppName.setText(getResources().getString(R.string.sharechat_app_name));
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
                ShareChatActivity.this.onBackPressed();
            }
        });
//        imInfo.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                counter++;
//                if (counter % 2 == 0) {
//                    LLHowToLayout.setVisibility(View.VISIBLE);
//                } else {
//                    LLHowToLayout.setVisibility(View.GONE);
//                }
//            }
//        });
        RequestManager with = Glide.with((FragmentActivity) this);
        Integer valueOf = Integer.valueOf(R.drawable.sc1);
        with.load(valueOf).into(this.imHowto1);
        RequestManager with2 = Glide.with((FragmentActivity) this);
        Integer valueOf2 = Integer.valueOf(R.drawable.sc2);
        with2.load(valueOf2).into(this.imHowto2);
        Glide.with((FragmentActivity) this).load(valueOf).into(this.imHowto3);
        Glide.with((FragmentActivity) this).load(valueOf2).into(this.imHowto4);
        tvHowToHeadOne.setVisibility(View.GONE);
        LLHowToOne.setVisibility(View.GONE);

        tvHowToHeadTwo.setText(getResources().getString(R.string.how_to_download));
        tvHowTo1.setText(getResources().getString(R.string.open_sharechat));
        tvHowTo3.setText(getResources().getString(R.string.cop_link_from_sharechat));

        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.ISSHOWHOWTOSHARECHAT).booleanValue()) {
            SharePrefs.getInstance(this).putBoolean(SharePrefs.ISSHOWHOWTOSHARECHAT, true);
            LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            LLHowToLayout.setVisibility(View.GONE);
        }

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {

                new MainAds().showInterAds(ShareChatActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {


                        String obj = ShareChatActivity.this.etText.getText().toString();
                        if (obj.equals("")) {
                            MyUtils.setToast(ShareChatActivity.this, ShareChatActivity.this.getResources().getString(R.string.enter_url));
                        } else if (!Patterns.WEB_URL.matcher(obj).matches()) {

                            MyUtils.setToast(ShareChatActivity.this, ShareChatActivity.this.getResources().getString(R.string.enter_valid_url));
                        } else {
                            MyUtils.showProgressDialog(ShareChatActivity.this);
                            ShareChatActivity.this.GetSharechatData();
                        }

                    }
                });


            }
        });

        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ShareChatActivity.this.PasteText();
            }
        });

        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(ShareChatActivity.this, "in.mohalla.sharechat");
            }
        });

    }


    public void GetSharechatData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(etText.getText().toString()).getHost().contains("sharechat.com")) {

//                Log.e("ShareChatURL", "--------url---------" + etText.getText().toString());

                MyUtils.showProgressDialog(this);
                new callGetShareChatData().execute(etText.getText().toString());
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
                    if (itemAt.getText().toString().contains("sharechat")) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("sharechat")) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("sharechat")) {
                etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class callGetShareChatData extends AsyncTask<String, Void, Document> {


        Document doc = null;
        String strHTML;

        callGetShareChatData() {

//            Log.e("ShareChatURL", "--------doc-----task4----");
        }


        public void onPreExecute() {
            super.onPreExecute();

//            Log.e("ShareChatURL", "--------doc-----task1----");
        }

        public Document doInBackground(String... strArr) {
//            Log.e("ShareChatURL", "--------doc-----task2----");

            try {
                Connection connection = Jsoup.connect(etText.getText().toString());
                connection.userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
                connection.referrer("http://www.google.com");
                doc = connection.get();


                strHTML = doc.title();

//                Log.e("ShareChatURL", "--------newdoc-----" + strHTML);

                Elements videoIdMeta1 = doc.select("script[type=application/ld+json]");
                for (Element link : videoIdMeta1) {
                    try {
                        JSONObject mobj = new JSONObject(link.data().toString());
                        VideoUrl = mobj.getString("contentUrl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
///



//                Log.e("ShareChatURL", "--------videoid---22222222--" + VideoUrl);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


            return doc;


        }

        public void onPostExecute(Document document) {

//            Log.e("ShareChatURL", "--------doc-----task3----");

            MyUtils.hideProgressDialog(ShareChatActivity.this);
            try {


                if (!VideoUrl.equals("")) {
                    try {
                        String str = VideoUrl;
                        String str2 = MyUtils.RootDirectoryShareChat;
                        MyUtils.startDownload(str, str2, ShareChatActivity.this, "sharechat_" + System.currentTimeMillis() + ".mp4");
                        VideoUrl = "";
                        etText.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (NullPointerException e2) {
                e2.printStackTrace();
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