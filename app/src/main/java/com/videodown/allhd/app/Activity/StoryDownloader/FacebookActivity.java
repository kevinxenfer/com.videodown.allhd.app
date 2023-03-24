package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AUtils.SharePrefs;
import com.videodown.allhd.app.Activity.StoryModel.FBStoryModel.EdgesModel;
import com.videodown.allhd.app.Activity.StoryModel.FBStoryModel.NodeModel;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.FBStoriesListAdapter;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.FbStoryUserListAdapter;
import com.videodown.allhd.app.AllStory.AllvideoAPI.CommonClassForAPI;
import com.videodown.allhd.app.AllStory.AllvideoMyDownload.DownloadVideosMain;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.UserListInterface;
import com.videodown.allhd.app.AllStory.story.TrayModel;
import com.videodown.allhd.app.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import kotlin.text.StringsKt;

public class FacebookActivity extends CdActivityBase implements UserListInterface, View.OnClickListener, TextView.OnEditorActionListener {


    public LinearLayout RLDownloadLayout;
    public RelativeLayout RLEdittextLayout;
    public RelativeLayout RLLoginInstagram;
    public RelativeLayout RLTopLayout;

    public RecyclerView RVStories;
    public RecyclerView RVUserList;
    public SwitchCompat SwitchLogin;
    public ImageView TVTitle;
    AppLangSessionManager appLangSessionManager;
    private ClipboardManager clipBoard;
    CommonClassForAPI commonClassForAPI;
    int counter = 0;
    ArrayList<NodeModel> edgeModelList;
    public EditText etText;
    FBStoriesListAdapter fbStoriesListAdapter;
    FbStoryUserListAdapter fbStoryUserListAdapter;
    public ImageView imBack;
    public ImageView imInfo;
    public LinearLayout lnrMain;
    public LinearLayoutCompat loginBtn1;
    public ProgressBar prLoadingBar;
    private String strName = "facebook";
    private String strNameSecond = "fb";
    public TextView tvAppName;
    public RelativeLayout tvLogin;
    public TextView tvLoginInstagram;
    public LinearLayoutCompat tvPaste;
    public TextView tvViewStories;

    public void userListClick(int i, TrayModel trayModel) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        RLDownloadLayout = findViewById(R.id.RLDownloadLayout);
        RLEdittextLayout = findViewById(R.id.RLEdittextLayout);
        RLLoginInstagram = findViewById(R.id.RLLoginInstagram);
        RVStories = findViewById(R.id.RVStories);
        RVUserList = findViewById(R.id.RVUserList);
        SwitchLogin = findViewById(R.id.SwitchLogin);
        TVTitle = findViewById(R.id.TVTitle);
        etText = findViewById(R.id.et_text);
        imBack = findViewById(R.id.imBack);
        imInfo = findViewById(R.id.imInfo);
        lnrMain = findViewById(R.id.lnr_main);
        loginBtn1 = findViewById(R.id.login_btn1);
        prLoadingBar = findViewById(R.id.pr_loading_bar);
        tvAppName = findViewById(R.id.tvAppName);
        tvLogin = findViewById(R.id.tvLogin);
        tvLoginInstagram = findViewById(R.id.tvLoginInstagram);
        tvPaste = findViewById(R.id.tv_paste);
        tvViewStories = findViewById(R.id.tvViewStories);

