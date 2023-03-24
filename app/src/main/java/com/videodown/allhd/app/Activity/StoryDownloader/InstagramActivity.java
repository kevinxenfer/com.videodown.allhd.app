package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.google.gms.ads.AdUtils.AD_LARGE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.videodown.allhd.app.Activity.StoryModel.EdgeSidecarToChildren;
import com.videodown.allhd.app.Activity.StoryModel.FBStoryModel.NodeModel;
import com.videodown.allhd.app.Activity.StoryModel.ResponseModel;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.StoriesListAdapter;
import com.videodown.allhd.app.AllStory.AllStoryAdepter.UserListAdapter;
import com.videodown.allhd.app.AllStory.AllvideoAPI.CommonClassForAPI;
import com.videodown.allhd.app.AllStory.AllvideoAPI.DBHelper;
import com.videodown.allhd.app.AllStory.Allvideointerfaces.UserListInterface;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.AppLangSessionManager;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.videodown.allhd.app.AUtils.SharePrefs;
import com.videodown.allhd.app.AllStory.story.FullDetailModel;
import com.videodown.allhd.app.AllStory.story.ItemModel;
import com.videodown.allhd.app.AllStory.story.ReelFeedModel;
import com.videodown.allhd.app.AllStory.story.StoryModel;
import com.videodown.allhd.app.AllStory.story.TrayModel;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;

public class InstagramActivity extends CdActivityBase implements UserListInterface {
    public LinearLayout LLHowToLayout;
    public LinearLayout LLHowToOne;
    public String PhotoUrl;
    public LinearLayout RLDownloadLayout;
    public RelativeLayout RLEdittextLayout;
    public RelativeLayout RLLoginInstagram;
    public RelativeLayout RLTopLayout;
    public RecyclerView RVStories;
    public RecyclerView RVUserList;
    public SwitchCompat SwitchLogin;
    public ImageView TVTitle;
    public String VideoUrl;
    AppLangSessionManager appLangSessionManager;
    private ClipboardManager clipBoard;
    CommonClassForAPI commonClassForAPI;
    int counter = 0;
    public EditText etText;
    public ImageView imBack;
    public ImageView imInfo;

    public LinearLayout lnrMain;
    public LinearLayoutCompat loginBtn1;
    public ProgressBar prLoadingBar;
    StoriesListAdapter storiesListAdapter;
    public static DBHelper dbHelper = null;
    public TextView tvAppName;
    public RelativeLayout tvLogin;
    public TextView tvLoginInstagram;
    public LinearLayoutCompat tvPaste;
    public TextView tvViewStories;
    UserListAdapter userListAdapter;

    private Dialog dialog;

