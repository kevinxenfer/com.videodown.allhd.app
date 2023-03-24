package com.videodown.allhd.app.AUtils.XAWebFB;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.videodown.allhd.app.Ads.Application;
import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.FacebookViaBrowserActivity;
import com.videodown.allhd.app.AUtils.MyUtils;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;

import org.json.JSONObject;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowserFragment extends Fragment implements View.OnClickListener {
    public static WebView webViewBrowser;
    private TranslateAnimation anim;

    public ImageView buttonDownload;

    public int counter = 0;
    public boolean download = false;
    public String downloadUrl = "";

    public LinearLayout layoutProgress;
    public static int downloadIdNew = 0;
    public static TextView TV_diloagValue, TV_DialogMsg;
    public static Dialog mLoadDialog;

    static int access$508(BrowserFragment x0) {
        int i = x0.counter;
        x0.counter = i + 1;
        return i;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        webViewBrowser = view.findViewById(R.id.webView_browser);
        layoutProgress = view.findViewById(R.id.layout_progress);
        buttonDownload = view.findViewById(R.id.button_download);
        buttonDownload.setOnClickListener(this);
        webViewBrowser.getSettings().setJavaScriptEnabled(true);
        webViewBrowser.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewBrowser.getSettings().setDisplayZoomControls(false);
        webViewBrowser.getSettings().setSupportMultipleWindows(false);
        webViewBrowser.getSettings().setBuiltInZoomControls(true);
        webViewBrowser.getSettings().setDisplayZoomControls(true);
        webViewBrowser.getSettings().setUseWideViewPort(true);
        webViewBrowser.getSettings().setLoadWithOverviewMode(true);
        webViewBrowser.setWebViewClient(new MyWebViewClient());
        webViewBrowser.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; sdk Build/KRT16L) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");

        WebSettings localWebSettings = webViewBrowser.getSettings();
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setAllowFileAccess(true);
        webViewBrowser.clearCache(false);
        CookieSyncManager.createInstance(getActivity());
        CookieManager.getInstance().setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();
        webViewBrowser.loadUrl("www.facebook.com");
        webViewBrowser.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 85 || newProgress <= 1) {
                    layoutProgress.setVisibility(View.GONE);
                } else {
                    layoutProgress.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @RequiresApi(api = 24)
    public void onClick(View v) {
        if (v == buttonDownload) {
            String url = webViewBrowser.getUrl();
            if (!url.contains("instagram.com")) {
                Log.d("DownloadUrl", "Url : " + url);
                if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {
                    FacebookViaBrowserActivity.showFileExistPopUpDialog("", "", getActivity());
                    showDownloadBubble(false);
                } else if (!url.contains("dailymotion.com/video/") && !url.contains("vimeo.com") && !downloadUrl.contains("https://tubidy.mobi/watch.php?id=") && !url.contains("facebook.com") && !downloadUrl.contains("muscdn.com")) {
                    Toast.makeText(getContext(), "opps!!!video link not found", Toast.LENGTH_SHORT).show();
                } else if (url.contains("vimeo.com")) {
                    if (url.contains("https://vimeo.com/watch")) {
                        Toast.makeText(getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    } else if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {
                        showDownloadBubble(false);
                        FacebookViaBrowserActivity.showFileExistPopUpDialog("", "", getActivity());
                    } else {
                        callVimeoVideoAPI(new File(url).getName());
                    }
                } else if (downloadUrl.contains("https://tubidy.mobi/watch.php?id=")) {
                    String tudibyVideoUrl = webViewBrowser.getUrl();
                    if (!tudibyVideoUrl.endsWith(".mp4")) {
                        Toast.makeText(getContext(), "Video Link not Found", Toast.LENGTH_SHORT).show();
                        showDownloadBubble(false);
                        buttonDownload.setVisibility(View.INVISIBLE);
                        return;
                    }
                    showDownloadManager(tudibyVideoUrl);
                } else if (downloadUrl.contains("muscdn.com")) {
                    showDownloadManager(downloadUrl);
                } else if (url.contains("facebook.com")) {
                    String url2 = downloadUrl;
                    if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url2)) {
                        showDownloadBubble(false);
                        FacebookViaBrowserActivity.showFileExistPopUpDialog("", "", getActivity());
                    } else if (url2.startsWith("https://video.") || url2.contains(".mp4?_")) {
                        startDownload(url2, getActivity());
                    } else {
                        Toast.makeText(getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (DBDownloadManager.getInstance(getActivity()).isUrlContains(url)) {
                FacebookViaBrowserActivity.showFileExistPopUpDialog("", "", getActivity());
                showDownloadBubble(false);
            } else if (!downloadUrl.equals("")) {
                String url3 = downloadUrl;
                if (url3.contains("https://scontent.cdninstagram.com/")) {
                    showDownloadManager(downloadUrl);
                    return;
                }
                Log.d("DownloadUrl", "Url : " + url3);
                if (downloadUrl.contains(".mp4?_nc_ht=")) {
                    showDownloadManager(downloadUrl);
                } else {
                    Toast.makeText(getContext(), "video link not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "video link not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {

        private Map<String, Boolean> loadedUrls;

        private MyWebViewClient() {
            loadedUrls = new HashMap();
        }

        public void onLoadResource(WebView view, String url) {
            try {
                if (view.getUrl() != null) {
                    if (BrowserFragment.this.getActivity() != null) {
                    }
                    if (view.getUrl().contains("https://www.instagram.com/")) {
                        showDownloadBubble(true);
                        buttonDownload.setVisibility(View.VISIBLE);
                    }
                    if (view.getUrl().contains("dailymotion.com/video/")) {
                        showDownloadBubble(true);
                    } else {
                        if (!view.getUrl().equals("https://vimeo.com/watch/") && !view.getUrl().equals("https://vimeo.com/home") && !view.getUrl().equals("https://vimeo.com/")) {
                            if (!view.getUrl().equals("https://vimeo.com/search")) {
                                if (!url.contains("https://tubidy.mobi/watch.php?id=")) {
                                    if (!url.contains("muscdn.com")) {
                                        if (url.contains("fna.fbcdn.net")) {
                                            showDownloadBubble(true);
                                            buttonDownload.setVisibility(View.VISIBLE);
                                            String unused = downloadUrl = url;
                                            Log.d("link-----", url);
                                        } else {
                                            if (!url.contains("https://scontent.cdninstagram.com/")) {
                                                if (!url.contains("https://www.instagram.com/")) {
                                                    showDownloadBubble(false);
                                                }
                                            }
                                            showDownloadBubble(true);
                                            buttonDownload.setVisibility(View.VISIBLE);
                                            if (BrowserFragment.this.download) {
                                                boolean unused2 = download = false;
                                                super.onLoadResource(view, url);
                                            } else if (url.toUpperCase().contains(".MP4") && !url.toUpperCase().endsWith(".ANI") && !url.toUpperCase().endsWith(".BMP") && !url.toUpperCase().endsWith(".CAL") && !url.toUpperCase().endsWith(".FAX") && !url.toUpperCase().endsWith(".GIF") && !url.toUpperCase().endsWith(".IMG") && !url.toUpperCase().endsWith(".JBG") && !url.toUpperCase().endsWith(".JPE") && !url.toUpperCase().endsWith(".JPEG") && !url.toUpperCase().endsWith(".JPG") && !url.toUpperCase().endsWith(".MAC") && !url.toUpperCase().endsWith(".PBM") && !url.toUpperCase().endsWith(".PCD") && !url.toUpperCase().endsWith(".PCX") && !url.toUpperCase().endsWith(".PCT") && !url.toUpperCase().endsWith(".PGM") && !url.toUpperCase().endsWith(".PNG") && !url.toUpperCase().endsWith(".PPM") && !url.toUpperCase().endsWith(".PSD") && !url.toUpperCase().endsWith(".RAS") && !url.toUpperCase().endsWith(".TGA") && !url.toUpperCase().endsWith(".TIFF")) {
                                                if (!url.toUpperCase().endsWith(".WMF")) {
                                                    BrowserFragment.access$508(BrowserFragment.this);
                                                    if (BrowserFragment.this.counter == 1) {
                                                        try {
                                                            String unused3 = downloadUrl = url;
                                                            boolean unused4 = download = true;
                                                            int unused5 = counter = 0;
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                showDownloadBubble(true);
                                buttonDownload.setVisibility(View.VISIBLE);
                                String unused6 = downloadUrl = url;
                                Log.d("download----", downloadUrl);
                            }
                        }
                        showDownloadBubble(true);
                        buttonDownload.setVisibility(View.VISIBLE);
                    }
                } else {
                    buttonDownload.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            super.onLoadResource(view, url);
        }

        public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
            if (BrowserFragment.this.getActivity() != null) {
            }
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            if (!paramString.contains("http://tubidy.mobi/watch.php/id=")) {
                return false;
            }
            if (BrowserFragment.this.download) {
                boolean unused = download = false;
                return super.shouldOverrideUrlLoading(paramWebView, paramString);
            } else if (!paramString.toUpperCase().contains(".MP4") && !paramString.toUpperCase().contains(".3GP") && !paramString.toUpperCase().contains(".PDF") && !paramString.toUpperCase().contains(".APK") && !paramString.toUpperCase().contains(".MP3") && !paramString.toUpperCase().contains(".APE") && !paramString.toUpperCase().contains(".AVI") && !paramString.toUpperCase().contains(".WMV") && !paramString.toUpperCase().contains(".WAV") && !paramString.toUpperCase().contains(".ASF") && !paramString.toUpperCase().contains(".MPG") && !paramString.toUpperCase().contains(".AMR") && !paramString.toUpperCase().contains(".OGG") && !paramString.toUpperCase().contains(".OGA") && !paramString.toUpperCase().contains(".OGV") && !paramString.toUpperCase().contains(".WMA") && !paramString.toUpperCase().contains(".DOC") && !paramString.toUpperCase().contains(".PPT") && !paramString.toUpperCase().contains(".PPS") && !paramString.toUpperCase().contains(".PPX") && !paramString.toUpperCase().contains(".XLS") && !paramString.toUpperCase().contains(".CHM") && !paramString.toUpperCase().contains(".TXT") && !paramString.toUpperCase().contains(".ZIP") && !paramString.toUpperCase().contains(".RAR") && !paramString.toUpperCase().contains(".MKV") && !paramString.toUpperCase().contains(".SWF") && !paramString.toUpperCase().contains(".FLV") && !paramString.toUpperCase().contains(".PCS") && !paramString.toUpperCase().contains(".MOV")) {
                return super.shouldOverrideUrlLoading(paramWebView, paramString);
            } else {
                BrowserFragment.access$508(BrowserFragment.this);
                if (BrowserFragment.this.counter == 1) {
                    try {
                        String unused2 = downloadUrl = String.valueOf(Uri.parse(paramString));
                        boolean unused3 = download = true;
                        int unused4 = counter = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }

        @TargetApi(11)
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

    }

    public void showDownloadBubble(boolean isShow) {

        if (isShow) {
            buttonDownload.setVisibility(View.VISIBLE);
            if (anim == null) {
                anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, 15.0f);
                anim.setRepeatCount(-1);
                anim.setRepeatMode(2);
                anim.setDuration(100);
                anim.setFillAfter(true);
                buttonDownload.setClickable(true);
                buttonDownload.startAnimation(anim);
                return;
            }
            return;
        }
        buttonDownload.setVisibility(View.GONE);
        TranslateAnimation translateAnimation = anim;
        if (translateAnimation != null && translateAnimation.isInitialized()) {
            anim.cancel();
            anim = null;
        }

    }

    private void callVimeoVideoAPI(String videoId) {
        try {
            AndroidUtils.vimeoVodeoCallAPI("http://player.vimeo.com/video/" + videoId + "/").vimeoVideoUrl().enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            showDownloadManager(new JSONObject(responseBody.string()).getJSONObject("request").getJSONObject("files").getJSONArray("progressive").getJSONObject(0).getString("url"));
                            return;
                        }
                        Toast.makeText(BrowserFragment.this.getContext(), "Video link not found", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AndroidUtils.dismissProgress();
                    t.printStackTrace();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
    }


    @SuppressLint("WrongConstant")
    public void showDownloadManager(String url) {
        if (MimeTypeMap.getFileExtensionFromUrl(url).equals("")) {
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        try {
            if (Application.getInstance().downloadIntent == null) {
                Application.getInstance().downloadIntent = new Intent(getActivity(), DownloadService.class);
                getActivity().bindService(Application.getInstance().downloadIntent, Application.getInstance().downloadConnection, 1);
                getActivity().startService(Application.getInstance().downloadIntent);
            }
        } catch (Exception se) {
            Log.d("Test", se.getMessage());
            se.printStackTrace();
        }
        try {
            String fileName = Application.getInstance().downloadService.getFileName(url.replace("%20", " "));
            Application.getInstance().downloadService.addDownloadData(url, "", fileName, new File(Application.getInstance().downloadService.getFilePath(fileName)).getAbsolutePath());
            showDownloadBubble(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showFaceBookDownloadManager(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension.equals("")) {
            extension = ".mp4";
        }
        try {
            if (Application.getInstance().downloadIntent == null) {
                Application.getInstance().downloadIntent = new Intent(getActivity(), DownloadService.class);
                getActivity().bindService(Application.getInstance().downloadIntent, Application.getInstance().downloadConnection, Context.BIND_AUTO_CREATE);
                getActivity().startService(Application.getInstance().downloadIntent);
            }
        } catch (Exception se) {
            Log.d("Test", se.getMessage());
            se.printStackTrace();
        }
        try {
            String fileName = "facebook" + url.substring(url.length() - 8) + "." + extension;
            Application.getInstance().downloadService.addDownloadData(url, "", fileName, new File(Application.getInstance().downloadService.getFilePath(fileName)).getAbsolutePath());
            showDownloadBubble(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDownload(String url, Activity activity) {

        mLoadDialog = ShowLoading(activity);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");

        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension.equals("")) {
            extension = ".mp4";
        }
        String fileName = "facebook" + url.substring(url.length() - 8) + "." + extension;
        downloadIdNew = PRDownloader.download(url, MyUtils.RootDirectoryFacebookShow.getAbsolutePath(), fileName).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
            @Override
            public void onStartOrResume() {
            }
        }).setOnPauseListener(new OnPauseListener() {
            @Override
            public void onPause() {
            }
        }).setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                downloadIdNew = 0;
                HideLoading(mLoadDialog);
            }
        }).setOnProgressListener(new OnProgressListener() {
            @Override
            public void onProgress(final Progress progress) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        TV_diloagValue.setText("" + progressPercent + " %");
                    }
                });
            }
        }).start(new OnDownloadListener() {
            @Override
            public void onDownloadComplete() {
                HideLoading(mLoadDialog);
            }

            @Override
            public void onError(Error error) {
                HideLoading(mLoadDialog);
            }

        });
    }

    public static Dialog ShowLoading(Activity activity) {
        Dialog dialog = new Dialog(activity);
        try {
            dialog.setContentView(R.layout.myprogress_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            new MainAds().showNativeAds(activity, dialog, null, AdUtils.AD_LARGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static void HideLoading(Dialog d) {
        if (d != null && d.isShowing()) {
            d.dismiss();
        }
    }

}
