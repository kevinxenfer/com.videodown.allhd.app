package com.videodown.allhd.app.Activity.StoryDownloader;




import androidx.appcompat.app.AppCompatActivity;
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
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AUtils.SharePrefs;
import com.google.android.exoplayer2.util.MimeTypes;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Locale;

public class MitronActivity extends AppCompatActivity {
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



        this.RLDownloadLayout = (LinearLayout) findViewById(R.id.RLDownloadLayout);
        this.RLEdittextLayout = (RelativeLayout) findViewById(R.id.RLEdittextLayout);
//        this.RLTopLayout = (RelativeLayout) findViewById(R.id.RLTopLayout);
        this.etText = (EditText) findViewById(R.id.et_text);
        this.imAppIcon = (ImageView) findViewById(R.id.imAppIcon);
        this.imBack = (ImageView) findViewById(R.id.imBack);
        this.imInfo = (ImageView) findViewById(R.id.imInfo);
        this.lnrMain = (LinearLayout) findViewById(R.id.lnr_main);
        this.loginBtn1 = (TextView) findViewById(R.id.login_btn1);
        this.tvAppName = (TextView) findViewById(R.id.tvAppName);
        this.tvPaste = (TextView) findViewById(R.id.tv_paste);
        this.LLHowToLayout = (LinearLayout) findViewById(R.id.LLHowToLayout);
        this.LLHowToOne = (LinearLayout) findViewById(R.id.LLHowToOne);
        this.imHowto1 = (ImageView) findViewById(R.id.im_howto1);
        this.imHowto2 = (ImageView) findViewById(R.id.im_howto2);
        this.imHowto3 = (ImageView) findViewById(R.id.im_howto3);
        this.imHowto4 = (ImageView) findViewById(R.id.im_howto4);
        this.tvHowTo1 = (TextView) findViewById(R.id.tvHowTo1);
        this.tvHowTo2 = (TextView) findViewById(R.id.tvHowTo2);
        this.tvHowTo3 = (TextView) findViewById(R.id.tvHowTo3);
        this.tvHowTo4 = (TextView) findViewById(R.id.tvHowTo4);
        this.tvHowToHeadOne = (TextView) findViewById(R.id.tvHowToHeadOne);
        this.tvHowToHeadTwo = (TextView) findViewById(R.id.tvHowToHeadTwo);
        this.commonClassForAPI = CommonClassForAPI.getInstance(this);
        MyUtils.createFileFolder();
        initViews();
        this.imAppIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_mitron));
        this.tvAppName.setText(getResources().getString(R.string.mitron_app_name));
        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        this.appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
    }

    public void onResume() {
        super.onResume();
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        PasteText();
    }

    private void initViews() {
        this.clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        this.imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MitronActivity.this.onBackPressed();
            }
        });
        this.imInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MitronActivity.this.counter++;
                if (MitronActivity.this.counter % 2 == 0) {
                    MitronActivity.this.LLHowToLayout.setVisibility(View.VISIBLE);
                } else {
                    MitronActivity.this.LLHowToLayout.setVisibility(View.GONE);
                }
            }
        });
        RequestManager with = Glide.with((FragmentActivity) this);
        Integer valueOf = Integer.valueOf(R.drawable.sc1);
        with.load(valueOf).into(this.imHowto1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.sc2)).into(this.imHowto2);
        Glide.with((FragmentActivity) this).load(valueOf).into(this.imHowto3);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.mi2)).into(this.imHowto4);
        this.tvHowToHeadOne.setVisibility(View.GONE);
        this.LLHowToOne.setVisibility(View.GONE);
        this.tvHowToHeadTwo.setText(getResources().getString(R.string.how_to_download));
        this.tvHowTo1.setText(getResources().getString(R.string.open_mitron));
        this.tvHowTo3.setText(getResources().getString(R.string.cop_link_from_mitron));
        if (!SharePrefs.getInstance(this).getBoolean(SharePrefs.ISSHOWHOWTOMITRON).booleanValue()) {
            SharePrefs.getInstance(this).putBoolean(SharePrefs.ISSHOWHOWTOMITRON, true);
            this.LLHowToLayout.setVisibility(View.VISIBLE);
        } else {
            this.LLHowToLayout.setVisibility(View.GONE);
        }
        this.loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = MitronActivity.this.etText.getText().toString();
                if (obj.equals("")) {
                    MitronActivity mitronActivity = MitronActivity.this;
                    MyUtils.setToast(mitronActivity, mitronActivity.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    MitronActivity mitronActivity2 = MitronActivity.this;
                    MyUtils.setToast(mitronActivity2, mitronActivity2.getResources().getString(R.string.enter_valid_url));
                } else {
                    MyUtils.showProgressDialog(MitronActivity.this);
                    MitronActivity.this.etText.setText(MyUtils.extractUrls(obj).get(0));
                    MitronActivity.this.getMitronData();
                }
            }
        });
        this.tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MitronActivity.this.PasteText();
            }
        });
        this.imAppIcon.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(MitronActivity.this, "com.mitron.tv");
            }
        });
    }


    public void getMitronData() {
        try {
            MyUtils.createFileFolder();
            String obj = this.etText.getText().toString();
            if (obj.contains("mitron")) {
                MyUtils.showProgressDialog(this);
                String[] split = obj.split("=");
                callGetJoshData callgetjoshdata = new callGetJoshData();
                callgetjoshdata.execute(new String[]{"https://web.mitron.tv/video/" + split[split.length - 1]});
                return;
            }
            MyUtils.setToast(this, getResources().getString(R.string.enter_valid_url));
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
                    if (itemAt.getText().toString().contains("mitron")) {
                        this.etText.setText(itemAt.getText().toString());
                    }
                } else if (this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("mitron")) {
                    this.etText.setText(this.clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("mitron")) {
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

            String str;
            try {
                String str2 = strArr[0];
                if (str2.contains("api.mitron.tv")) {
                    String[] split = str2.split("=");
                    str = "https://web.mitron.tv/video/" + split[split.length - 1];
                } else {
                    str = strArr[0];
                }
                JoshDoc = Jsoup.connect(str).get();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

//            try {
//                this.JoshDoc = Jsoup.connect(strArr[0]).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return this.JoshDoc;
        }

        public void onPostExecute(Document document) {
            MyUtils.hideProgressDialog(MitronActivity.this);
            try {
                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    MitronActivity.this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject(MimeTypes.BASE_TYPE_VIDEO).getString("videoUrl"));
                    String str = MitronActivity.this.VideoUrl;
                    MitronActivity mitronActivity = MitronActivity.this;
                    MyUtils.startDownload(str, MyUtils.ROOTDIRECTORYMITRON, mitronActivity, "mitron_" + System.currentTimeMillis() + ".mp4");
                    MitronActivity.this.VideoUrl = "";
                    MitronActivity.this.etText.setText("");
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