package com.videodown.allhd.app.Activity.StoryDownloader;


import static com.videodown.allhd.app.AUtils.MyUtils.HideLoading;
import static com.videodown.allhd.app.AUtils.MyUtils.ShowLoading;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.videodown.allhd.app.Ads.Constant;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.ASplash.CdActivityBase;
import com.videodown.allhd.app.AUtils.InstaPhoto;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.google.gms.ads.BackInterAds;
import com.google.gms.ads.MainAds;
import com.preference.PowerPreference;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class InstagramViaBrowserActivity<S extends Scrollable> extends CdActivityBase implements ObservableScrollViewCallbacks {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    public static String URL = "https://www.instagram.com/accounts/login/";
    Context context;
    AppCompatImageView cpb_downloadButton, cpb_downloadButton_photo;
    private int downloadTasksCounter = 0;
    String fileN = null;
    Handler h_photo = null;
    Handler h_video = null;
    int load_counter = 0;
    public S mScrollable;
    boolean result;
    View rootView;
    boolean start_download = false;
    String urlString;
    public ObservableWebView webo;

    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    public void onScrollChanged(int i, boolean z, boolean z2) {
    }

    public void setValue(int i) {
    }

    static int access$710(InstagramViaBrowserActivity InstagramViaBrowserActivity) {
        int i = InstagramViaBrowserActivity.downloadTasksCounter;
        InstagramViaBrowserActivity.downloadTasksCounter = i - 1;
        return i;
    }

    public static boolean isConnectingToInternet(Context context2) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private static AppCompatTextView TV_diloagValue, TV_DialogMsg;
    public static Dialog mLoadDialog;
    public static int downloadIdNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_via_browser);


        context = this;
        h_photo = new Handler();
        h_video = new Handler();
        rootView = findViewById(R.id.swipeRecyclerView);
        cpb_downloadButton = findViewById(R.id.circular_progress_bar_3);
        cpb_downloadButton_photo = findViewById(R.id.circular_progress_bar_4);
        cpb_downloadButton_photo.setVisibility(View.GONE);
        cpb_downloadButton.setVisibility(View.GONE);
        mScrollable = (S) gotoFB();
        mScrollable.setScrollViewCallbacks(this);
        result = checkPermission();

        if (result) {
            checkFolder();
        }
        if (!isConnectingToInternet(this)) {
            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_LONG).show();
        }

        cpb_downloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!start_download) {
                    InstagramViaBrowserActivity InstagramViaBrowserActivity = InstagramViaBrowserActivity.this;
                    InstagramViaBrowserActivity.newDownload(InstagramViaBrowserActivity.urlString);
                    InstagramViaBrowserActivity InstagramViaBrowserActivity2 = InstagramViaBrowserActivity.this;
                    InstagramViaBrowserActivity2.start_download = true;
                }
            }
        });

        cpb_downloadButton_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!start_download) {
                    InstagramViaBrowserActivity InstagramViaBrowserActivity = InstagramViaBrowserActivity.this;
                    InstagramViaBrowserActivity.newPhotoDownload(InstagramViaBrowserActivity.urlString);
                    InstagramViaBrowserActivity InstagramViaBrowserActivity2 = InstagramViaBrowserActivity.this;
                    InstagramViaBrowserActivity2.start_download = true;
                }
            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void newPhotoDownload(String str) {
        if (!urlString.isEmpty()) {
            PhotoDownloadTask photoDownloadTask = new PhotoDownloadTask(this);
            photoDownloadTask.setIsVideo(false);
            photoDownloadTask.execute(new String[]{urlString});
            return;
        }
    }

    public class PhotoDownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private Button cpb;
        boolean isVideo = true;
        private PowerManager.WakeLock mWakeLock;

        public PhotoDownloadTask(Context context2) {
            context = context2;
        }

        public void setIsVideo(boolean z) {
            isVideo = z;
        }

        public String doInBackground(String... strArr1) {
            String error = null;
            InputStream inputStream;
            Throwable th;
            Exception e;
            File file;
            FileOutputStream fileOutputStream = null;
            FileOutputStream fileOutputStream2 = null;
            HttpURLConnection strArr;
            try {
                try {
                    strArr = (HttpURLConnection) new URL(strArr1[0]).openConnection();
                    try {
                        strArr.connect();
                        if (strArr.getResponseCode() != 200) {
                            String str = "Server returned HTTP " + strArr.getResponseCode() + StringUtils.SPACE + strArr.getResponseMessage();
                            if (strArr != null) {
                                strArr.disconnect();
                            }
                            return str;
                        }
                        int contentLength = strArr.getContentLength();
                        inputStream = strArr.getInputStream();
                        try {
                            if (isVideo) {
                                fileN = "InstaDownloader+" + UUID.randomUUID().toString().substring(0, 10) + ".mp4";
                                StringBuilder sb = new StringBuilder();
                                sb.append(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
                                file = new File(sb.toString(), fileN);

                            } else {
                                fileN = "InstaDownloader+" + UUID.randomUUID().toString().substring(0, 10) + ".jpg";
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
                                File file2 = new File(sb2.toString(), fileN);
                                File file3 = new File(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
                                if (!file3.exists()) {
                                    file3.mkdirs();
                                }
                                file = file2;
                            }
                            fileOutputStream = new FileOutputStream(file);
                        } catch (Exception e2) {
                            e = e2;
                        }
                        try {
                            byte[] bArr = new byte[4096];
                            long j = 0;
                            while (true) {
                                int read = inputStream.read(bArr);
                                if (read == -1) {
                                    try {
                                        fileOutputStream.close();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException unused) {
                                    }
                                    if (strArr != null) {
                                        strArr.disconnect();
                                    }
                                    return null;
                                } else if (isCancelled()) {
                                    break;
                                } else {
                                    j += read;
                                    if (contentLength > 0) {
                                        publishProgress(Integer.valueOf((int) ((100 * j) / contentLength)));
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                            }
                        } catch (Exception e3) {
                            e = e3;
                            fileOutputStream2 = fileOutputStream;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                                }
                            });
                            String exc = e.toString();
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException unused2) {
                                    if (strArr != null) {
                                        strArr.disconnect();
                                    }
                                    return exc;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return exc;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException unused3) {
                                    if (strArr != null) {
                                        strArr.disconnect();
                                    }
                                    throw th;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                        if (strArr != null) {
                            strArr.disconnect();
                        }
                        return null;
                    } catch (Exception e4) {
                        e = e4;
                        inputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        inputStream = null;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e5) {
                e = e5;
                strArr = null;
                inputStream = null;
            } catch (Throwable th5) {
                th = th5;
                strArr = null;
                inputStream = null;
            }
            return error;
        }

        public void onPreExecute() {
            super.onPreExecute();
            mWakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
            mWakeLock.acquire();

            mLoadDialog = ShowLoading(InstagramViaBrowserActivity.this);
            TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
            TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
            TV_diloagValue.setVisibility(View.VISIBLE);
            TV_DialogMsg.setText("Downloading...");

        }

        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
            runOnUiThread(new Runnable() {
                public void run() {
                    TV_diloagValue.setText("" + numArr[0].intValue() + " %");
                }
            });
        }

        public void onPostExecute(String str) {
            mWakeLock.release();
            HideLoading(mLoadDialog);

            if (str != null) {
                Context context2 = context;
                Toast.makeText(context2, "Link Not Found..", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                cpb_downloadButton_photo.setVisibility(View.GONE);
                            }

                        });
                        cpb_downloadButton_photo.startAnimation(loadAnimation);
                        InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                    }
                }, 2000);
            } else {
                urlString = "";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                cpb_downloadButton_photo.setVisibility(View.GONE);
                            }
                        });
                        cpb_downloadButton_photo.startAnimation(loadAnimation);
                        InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                    }
                }, 2000);
            }

            String str2 = MyUtils.RootDirectoryInsta;
            InstagramViaBrowserActivity InstagramViaBrowserActivity = InstagramViaBrowserActivity.this;
            MediaScannerConnection.scanFile(InstagramViaBrowserActivity, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() + str2 + fileN}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("-> uri=");
                    sb.append(uri);
                }
            });

        }
    }

    public void newDownload(String str) {
        if (!urlString.isEmpty()) {
            new DownloadTask(this).execute(new String[]{urlString});
            return;
        }
    }

    public class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private Button cpb;
        boolean isVideo = true;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context2) {
            context = context2;
        }

        public void setIsVideo(boolean z) {
            isVideo = z;
        }

        public String doInBackground(String... strArr) {

            String error = null;
            InputStream inputStream;
            Throwable th;
            Exception e;
            File file;
            FileOutputStream fileOutputStream = null;
            FileOutputStream fileOutputStream2 = null;
            HttpURLConnection strArr2 = null;

            try {
                try {
                    strArr2 = (HttpURLConnection) new URL(strArr[0]).openConnection();
                    try {
                        strArr2.connect();
                        if (strArr2.getResponseCode() != 200) {
                            String str = "Server returned HTTP " + strArr2.getResponseCode() + StringUtils.SPACE + strArr2.getResponseMessage();
                            if (strArr2 != null) {
                                strArr2.disconnect();
                            }
                            return str;
                        }
                        int contentLength = strArr2.getContentLength();
                        inputStream = strArr2.getInputStream();

                        try {
                            if (isVideo) {
                                fileN = "InstaDownloader+" + UUID.randomUUID().toString().substring(0, 10) + ".mp4";
                                StringBuilder sb = new StringBuilder();
                                sb.append(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
                                file = new File(sb.toString(), fileN);

                            } else {
                                fileN = "InstaDownloader+" + UUID.randomUUID().toString().substring(0, 10) + ".jpg";
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
                                File file2 = new File(sb2.toString(), fileN);
                                File file3 = new File(MyUtils.RootDirectoryInstaShow.getAbsolutePath()/* + Constants.PHOTO_FOLDER_NAME*/);
                                if (!file3.exists()) {
                                    file3.mkdirs();
                                }
                                file = file2;
                            }

                            fileOutputStream = new FileOutputStream(file);

                        } catch (Exception e2) {
                            e = e2;
                            error = e.getMessage();
                        }

                        try {
                            byte[] bArr = new byte[4096];
                            long j = 0;
                            while (true) {
                                int read = inputStream.read(bArr);
                                if (read == -1) {
                                    try {
                                        fileOutputStream.close();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                    } catch (IOException ee) {
                                        error = ee.getMessage();
                                    }
                                    if (strArr2 != null) {
                                        strArr2.disconnect();
                                    }
                                    return null;
                                } else if (isCancelled()) {
                                    break;
                                } else {
                                    j += read;
                                    if (contentLength > 0) {
                                        publishProgress(Integer.valueOf((int) ((100 * j) / contentLength)));
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                            }

                        } catch (Exception e3) {
                            error = e3.getMessage();
                            e = e3;
                            fileOutputStream2 = fileOutputStream;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                                }
                            });
                            String exc = e.toString();
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e33) {
                                    error = e33.getMessage();
                                    if (strArr2 != null) {
                                        strArr2.disconnect();
                                    }
                                    return exc;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return exc;
                        } catch (Throwable th2) {
                            error = th2.getMessage();
                            th = th2;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException eee) {
                                    error = eee.getMessage();
                                    if (strArr2 != null) {
                                        strArr2.disconnect();
                                    }
                                    throw th;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                        if (strArr2 != null) {
                            strArr2.disconnect();
                        }
                        return null;
                    } catch (Exception e4) {
                        error = e4.getMessage();
                        e = e4;
                        inputStream = null;
                    } catch (Throwable e44) {
                        error = e44.getMessage();
                        inputStream = null;
                    }
                } catch (Throwable th4) {
                    error = th4.getMessage();
                    th = th4;
                }
            } catch (Exception e5) {
                error = e5.getMessage();
                e = e5;
                strArr2 = null;
                inputStream = null;
            } catch (Throwable th5) {
                error = th5.getMessage();
                th = th5;
                strArr2 = null;
                inputStream = null;
            }
            return error;
        }

        public void onPreExecute() {
            super.onPreExecute();
            mWakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
            mWakeLock.acquire();
            mLoadDialog = ShowLoading(InstagramViaBrowserActivity.this);
            TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
            TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
            TV_diloagValue.setVisibility(View.VISIBLE);
            TV_DialogMsg.setText("Downloading...");

        }

        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
            runOnUiThread(new Runnable() {
                public void run() {
                    TV_diloagValue.setText("" + numArr[0].intValue() + " %");
                }
            });
        }

        public void onPostExecute(String str) {
            mWakeLock.release();
            HideLoading(mLoadDialog);
            if (str != null) {
                Context context2 = context;
                Toast.makeText(context2, "Link Not Found..", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                cpb_downloadButton.setVisibility(View.GONE);
                            }
                        });
                        cpb_downloadButton.startAnimation(loadAnimation);
                        InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                    }
                }, 2000);
            } else {
                urlString = "";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                cpb_downloadButton.setVisibility(View.GONE);
                            }
                        });
                        cpb_downloadButton.startAnimation(loadAnimation);
                        InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                    }
                }, 2000);
            }

            String str2 = MyUtils.RootDirectoryInsta;
            InstagramViaBrowserActivity InstagramViaBrowserActivity = InstagramViaBrowserActivity.this;
            MediaScannerConnection.scanFile(InstagramViaBrowserActivity, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() + str2 + fileN}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("-> uri=");
                    sb.append(uri);
                }
            });

        }
    }

    @SuppressLint("ResourceType")
    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle((CharSequence) "Permission necessary");
            builder.setMessage((CharSequence) "Write Storage permission is necessary to Download Images and Videos!!!");
            builder.setPositiveButton(17039379, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(InstagramViaBrowserActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, InstagramViaBrowserActivity.MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
            });
            builder.create().show();
        }
        return false;
    }

    @SuppressLint("ResourceType")
    public void checkAgain() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle((CharSequence) "Permission necessary");
            builder.setMessage((CharSequence) "Write Storage permission is necessary to Download Images and Videos!!!");
            builder.setPositiveButton(17039379, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(InstagramViaBrowserActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, InstagramViaBrowserActivity.MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
            });
            builder.create().show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                checkAgain();
            } else {
                checkFolder();
            }
        }
    }

    public void checkFolder() {
        File file = new File(MyUtils.RootDirectoryInstaShow.getAbsolutePath());
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @JavascriptInterface
    public ObservableWebView gotoFB() {
        webo = (ObservableWebView) findViewById(R.id.webViewFb);
        webo.getSettings().setJavaScriptEnabled(true);
        webo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webo.getSettings().setDomStorageEnabled(true);
        setDesktopMode(webo, true);
        String newUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0";
        webo.getSettings().setUserAgentString(newUA);
        webo.getSettings().setDefaultTextEncodingName("UTF-8");
        webo.addJavascriptInterface(this, "FBDownloader");
        webo.getSettings().setPluginState(WebSettings.PluginState.ON);
        webo.getSettings().setSupportMultipleWindows(false);
        webo.getSettings().setBuiltInZoomControls(true);
        webo.getSettings().setDisplayZoomControls(true);
        webo.getSettings().setUseWideViewPort(true);
        webo.getSettings().setLoadWithOverviewMode(true);
        webo.getSettings().setAllowFileAccess(true);
        webo.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                setValue(i);
                super.onProgressChanged(webView, i);
            }
        });
        webo.setWebViewClient(new WebViewClient() {
            public void onLoadResource(WebView webView, String str) {
            }

            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                return false;

            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            public void onPageFinished(WebView webView, String str) {
                webo.loadUrl(MyUtils.My_JAVASCRIPT);
                load_counter = 0;
                super.onPageFinished(webView, str);
                if (Build.VERSION.SDK_INT < 21) {
                    CookieSyncManager.createInstance(context).sync();
                }
            }
        });
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(this).sync();
        }
        instance.setAcceptCookie(true);
        webo.loadUrl(URL);
        return webo;
    }


    public void setListnerPhoto() {
        runOnUiThread(new Runnable() {
            public void run() {
                cpb_downloadButton_photo.setVisibility(View.VISIBLE);
                cpb_downloadButton_photo.startAnimation(AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_in));
                h_photo.removeCallbacksAndMessages((Object) null);
                h_photo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!start_download && cpb_downloadButton_photo.getVisibility() == View.VISIBLE) {
                            Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                            loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                                public void onAnimationRepeat(Animation animation) {
                                }

                                public void onAnimationStart(Animation animation) {
                                }

                                public void onAnimationEnd(Animation animation) {
                                    cpb_downloadButton_photo.setVisibility(View.GONE);
                                }
                            });
                            cpb_downloadButton_photo.startAnimation(loadAnimation);
                            InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                        }
                    }
                }, 5000);
            }
        });
    }

    public void setListnerVideo() {
        runOnUiThread(new Runnable() {
            public void run() {
                cpb_downloadButton.setVisibility(View.VISIBLE);
                cpb_downloadButton.startAnimation(AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_in));
                h_video.removeCallbacksAndMessages((Object) null);
                h_video.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!start_download && cpb_downloadButton.getVisibility() == View.VISIBLE) {
                            Animation loadAnimation = AnimationUtils.loadAnimation(InstagramViaBrowserActivity.this, R.anim.fade_out);
                            loadAnimation.setAnimationListener(new Animation.AnimationListener() {
                                public void onAnimationRepeat(Animation animation) {
                                }

                                public void onAnimationStart(Animation animation) {
                                }

                                public void onAnimationEnd(Animation animation) {
                                    cpb_downloadButton.setVisibility(View.GONE);
                                }

                            });
                            cpb_downloadButton.startAnimation(loadAnimation);
                            InstagramViaBrowserActivity.access$710(InstagramViaBrowserActivity.this);
                        }
                    }
                }, 5000);
            }
        });

    }

    @JavascriptInterface
    public void processVideo(String str) {
        Log.e("WEBVIEWJS", "RUN");
        load_counter = 0;
        if (downloadTasksCounter < 1 && !str.isEmpty()) {
            Log.e("WEBVIEWJS", str);
            start_download = false;
            downloadTasksCounter++;
            load_counter++;
            new Bundle().putString("vid_data", str);
            urlString = str;
            new Thread() {
                public void run() {
                    try {
                        setListnerVideo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @JavascriptInterface
    public void processPhoto(String str) {
        if (!str.isEmpty()) {
            InstaPhoto instaPhoto = new InstaPhoto();
            instaPhoto.parseStr(str);
            String regular = instaPhoto.getRegular();
            start_download = false;
            new Bundle().putString("vid_data", regular);
            urlString = regular;
            new Thread() {
                public void run() {
                    try {
                        setListnerPhoto();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void setDesktopMode(WebView webView, boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
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
    public void onBackPressed() {

        new MainAds().showBackInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

    }

}