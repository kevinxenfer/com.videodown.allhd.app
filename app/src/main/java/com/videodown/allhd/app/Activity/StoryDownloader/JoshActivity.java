package com.videodown.allhd.app.Activity.StoryDownloader;




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

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class JoshActivity extends CdActivityBase {
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
    public TextView loginBtn1;
    public TextView tvAppName;
    public TextView tvHowTo1;
    public TextView tvHowTo2;
    public TextView tvHowTo3;
    public TextView tvHowTo4;
    public TextView tvHowToHeadOne;
    public TextView tvHowToHeadTwo;
    public TextView tvPaste;

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
        MyUtils.createFileFolder();
        initViews();
        imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.josh_logo));
        tvAppName.setText(getResources().getString(R.string.josh_app_name));
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
    }

    public void onResume() {
        super.onResume();
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        PasteText();

    }

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                JoshActivity.this.onBackPressed();
            }
        });
        imInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                counter++;
                if (counter % 2 == 0) {
                    LLHowToLayout.setVisibility(View.VISIBLE);
                } else {
                    LLHowToLayout.setVisibility(View.GONE);
                }
            }
        });
        RequestManager with = Glide.with((FragmentActivity) this);
        Integer valueOf = Integer.valueOf(R.drawable.sc1);
        with.load(valueOf).into(this.imHowto1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.sc2)).into(this.imHowto2);
        Glide.with((FragmentActivity) this).load(valueOf).into(this.imHowto3);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.jo2)).into(this.imHowto4);
        tvHowToHeadOne.setVisibility(View.GONE);
        LLHowToOne.setVisibility(View.GONE);
        tvHowToHeadTwo.setText(getResources().getString(R.string.how_to_download));
        tvHowTo1.setText(getResources().getString(R.string.open_josh));
        tvHowTo3.setText(getResources().getString(R.string.cop_link_from_josh));
        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.ISSHOWHOWTOJOSH).booleanValue()) {
            SharePrefs.getInstance(this).putBoolean(SharePrefs.ISSHOWHOWTOJOSH, true);
            LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            LLHowToLayout.setVisibility(View.GONE);
        }
        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = JoshActivity.this.etText.getText().toString();
                if (obj.equals("")) {
                    JoshActivity joshActivity = JoshActivity.this;
                    MyUtils.setToast(joshActivity, joshActivity.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    JoshActivity joshActivity2 = JoshActivity.this;
                    MyUtils.setToast(joshActivity2, joshActivity2.getResources().getString(R.string.enter_valid_url));
                } else {
                    MyUtils.showProgressDialog(JoshActivity.this);
                    JoshActivity.this.getJoshData();
                }
            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                JoshActivity.this.PasteText();
            }
        });
        imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(JoshActivity.this, "com.eterno.shortvideos");
            }
        });
    }


    public void getJoshData() {
        try {
            MyUtils.createFileFolder();
            if (new URL(this.etText.getText().toString()).getHost().contains("myjosh")) {
                MyUtils.showProgressDialog(this);
                new callGetJoshData().execute(new String[]{this.etText.getText().toString()});
                return;
            }
            MyUtils.setToast(this, getResources().getString(R.string.enter_url));
        } catch (Exception e) {
            e.printStackTrace();
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
                    if (itemAt.getText().toString().contains("myjosh")) {
                        this.etText.setText(itemAt.getText().toString());
                    }
                } else if (this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("myjosh")) {
                    this.etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("myjosh")) {
                this.etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class callGetJoshData extends AsyncTask<String, Void, Document> {
        Document JoshDoc;

        callGetJoshData() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Document doInBackground(String... strArr) {
            try {
                this.JoshDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.JoshDoc;
        }

        public void onPostExecute(Document document) {
            MyUtils.hideProgressDialog(JoshActivity.this);
            try {
                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    JSONObject jSONObject = new JSONObject(html);
                    VideoUrl = (jSONObject.getJSONObject("props").getJSONObject("pageProps").getJSONObject("detail").getJSONObject("data").getString("download_url"));
                    String.valueOf(jSONObject.getJSONObject("props").getJSONObject("pageProps").getJSONObject("detail").getJSONObject("data").getJSONObject("audio_track_meta").getString("url"));
                    VideoUrl = VideoUrl.replace("r4_wmj_480.mp4", "r4_480.mp4");
                    String str = VideoUrl;
                    MyUtils.startDownload(str, MyUtils.ROOTDIRECTORYJOSH, JoshActivity.this, "josh_" + System.currentTimeMillis() + ".mp4");
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

        finish();

    }
}