    public void fbUserListClick(int i, NodeModel nodeModel) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

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
        tvAppName.setText("Instagram Via Link");
        MyUtils.createFileFolder();
        initViews();

    }

    private DisposableObserver<JsonObject> disposableObserver = new DisposableObserver<JsonObject>() {
        public void onNext(JsonObject jsonObject) {
            String str;
            Exception e;
            String str2;
            ItemModel itemModel = null;
            String str3;
            try {
                boolean booleanValue = SharePrefs.getInstance(InstagramActivity.this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue();
                str = DBHelper.TABLE_RECENTLY;
                int i = 0;
                try {
                    if (booleanValue) {
                        Log.d("jsonObject", "" + jsonObject.keySet().toArray()[0]);
                        if (jsonObject.keySet().toArray()[0].equals("showQRModal")) {
                            TrayModel trayModel = (TrayModel) new Gson().fromJson(jsonObject, new TypeToken<TrayModel>() {
                            }.getType());
                            Log.d("jsonObject", "" + trayModel.getUser().getId());
                            callStoriesDetailApi(String.valueOf(trayModel.getUser().getId()));
                            dbHelper = new DBHelper(getApplicationContext());
                            SQLiteDatabase writableDatabase = InstagramActivity.dbHelper.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DBHelper.KEY_NAME, trayModel.getUser().getUsername());
                            contentValues.put(DBHelper.KEY_PHOTO, trayModel.getUser().getProfile_pic_url());
                            Cursor query = writableDatabase.query(DBHelper.TABLE_RECENTLY, null, null, null, null, null, null);
                            Log.d("DatabaseUtils", "" + DatabaseUtils.queryNumEntries(writableDatabase, str));
                            int i2 = 0;
                            while (true) {
                                if (i2 >= DatabaseUtils.queryNumEntries(writableDatabase, str)) {
                                    break;
                                }
                                if (query.moveToPosition(i2)) {
                                    int columnIndex = query.getColumnIndex(DBHelper.KEY_ID);
                                    int columnIndex2 = query.getColumnIndex(DBHelper.KEY_NAME);
                                    Log.d("nameIndex", "" + columnIndex2);
                                    query.getColumnIndex(DBHelper.KEY_PHOTO);
                                    if (query.getString(columnIndex2).equals(trayModel.getUser().getUsername())) {
                                        writableDatabase.delete(str, "_id=" + query.getInt(columnIndex), null);
                                        break;
                                    }
                                } else {
                                    Log.d("mLog", "0 row");
                                }
                                i2++;
                            }
                            query.close();
                            writableDatabase.insert(str, null, contentValues);
                            dbHelper.close();
                            return;
                        }

                        ArrayList arrayList = new ArrayList();
                        ReelFeedModel reelFeedModel = (ReelFeedModel) new Gson().fromJson(jsonObject, new TypeToken<ReelFeedModel>() {
                        }.getType());
                        Log.d("jsonObject", "" + jsonObject);
                        JsonObject asJsonObject = jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject();
                        String asString = asJsonObject.get("user").getAsJsonObject().get("username").getAsString();
                        asJsonObject.get("user").getAsJsonObject().get("full_name").getAsString();
                        String trim = asJsonObject.get("user").getAsJsonObject().get("profile_pic_url").getAsString().trim();

                        try {

                            if (asJsonObject.get("media_type").getAsInt() == 1 || asJsonObject.get("media_type").getAsInt() == 2) {
                                str = " ";
                                arrayList.addAll(reelFeedModel.getItems());
                            } else {
                                JsonArray asJsonArray = asJsonObject.get("carousel_media").getAsJsonArray();
                                for (int i3 = 0; i3 < asJsonArray.size(); i3++) {
                                    arrayList.add((ItemModel) new Gson().fromJson(asJsonArray.get(i3).getAsJsonObject(), new TypeToken<ItemModel>() {
                                    }.getType()));
                                    Log.d("item", "" + itemModel.getMedia_type());
                                }
                                StringBuilder sb = new StringBuilder();
                                sb.append("");
                                sb.append(asJsonArray.get(0).getAsJsonObject().keySet());
                                str = " ";
                                sb.append(str);
                                sb.append(asJsonArray.size());
                                sb.append(str);
                                sb.append(asJsonArray.get(0).getAsJsonObject());
                                Log.d("carousel", sb.toString());
                            }

                            try {
                                str2 = asJsonObject.get("caption").getAsJsonObject().getAsJsonObject().get("text").getAsString();
                            } catch (Exception unused) {
                                str2 = "";
                            }
                            HashMap hashMap = new HashMap();
                            hashMap.put(ImagesContract.URL, etText.getText().toString());
                            hashMap.put("intro", str2);
                            hashMap.put("tags", setTags(str2));
                            String json = new Gson().toJson(hashMap);
                            long currentTimeMillis = System.currentTimeMillis();
                            if (arrayList.size() > 0) {
                                String str4 = MyUtils.RootDirectoryInsta;
                                MyUtils.startDownloadLogin(arrayList, trim, str4, InstagramActivity.this, asString + ".png", "Instagram_story_" + currentTimeMillis);
                                etText.setText("");
                            }
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                        }

                    } else if (jsonObject.keySet().toArray()[0].equals("showQRModal")) {
                        TrayModel trayModel2 = (TrayModel) new Gson().fromJson(jsonObject, new TypeToken<TrayModel>() {
                        }.getType());
                        callStoriesDetailApi(String.valueOf(trayModel2.getUser().getId()));
                        dbHelper = new DBHelper(getApplicationContext());
                        SQLiteDatabase writableDatabase2 = InstagramActivity.dbHelper.getWritableDatabase();
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put(DBHelper.KEY_NAME, trayModel2.getUser().getUsername());
                        contentValues2.put(DBHelper.KEY_PHOTO, trayModel2.getUser().getProfile_pic_url());
                        Cursor query2 = writableDatabase2.query(DBHelper.TABLE_RECENTLY, null, null, null, null, null, null);
                        Log.d("DatabaseUtils", "" + DatabaseUtils.queryNumEntries(writableDatabase2, str));
                        while (true) {
                            if (i >= DatabaseUtils.queryNumEntries(writableDatabase2, str)) {
                                break;
                            }
                            if (query2.moveToPosition(i)) {
                                int columnIndex3 = query2.getColumnIndex(DBHelper.KEY_ID);
                                int columnIndex4 = query2.getColumnIndex(DBHelper.KEY_NAME);
                                Log.d("nameIndex", "" + columnIndex4);
                                query2.getColumnIndex(DBHelper.KEY_PHOTO);
                                if (query2.getString(columnIndex4).equals(trayModel2.getUser().getUsername())) {
                                    writableDatabase2.delete(str, "_id=" + query2.getInt(columnIndex3), null);
                                    break;
                                }
                            } else {
                                Log.d("mLog", "0 row");
                            }
                            i++;
                        }
                        query2.close();
                        writableDatabase2.insert(str, null, contentValues2);
                        dbHelper.close();

                    } else {
                        ResponseModel responseModel = (ResponseModel) new Gson().fromJson(jsonObject, new TypeToken<ResponseModel>() {
                        }.getType());
                        JsonObject asJsonObject2 = jsonObject.get("graphql").getAsJsonObject().get("shortcode_media").getAsJsonObject();
                        String asString2 = asJsonObject2.get("owner").getAsJsonObject().get("username").getAsString();
                        asJsonObject2.get("owner").getAsJsonObject().get("full_name").getAsString();
                        String asString3 = asJsonObject2.get("owner").getAsJsonObject().get("profile_pic_url").getAsString();
                        try {
                            str3 = asJsonObject2.get("edge_media_to_caption").getAsJsonObject().get("edges").getAsJsonArray().get(0).getAsJsonObject().get("node").getAsJsonObject().get("text").getAsString();
                        } catch (Exception unused2) {
                            str3 = "";
                        }
                        setTags(str3);
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put(ImagesContract.URL, "" + etText.getText().toString());
                        hashMap2.put("intro", "" + str3);
                        hashMap2.put("tags", "" + setTags(str3));
                        String json2 = new Gson().toJson(hashMap2);
                        EdgeSidecarToChildren edgeSidecarToChildren = responseModel.getGraphql().getShortcode_media().getEdge_sidecar_to_children();
                        long currentTimeMillis2 = System.currentTimeMillis();
                        if (edgeSidecarToChildren != null) {
                            String str5 = MyUtils.RootDirectoryInsta;
                            MyUtils.startDownloadFromLink(edgeSidecarToChildren.getEdges(), asString3, str5, InstagramActivity.this, asString2 + ".png", json2, "Instagram_story_" + currentTimeMillis2);
                            etText.setText("");
                        } else if (responseModel.getGraphql().getShortcode_media().isIs_video()) {
                            VideoUrl = responseModel.getGraphql().getShortcode_media().getVideo_url();
                            String str5 = VideoUrl;
                            String str6 = MyUtils.RootDirectoryInsta;
                            InstagramActivity instagramActivity3 = InstagramActivity.this;
                            MyUtils.startNewDownload(str5, str6, instagramActivity3, instagramActivity3.getVideoFilenameFromURL(instagramActivity3.VideoUrl));
                            VideoUrl = "";
                            etText.setText("");
                        } else {
                            PhotoUrl = responseModel.getGraphql().getShortcode_media().getDisplay_resources().get(responseModel.getGraphql().getShortcode_media().getDisplay_resources().size() - 1).getSrc();
                            String str7 = PhotoUrl;
                            String str8 = MyUtils.RootDirectoryInsta;
                            InstagramActivity instagramActivity4 = InstagramActivity.this;
                            MyUtils.startNewDownload(str7, str8, instagramActivity4, instagramActivity4.getImageFilenameFromURL(instagramActivity4.PhotoUrl));
                            PhotoUrl = "";
                            etText.setText("");
                        }
                    }

                } catch (Exception e3) {
                    e = e3;
                    str = " ";
                }
            } catch (Exception e4) {
                e = e4;
                str = " ";
            }

        }

        @Override
        public void onError(Throwable th) {
            th.printStackTrace();
            if (!SharePrefs.getInstance(InstagramActivity.this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue()) {
                final Dialog dialog = new Dialog(InstagramActivity.this);
                dialog.setContentView(R.layout.account_private);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                ((AppCompatTextView) dialog.findViewById(R.id.okk)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return;
            }
            Toast.makeText(getApplicationContext(), "Network Error. Please, try reload mobile Internet or Wi-Fi router", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onComplete() {
        }

    };

    private DisposableObserver<FullDetailModel> storyDetailObserver = new DisposableObserver<FullDetailModel>() {
        public void onNext(FullDetailModel fullDetailModel) {
            RVUserList.setVisibility(View.VISIBLE);
            prLoadingBar.setVisibility(View.GONE);
            try {
                InstagramActivity instagramActivity = InstagramActivity.this;
                storiesListAdapter = new StoriesListAdapter(instagramActivity, fullDetailModel.getReel_feed().getItems());
                RVStories.setAdapter(storiesListAdapter);
                storiesListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onError(Throwable th) {
            prLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        public void onComplete() {
            prLoadingBar.setVisibility(View.GONE);
        }

    };

    private DisposableObserver<StoryModel> storyObserver = new DisposableObserver<StoryModel>() {
        public void onNext(StoryModel storyModel) {
            RVUserList.setVisibility(View.VISIBLE);
            prLoadingBar.setVisibility(View.GONE);
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < storyModel.getTray().size(); i++) {
                    try {
                        if (storyModel.getTray().get(i).getUser().getFull_name() != null) {
                            arrayList.add(storyModel.getTray().get(i));
                        }
                    } catch (Exception unused) {
                    }
                }

                InstagramActivity instagramActivity = InstagramActivity.this;
                InstagramActivity instagramActivity2 = InstagramActivity.this;
                userListAdapter = new UserListAdapter(instagramActivity2, arrayList, instagramActivity2);
                RVUserList.setAdapter(userListAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onError(Throwable th) {
            prLoadingBar.setVisibility(View.GONE);
            th.printStackTrace();
        }

        public void onComplete() {
            prLoadingBar.setVisibility(View.GONE);
        }

    };


    public void onBackPressed() {

        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }

    public void onResume() {
        super.onResume();
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        PasteText();

        new MainAds().showNativeAds(this, null, null, AD_LARGE);
        new MainAds().showBannerAds(this, null);

    }

    private void initViews() {
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        imBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        loginBtn1.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                String obj = etText.getText().toString();
                if (obj.equals("")) {
                    InstagramActivity instagramActivity = InstagramActivity.this;
                    MyUtils.setToast(instagramActivity, instagramActivity.getResources().getString(R.string.enter_url));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    InstagramActivity instagramActivity2 = InstagramActivity.this;
                    MyUtils.setToast(instagramActivity2, instagramActivity2.getResources().getString(R.string.enter_valid_url));
                } else {
                    GetInstagramData();
                }
            }
        });
        tvPaste.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                PasteText();
            }
        });
        TVTitle.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MyUtils.OpenApp(InstagramActivity.this, "com.instagram.android");
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue()) {
            layoutCondition();
            callStoriesApi();
            SwitchLogin.setChecked(true);
        } else {
            SwitchLogin.setChecked(false);
        }
        tvLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent(InstagramActivity.this, LoginActivity.class), 100);
            }
        });
        RLLoginInstagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!SharePrefs.getInstance(InstagramActivity.this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue()) {
                    startActivityForResult(new Intent(InstagramActivity.this, LoginActivity.class), 100);
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(InstagramActivity.this);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharePrefs.getInstance(InstagramActivity.this).putBoolean(SharePrefs.ISINSTALOGIN, false);
                        SharePrefs.getInstance(InstagramActivity.this).putString(SharePrefs.COOKIES, "");
                        SharePrefs.getInstance(InstagramActivity.this).putString(SharePrefs.CSRF, "");
                        SharePrefs.getInstance(InstagramActivity.this).putString(SharePrefs.SESSIONID, "");
                        SharePrefs.getInstance(InstagramActivity.this).putString(SharePrefs.USERID, "");
                        if (SharePrefs.getInstance(InstagramActivity.this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue()) {
                            SwitchLogin.setChecked(true);
                        } else {
                            SwitchLogin.setChecked(false);
                            RVUserList.setVisibility(View.GONE);
                            RVStories.setVisibility(View.GONE);
                            tvViewStories.setText(getResources().getText(R.string.view_stories));
                            tvLogin.setVisibility(View.VISIBLE);
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
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
        RVStories.setLayoutManager(gridLayoutManager2);
        RVStories.setNestedScrollingEnabled(false);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
    }

    public void layoutCondition() {
        tvViewStories.setText(getResources().getString(R.string.stories));
        tvLogin.setVisibility(View.GONE);
    }

    public void GetInstagramData() {
        try {
            MyUtils.createFileFolder();
            String host = new URL(etText.getText().toString()).getHost();
            if (host.equals("www.instagram.com")) {
                setDownloadStory(etText.getText().toString());
            } else {
                MyUtils.setToast(this, getResources().getString(R.string.enter_valid_url));
            }
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
                    if (itemAt.getText().toString().contains("instagram.com")) {
                        etText.setText(itemAt.getText().toString());
                    }
                } else if (clipBoard.getPrimaryClip().getItemAt(0).getText().toString().contains("instagram.com")) {
                    etText.setText(clipBoard.getPrimaryClip().getItemAt(0).getText().toString());
                }
            } else if (stringExtra.contains("instagram.com")) {
                etText.setText(stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUrlWithoutParameters(String str) {
        try {
            URI uri = new URI(str);
            String str2 = null;
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), (String) null, uri.getFragment()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.setToast(this, getResources().getString(R.string.enter_valid_url));
            return "";
        }
    }


    private void setDownloadStory(String Url) {
        String urlWithoutParameters = getUrlWithoutParameters(Url);
        String str2 = urlWithoutParameters + "?__a=1&__d=dis";
        try {
            if (!new MyUtils(this).isNetworkAvailable()) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else if (commonClassForAPI != null) {
                commonClassForAPI.setResult(disposableObserver, str2, "ds_user_id=" + SharePrefs.getInstance(this).getString(SharePrefs.USERID) + "; sessionid=" + SharePrefs.getInstance(this).getString(SharePrefs.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImageFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath().toString()).getName();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return System.currentTimeMillis() + ".png";
        }
    }

    public String getVideoFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath().toString()).getName();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return System.currentTimeMillis() + ".mp4";
        }
    }

    public void onDestroy() {
        super.onDestroy();
        disposableObserver.dispose();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i == 100 && i2 == -1) {
                intent.getStringExtra("key");
                if (SharePrefs.getInstance(this).getBoolean(SharePrefs.ISINSTALOGIN).booleanValue()) {
                    SwitchLogin.setChecked(true);
                    layoutCondition();
                    callStoriesApi();
                    return;
                }
                SwitchLogin.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void callStoriesApi() {
        try {
            if (!new MyUtils(this).isNetworkAvailable()) {
                MyUtils.setToast(this, getResources().getString(R.string.no_net_conn));
            } else if (commonClassForAPI != null) {
                prLoadingBar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = commonClassForAPI;
                DisposableObserver<StoryModel> disposableObserver = storyObserver;
                commonClassForAPI2.getStories(disposableObserver, "ds_user_id=" + SharePrefs.getInstance(this).getString(SharePrefs.USERID) + "; sessionid=" + SharePrefs.getInstance(this).getString(SharePrefs.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userListClick(int i, TrayModel trayModel) {
        callStoriesDetailApi(String.valueOf(trayModel.getUser().getPk()));
    }

    private void callStoriesDetailApi(String str) {
        try {
            if (!new MyUtils(this).isNetworkAvailable()) {
                MyUtils.setToast(this, getResources().getString(R.string.no_net_conn));
            } else if (commonClassForAPI != null) {
                prLoadingBar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = commonClassForAPI;
                DisposableObserver<FullDetailModel> disposableObserver = storyDetailObserver;
                commonClassForAPI2.getFullDetailFeed(disposableObserver, str, "ds_user_id=" + SharePrefs.getInstance(this).getString(SharePrefs.USERID) + "; sessionid=" + SharePrefs.getInstance(this).getString(SharePrefs.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String setTags(String str) {
        String str2;
        if (!str.contains("#")) {
            return "";
        }
        String[] split = str.split("#");
        String str3 = "";
        for (int i = 1; i < split.length; i++) {
            String valueOf = String.valueOf(split[i].charAt(0));
            if (split[i].length() > 1) {
                if (valueOf.equals(" ")) {
                    split[i] = split[i].replace(" ", "");
                }
                if (split[i].contains(" ")) {
                    str2 = split[i].substring(0, split[i].indexOf(" ") + 1);
                } else if (split[i].contains("\n")) {
                    str2 = split[i].replace("\n", " ");
                } else {
                    str2 = split[i].substring(0, split[i].length() - 1);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("substring ");
                sb.append(str2);
                Log.e("substring: ", sb.toString());
                if (str2.length() > 0) {
                    str3 = str3 + "#" + str2;
                }
            }
        }
        return str3;
    }

}