        AppLangSessionManager appLangSessionManager2 = new AppLangSessionManager(this);
        appLangSessionManager = appLangSessionManager2;
        setLocale(appLangSessionManager2.getLanguage());
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        MyUtils.createFileFolder();
        initViews();

    }

    public static void showFileExistPopUpDialog(String fileName, String filePath, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle((CharSequence) "File Exist!");
        builder.setMessage((CharSequence) "File already exist you want to re-download it?");
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) null);
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public final void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        playFile(activity, filePath);
                        return;
                    }
                });
                alertDialog.getButton(-2).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public static void playFile(Context context, String mLocalUri) {
        try {
            context.startActivity(Intent.createChooser(playFileIntent(context, mLocalUri), "Play With"));
        } catch (Exception e) {
            Toast.makeText(context, "No App Found", Toast.LENGTH_LONG).show();
        }
    }

    public static Intent playFileIntent(Context context, String mLocalUri) {
        String contain = mLocalUri;
        Uri uri = Uri.parse(mLocalUri);
        Intent intent = new Intent("android.intent.action.VIEW");
        if (contain.contains(".zip") || contain.contains(".rar")) {
            intent.setDataAndType(uri, "application/x-wav");
        } else if (contain.contains(".wav") || contain.contains(".mp3")) {
            Uri fileUri = FileProvider.getUriForFile(context, "com.mydownloader.mysocialvideos.app.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, "audio/*");
        } else if (contain.contains(".gif")) {
            intent.setDataAndType(uri, "image/gif");
        } else if (contain.contains(".jpg") || contain.contains(".jpeg") || contain.contains(".png")) {
            intent.setDataAndType(uri, "image/jpeg");
        } else if (contain.contains(".txt")) {
            intent.setDataAndType(uri, "text/plain");
        } else if (contain.contains(".3gp") || contain.contains(".mpg") || contain.contains(".mpeg") || contain.contains(".mpe") || contain.contains(".mp4") || contain.contains(".avi")) {
            Uri fileUri2 = FileProvider.getUriForFile(context, "com.mydownloader.mysocialvideos.app.fileprovider", new File(String.valueOf(uri)));
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri2, "video/mp4");
        } else {
            intent.setDataAndType(uri, "*/*");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public void onResume() {
        super.onResume();
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        pasteText();
        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    private ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResultCallback;

    public void setOnRequestPermissionsResultListener(ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResultCallback) {
        onRequestPermissionsResultCallback = onRequestPermissionsResultCallback;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        return handled;
    }

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        tvAppName.setText("Facebook Via Link");
        TVTitle.setImageResource(R.drawable.fb);
        tvLoginInstagram.setText(getResources().getString(R.string.download_stories));
        imBack.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                pasteText();
            }
        });
        tvAppName.setText("Facebook Via Link");
        imInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {

                String obj = etText.getText().toString();
                if (obj.contains("posts")) {
                    MyUtils.setToast(FacebookActivity.this, getResources().getString(R.string.enter_valid_url));
                } else if (obj.equals("")) {
                    FacebookActivity facebookActivity = FacebookActivity.this;
                    MyUtils.setToast(facebookActivity, facebookActivity.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    FacebookActivity facebookActivity2 = FacebookActivity.this;
                    MyUtils.setToast(facebookActivity2, facebookActivity2.getResources().getString(R.string.enter_valid_url));
                } else {
                    String str4 = etText.getText().toString();
                    DownloadVideosMain.Start(FacebookActivity.this, StringsKt.trim((CharSequence) str4).toString(), false);
                }
            }
        });
        TVTitle.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(FacebookActivity.this, "com.facebook.katana");
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ArrayList<NodeModel> arrayList = new ArrayList<>();
        edgeModelList = arrayList;
        FbStoryUserListAdapter fbStoryUserListAdapter2 = new FbStoryUserListAdapter(this, arrayList, this);
        fbStoryUserListAdapter = fbStoryUserListAdapter2;
        RVUserList.setAdapter(fbStoryUserListAdapter2);
        if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
            layoutCondition();
            getFacebookUserData();
            SwitchLogin.setChecked(true);
        } else {
            SwitchLogin.setChecked(false);
        }
        RLLoginInstagram.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                tvLogin.performClick();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (!SharePrefs.getInstance(FacebookActivity.this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                    startActivityForResult(new Intent(FacebookActivity.this, FBLoginActivity.class), 100);
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(FacebookActivity.this);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SharePrefs.getInstance(FacebookActivity.this).putBoolean(SharePrefs.ISFBLOGIN, false);
                        SharePrefs.getInstance(FacebookActivity.this).putString(SharePrefs.FBKEY, "");
                        SharePrefs.getInstance(FacebookActivity.this).putString(SharePrefs.FBCOOKIES, "");
                        if (SharePrefs.getInstance(FacebookActivity.this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                            SwitchLogin.setChecked(true);
                        } else {
                            SwitchLogin.setChecked(false);
                            RVUserList.setVisibility(View.GONE);
                            RVStories.setVisibility(View.GONE);
                            tvLogin.setVisibility(View.VISIBLE);
                            tvViewStories.setText(getResources().getText(R.string.view_stories));
                        }
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle(getResources().getString(R.string.do_u_want_to_download_media_from_pvt));
                create.show();
            }
        });
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
        RVStories.setLayoutManager(gridLayoutManager2);
        RVStories.setNestedScrollingEnabled(false);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);

    }

    public void layoutCondition() {
        tvViewStories.setText(getResources().getString(R.string.stories));
        tvLogin.setVisibility(View.GONE);
    }

    public static int ordinalIndexOf(String str, String str2, int i) {
        int i2 = -1;
        while (true) {
            i2 = str.indexOf(str2, i2 + 1);
            if (i <= 0 || i2 == -1) {
                break;
            }
            i--;
        }
        return i2;
    }

    public void pasteText() {

        try {
            etText.setText("");
            String extractLinks = getIntent().getStringExtra("CopyIntent");
            if (extractLinks == null || extractLinks.equals("")) {
                if (!clipBoard.hasPrimaryClip()) {
                    Log.d("ContentValues", "PasteText");
                } else if (clipBoard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    ClipData.Item itemAt = clipBoard.getPrimaryClip().getItemAt(0);
                    if (itemAt.getText().toString().contains(strName)) {
                        etText.setText(itemAt.getText().toString());
                    } else if (itemAt.getText().toString().contains(strNameSecond)) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains(strName)) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains(strNameSecond)) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (extractLinks.contains(strName)) {
                etText.setText(extractLinks);
            } else if (extractLinks.contains(strNameSecond)) {
                etText.setText(extractLinks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fbUserListClick(int i, NodeModel nodeModel) {
        getFacebookUserStories(nodeModel.getNodeDataModel().getId());
    }

    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i != 100) {
                return;
            }
            if (i2 == -1) {
                if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISFBLOGIN).booleanValue()) {
                    SwitchLogin.setChecked(true);
                    layoutCondition();
                    getFacebookUserData();
                    return;
                }
                SwitchLogin.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFacebookUserData() {

        prLoadingBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://www.facebook.com/api/graphql/").addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7").addHeaders("cookie", SharePrefs.getInstance(this).getString(SharePrefs.FBCOOKIES)).addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").addHeaders("Content-Type", "application/json").addBodyParameter("fb_dtsg", SharePrefs.getInstance(this).getString(SharePrefs.FBKEY)).addBodyParameter("variables", "{\"bucketsCount\":200,\"initialBucketID\":null,\"pinnedIDs\":[\"\"],\"scale\":3}").addBodyParameter("doc_id", "2893638314007950").setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("JsonResp- " + response);
                if (response != null) {
                    try {
                        JSONObject unified_stories_buckets = response.getJSONObject("data").getJSONObject("me").getJSONObject("unified_stories_buckets");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<EdgesModel>() {
                        }.getType();
                        EdgesModel edgesModelNew = gson.fromJson(String.valueOf(unified_stories_buckets), listType);

                        if (edgesModelNew.getEdgeModel().size() > 0) {
                            edgeModelList.clear();
                            edgeModelList.addAll(edgesModelNew.getEdgeModel());
                            fbStoryUserListAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                RVUserList.setVisibility(View.VISIBLE);
                prLoadingBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(ANError anError) {
                prLoadingBar.setVisibility(View.GONE);
            }

        });
    }

    public void getFacebookUserStories(String userId) {
        prLoadingBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://www.facebook.com/api/graphql/").addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7").addHeaders("cookie", SharePrefs.getInstance(this).getString(SharePrefs.FBCOOKIES)).addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").addHeaders("Content-Type", "application/json").addBodyParameter("fb_dtsg", SharePrefs.getInstance(this).getString(SharePrefs.FBKEY)).addBodyParameter("variables", "{\"bucketID\":\"" + userId + "\",\"initialBucketID\":\"" + userId + "\",\"initialLoad\":false,\"scale\":5}").addBodyParameter("doc_id", "2558148157622405").setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("JsonResp- " + response);
                prLoadingBar.setVisibility(View.GONE);
                if (response != null) {
                    try {
                        JSONObject edges = response.getJSONObject("data").getJSONObject("bucket").getJSONObject("unified_stories");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<EdgesModel>() {
                        }.getType();
                        EdgesModel edgesModelNew = gson.fromJson(String.valueOf(edges), listType);
                        fbStoriesListAdapter = new FBStoriesListAdapter(FacebookActivity.this, edgesModelNew.getEdgeModel());
                        RVStories.setAdapter(fbStoriesListAdapter);
                        fbStoriesListAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                prLoadingBar.setVisibility(View.GONE);
            }

        });
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