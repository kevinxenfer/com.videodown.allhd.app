package com.videodown.allhd.app.AUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;

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
import com.videodown.allhd.app.Activity.StoryModel.Edge;
import com.videodown.allhd.app.AllStory.story.ItemModel;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.gms.ads.AdUtils;
import com.google.gms.ads.MainAds;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inet.ipaddr.Address;

public class MyUtils {

    public static final String ROOTDIRECTORYCHINGARI = "/AllHDVideoDownloader /Chingari/";
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Chingari");
    public static final String ROOTDIRECTORYJOSH = "/AllHDVideoDownloader /Josh/";
    public static final File ROOTDIRECTORYJOSHSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Josh");
    public static final String ROOTDIRECTORYMITRON = "/AllHDVideoDownloader /Mitron/";
    public static final File ROOTDIRECTORYMITRONSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Mitron");
    public static final File ROOTDIRECTORYMOJSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Moj");
    public static final File ROOTDIRECTORYMXSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Mxtakatak");

    public static final File ROOTDIRECTORYWEBFB = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Webfacebook");

    public static String RootDirectoryFacebook = "/AllHDVideoDownloader /Facebook/";
    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Facebook");
    public static File RootDirectoryweb = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "WebDownloaderInsta");
    public static String RootDirectoryInsta = "/AllHDVideoDownloader /Insta/";
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Insta");
    public static File RootDirectoryLikeeShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Likee");
    public static String RootDirectoryRoposo = "/AllHDVideoDownloader /Roposo/";
    public static File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Roposo");
    public static String RootDirectoryShareChat = "/AllHDVideoDownloader /ShareChat/";
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /ShareChat");
    public static File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /SnackVideo");
    public static File RootDirectoryTikTokShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /TikTok");
    public static String RootDirectoryTwitter = "/AllHDVideoDownloader /Twitter/";
    public static File RootDirectoryDownloadPath = new File("/storage/emulated/0/All Downloader App/Videos");

    public static final String directoryInstaShoryDirectorydownload_audio = "/AllHDVideoDownloader /Facebook/";
    public static final String directoryInstaShoryDirectorydownload_images = "/AllHDVideoDownloader /Facebook/";
    public static final String directoryInstaShoryDirectorydownload_videos = "/AllHDVideoDownloader /Facebook/";
    public static final String MY_ANDROID_10_IDENTIFIER_OF_FILE = "All_Video_Downloader_";
    public static final String My_JAVASCRIPT = "javascript:(function(){function dload_timer(){setTimeout(dload_timer,300);video_dloader();getImagesSrcSet();startListners();}function video_dloader(){var el = document.querySelectorAll('video');for(var i=0;i<el.length; i++){var src = el[i].src;el[i].parentElement.parentElement.parentElement.parentElement.setAttribute('onClick', 'setTimeout(function(){},500);FBDownloader.processVideo(\\\"'+src+'\\\");');}}function clickListner(){FBDownloader.processPhoto(this.getAttribute(\"srcset\"));} function setLeftListners(){getImagesSrcSet();var left = document.getElementsByClassName(\"coreSpriteLeftChevron\");if(left[0] !== undefined){left[0].removeEventListener(\"click\", setRightListners);left[0].addEventListener(\"click\", setRightListners);}}function setRightListners(){getImagesSrcSet();var right = document.getElementsByClassName(\"coreSpriteRightChevron\");if(right[0] !== undefined){right[0].removeEventListener(\"click\", setLeftListners);right[0].addEventListener(\"click\", setLeftListners);}}function startListners(){var right = document.getElementsByClassName(\"coreSpriteRightChevron\");if(right[0] !== undefined){\tright[0].addEventListener(\"click\", setLeftListners);}}function getImagesSrcSet(){var div = document.querySelectorAll('img[srcset]');for(i=0;i<div.length;i++){div[i].setAttribute(\"style\",\"z-index:100000\");div[i].removeEventListener(\"click\", clickListner);div[i].addEventListener(\"click\", clickListner);}}dload_timer();})()";

    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Twitter");
    //    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/AllHDVideoDownloader /Whatsapp");
    public static File downloadWhatsAppDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), Application.getInstance().getString(R.string.app_name));
    private static Context context;
    public static Dialog customDialog;
    public static String mPath;

    public static Activity mactivity = new Activity();
    private static AppCompatTextView TV_diloagValue, TV_DialogMsg;

    public MyUtils(Context context2) {
        context = context2;
    }

    public static void setToast(Context context2, String str) {
        Toast makeText = Toast.makeText(context2, str, Toast.LENGTH_SHORT);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static void createFileFolder() {

        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }
        if (!RootDirectoryTikTokShow.exists()) {
            RootDirectoryTikTokShow.mkdirs();
        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!downloadWhatsAppDir.exists()) {
            downloadWhatsAppDir.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()) {
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryLikeeShow.exists()) {
            RootDirectoryLikeeShow.mkdirs();
        }
        if (!RootDirectoryShareChatShow.exists()) {
            RootDirectoryShareChatShow.mkdirs();
        }
        if (!RootDirectoryRoposoShow.exists()) {
            RootDirectoryRoposoShow.mkdirs();
        }
        if (!RootDirectorySnackVideoShow.exists()) {
            RootDirectorySnackVideoShow.mkdirs();
        }
        if (!RootDirectoryweb.exists()) {
            RootDirectoryweb.mkdirs();
        }
        if (!RootDirectoryDownloadPath.exists()) {
            RootDirectoryDownloadPath.mkdirs();
        }
        if (!ROOTDIRECTORYWEBFB.exists()) {
            ROOTDIRECTORYWEBFB.mkdirs();
        }

        File file = ROOTDIRECTORYJOSHSHOW;
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = ROOTDIRECTORYCHINGARISHOW;
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = ROOTDIRECTORYMITRONSHOW;
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = ROOTDIRECTORYMXSHOW;
        if (!file4.exists()) {
            file4.mkdirs();
        }
        File file5 = ROOTDIRECTORYMOJSHOW;
        if (!file5.exists()) {
            file5.mkdirs();
        }
    }

    public static void showProgressDialog(Activity activity) {
        System.out.println("Show");
        Dialog dialog = customDialog;
        if (dialog != null) {
            dialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity);
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, (ViewGroup) null);
        customDialog.setCancelable(false);
        customDialog.setContentView(inflate);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog(Activity activity) {
        System.out.println("Hide");
        Dialog dialog = customDialog;
        if (dialog != null && dialog.isShowing()) {
            customDialog.dismiss();
        }
    }


    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static Dialog mLoadDialog;
    public static int downloadIdNew;

    @SuppressLint("WrongConstant")
    public static void startDownload(String str, String str2, String str3, Context context2, String str4, String str5, String str6, String str7) {
        setToast(context2, context2.getResources().getString(R.string.download_started));
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context2.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "json.json");
            contentValues.put("mime_type", "application/json");
            contentValues.put("is_pending", (Integer) 1);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + "/IG Saver/" + str7);
            Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            Log.d("mediaUri", "mediaUri " + insert);
            try {
                OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                try {
                    openOutputStream.write(str6.getBytes());
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                } catch (Throwable th) {
                    if (openOutputStream != null) {
                        try {
                            openOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues2, null, null);

        } else {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/IG Saver/" + str7);
            if (!file.exists()) {
                Log.d("dir", "" + file);
                file.mkdirs();
            }
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(file, "json.json"), true));
                bufferedWriter.write(str6);
                bufferedWriter.close();
//                Log.e("file", "file");
            } catch (IOException e3) {
                e3.printStackTrace();
//                Log.e("Error", "" + e3);
            }
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str2));
        request.setAllowedNetworkTypes(3);
        request.setNotificationVisibility(1);
        request.setTitle(str5 + "");
        request.setVisibleInDownloadsUi(true);
        String str8 = Environment.DIRECTORY_DOWNLOADS;
        request.setDestinationInExternalPublicDir(str8, str3 + "/" + str5);
        ((DownloadManager) context2.getSystemService("download")).enqueue(request);
        DownloadManager.Request request2 = new DownloadManager.Request(Uri.parse(str));
        request2.setAllowedNetworkTypes(3);
        request2.setNotificationVisibility(1);
        request2.setTitle(str4 + "");
        request2.setVisibleInDownloadsUi(true);
        String str9 = Environment.DIRECTORY_DOWNLOADS;
        request2.setDestinationInExternalPublicDir(str9, str3 + "/" + str4);
        ((DownloadManager) context2.getSystemService("download")).enqueue(request2);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/json.json").getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: video.story.ig.photo.save.insta.downloader.utils.Utils.2
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String str10, Uri uri) {
                    }
                });
                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/" + str5).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: video.story.ig.photo.save.insta.downloader.utils.Utils.3
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String str10, Uri uri) {
                    }
                });
                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/" + str4).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: video.story.ig.photo.save.insta.downloader.utils.Utils.4
                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                    public void onScanCompleted(String str10, Uri uri) {
                    }
                });
            } else {
                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/json.json"))));
                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/" + str5))));
                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + str3 + "/" + str4))));
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public static void startDownload(String str, String str2, Context context2, String str3) {
//        Log.e("DOWNLOADED", "---1");
//        setToast(context2, context2.getResources().getString(R.string.download_started));

//        new DownloadFileFromURL().execute(str);
//        showProgressDialog(mactivity);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
//        request.setAllowedNetworkTypes(3);
//        request.setNotificationVisibility(1);
//        request.setTitle(str3 + "");
//        request.setVisibleInDownloadsUi(true);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, str2 + str3);
//        ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//        Log.e("DOWNLOADED", "-----Video Path : " + str);
//        Log.e("DOWNLOADED", "-----Path : " + path + str2);
//        Log.e("DOWNLOADED", "-----Name : " + str3);

        int downloadId = PRDownloader.download(String.valueOf(Uri.parse(str)), path + str2, str3)
                .build()
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Log.e("DOWNLOADED", "Download Complete");
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e("DOWNLOADED", "---" + error.getServerErrorMessage());

                    }



                });

        try {
            if (Build.VERSION.SDK_INT >= 19) {
                String[] strArr = null;
                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).getAbsolutePath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                        Log.e("DOWNLOADED", "---4");
                    }
                });
                return;
            }
            context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3))));
            Log.e("DOWNLOADED", "---4.1");
        } catch (Exception e) {
            Log.e("DOWNLOADED", "---5" + e.getMessage());
            e.printStackTrace();
        }
        Log.e("DOWNLOADED", "---6");

//        try {
//            if (Build.VERSION.SDK_INT >= 21) {
//                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + str2 + str3 + "/json.json").getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
//                    public void onScanCompleted(String str10, Uri uri) {
//                    }
//                });
//                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" +  str2 + str3).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
//                    public void onScanCompleted(String str10, Uri uri) {
//                    }
//                });
//                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS  + "/" +  str2 + str3).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//                    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
//                    public void onScanCompleted(String str10, Uri uri) {
//                    }
//                });
//            } else {
//                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + str2 + str3 + "/json.json"))));
//                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" +  str2 + str3))));
//                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS +  "/" +  str2 + str3))));
//            }
//        } catch (Exception e4) {
//            e4.printStackTrace();
//        }

    }

    public static void startNewDownload(String str, String str2, Activity context2, String str3) {

//        Log.e("DownloadVideo", "-----enter-----------111111-----" + Uri.parse(str));
//        Log.e("DownloadVideo", "-----enter-----------22-----" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + str2);
//        Log.e("DownloadVideo", "-----enter----------33-----" + str3);

        mLoadDialog = ShowLoading(context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");

//        Log.e("MYPATHH", ">new>" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + str2);
        int download = PRDownloader.download(String.valueOf(Uri.parse(str)), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + str2, str3)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        downloadIdNew = 0;
                        HideLoading(mLoadDialog);
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(final Progress progress) {
                        context2.runOnUiThread(new Runnable() {
                            public void run() {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                TV_diloagValue.setText("" + progressPercent + " %");
//                                Log.e("DownloadVideo", "Percent : " + progressPercent + "%");
                            }
                        });
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        HideLoading(mLoadDialog);
//                        Log.e("DownloadVideo", "Download Complete Successfully");
                    }

                    @Override
                    public void onError(Error error) {

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
            Log.w("Dialog", "..........Hide");
            d.dismiss();
        }
    }



    public static void startDownloadFromLink(List<Edge> list, String str, String str2, Activity context2, String str3, String str4, String str5) {

        mLoadDialog = ShowLoading(context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");

        String str6;
        String str7;
        String str8;
        String str9;
//        setToast(context2, context2.getResources().getString(R.string.download_started));
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context2.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "json.json");
            contentValues.put("mime_type", "application/json");
            contentValues.put("is_pending", (Integer) 1);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            Log.d("mediaUri", "mediaUri " + insert);
            try {
                OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                try {
                    openOutputStream.write(str4.getBytes());
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                } catch (Throwable th) {
                    if (openOutputStream != null) {
                        try {
                            openOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues2, null, null);
        } else {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            if (!file.exists()) {
                Log.d("dir", "" + file);
                file.mkdirs();
            }

        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        int i = 3;
        request.setAllowedNetworkTypes(3);
        request.setNotificationVisibility(1);
        request.setTitle(str3 + "");
        request.setVisibleInDownloadsUi(true);
        String str10 = Environment.DIRECTORY_DOWNLOADS;
        request.setDestinationInExternalPublicDir(str10, str2 + str3);
        ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
        int size = list.size();
        String str11 = ".jpeg";
        String str12 = str11;
        String str13 = "image";
        int i2 = 0;
        while (i2 < size) {
            if (list.get(i2).getNode().isIs_video()) {
                str8 = list.get(i2).getNode().getVideo_url();
                str7 = ".mp4";
                str9 = "video";
            } else {
                str8 = list.get(i2).getNode().getDisplay_resources().get(list.get(i2).getNode().getDisplay_resources().size() - 1).getSrc();
                str7 = str11;
                str9 = "image";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str9);
            sb.append("");
            i2++;
            sb.append(i2);
            sb.append("");
            String str14 = str7;
            sb.append(str14);
            sb.append("");

            downloadIdNew = PRDownloader.download(String.valueOf(Uri.parse(str8)), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + str2 + str9 + "" + i2 + "" + str14, sb.toString())
                    .build()
                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                        @Override
                        public void onStartOrResume() {
                        }
                    })
                    .setOnPauseListener(new OnPauseListener() {
                        @Override
                        public void onPause() {
                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel() {
                            downloadIdNew = 0;
                            HideLoading(mLoadDialog);
                        }
                    })
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(final Progress progress) {

                            context2.runOnUiThread(new Runnable() {
                                public void run() {
                                    long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                    TV_diloagValue.setText("" + progressPercent + " %");
                                }
                            });

                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            HideLoading(mLoadDialog);
//                            Toast.makeText(context2, "Downloading Complete", Toast.LENGTH_SHORT).show();
//                            Log.e("DownloadVideo", "Download Complete Successfully");

                        }

                        @Override
                        public void onError(Error error) {

                        }


                    });

//            request2.setTitle(sb.toString());
//            request2.setVisibleInDownloadsUi(true);
//            String str15 = Environment.DIRECTORY_DOWNLOADS;
//            str11 = str11;
//            request2.setDestinationInExternalPublicDir(str15, str2 + "/" + str9 + "" + i2 + "" + str14);
//            ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request2);
            str12 = str14;
            str13 = str9;
            i = 3;


        }
        String str16 = str11;
    }

    public static void startDownloadLogin(List<ItemModel> list, String str, String str2, Activity context2, String str3, String str4) {
        Log.d("TAG_startDownload", "startDownload: startDownloadLogin");
        String str6;
        String str7;
        String str8;
        // setToast(context2, context2.getResources().getString(R.string.download_started));
        int i = 0;
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context2.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", "json.json");
            contentValues.put("mime_type", "application/json");
            contentValues.put("is_pending", (Integer) 1);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            Uri insert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            Log.d("mediaUri", "mediaUri " + insert);

            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("is_pending", (Integer) 0);
            contentResolver.update(insert, contentValues2, null, null);
        } else {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryInsta);
            if (!file.exists()) {
                Log.d("dir", "" + file);
                file.mkdirs();
            }

        }
        int i2 = 3;

        mLoadDialog = ShowLoading((Activity) context2);
        TV_diloagValue = mLoadDialog.findViewById(R.id.TV_diloagValue);
        TV_DialogMsg = mLoadDialog.findViewById(R.id.TV_DialogMsg);
        TV_diloagValue.setVisibility(View.VISIBLE);
        TV_DialogMsg.setText("Downloading...");

        int size = list.size();
        String str10 = ".jpeg";
        String str11 = str10;
        String str12 = "image";
        int i3 = 0;
        while (i3 < size) {
            if (list.get(i3).getMedia_type() == 2) {
                str8 = list.get(i3).getVideo_versions().get(i).getUrl();
                str7 = ".mp4";
                String name = String.valueOf(System.currentTimeMillis());
                str12 = name + "_video";
            } else {
                str8 = list.get(i3).getImage_versions2().getCandidates().get(i).getUrl();
                str7 = str10;
                String name = String.valueOf(System.currentTimeMillis());
                str12 = name + "_image";
            }
            i3++;

            String finalStr = str8;
            String finalStr1 = str12;
            int finalI = i3;
            String finalStr2 = str7;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    animation_view.setVisibility(View.GONE);
//                    rcprogress.setVisibility(View.VISIBLE);
                    String destinationPath = Environment.getExternalStorageDirectory() + "/Download";
                    Uri downloadUri = Uri.parse(finalStr);
                    Uri destinationUri = Uri.parse(destinationPath + str2 + finalStr1 + "" + finalI + "" + finalStr2);
                    Log.d("TAG_startDownloadLogin", "startDownloadLogin: " + destinationUri);
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .addCustomHeader("Auth-Token", "myTokenKey")
                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setDownloadContext(context2)
                            .setDownloadListener(new DownloadStatusListener() {
                                @Override
                                public void onDownloadComplete(int id) {
//                                    dialog.dismiss();
                                    HideLoading(mLoadDialog);
//                                    Toast.makeText(context2, "Downloading Complete", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
//                                    Toast.makeText(context2, "Failed", Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
                                    HideLoading(mLoadDialog);
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {

                                    ((Activity) context2).runOnUiThread(new Runnable() {
                                        public void run() {
                                            long progressPercent = downlaodedBytes * 100 / totalBytes;
                                            TV_diloagValue.setText("" + progressPercent + " %");
                                        }
                                    });

                                }
                            });

                    ThinDownloadManager downloadManager = new ThinDownloadManager(1);
                    downloadManager.add(downloadRequest);
                }
            }, 1000);

            str11 = str7;
            i2 = 3;
            i = 0;

        }
    }

    public static void shareImage(Context context2, String str) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", context2.getResources().getString(R.string.share_txt));
            String str2 = null;
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(context2.getContentResolver(), str, "", (String) null)));
            intent.setType("image/*");
            context2.startActivity(Intent.createChooser(intent, context2.getResources().getString(R.string.share_image_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public static void shareImageVideoOnWhatsapp(Context context2, String str, boolean z) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", "");
        intent.putExtra("android.intent.extra.STREAM", parse);
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.addFlags(1);
        try {
            context2.startActivity(intent);
        } catch (Exception unused) {
            setToast(context2, context2.getResources().getString(R.string.whatsapp_not_installed));
        }
    }

    @SuppressLint("WrongConstant")
    public static void shareVideo(Context context2, String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(MimeTypes.VIDEO_MP4);
        intent.putExtra("android.intent.extra.STREAM", parse);
        intent.addFlags(1);
        try {
            context2.startActivity(Intent.createChooser(intent, "Share Video using"));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context2, context2.getResources().getString(R.string.no_app_installed), Toast.LENGTH_SHORT).show();
        }
    }

    public static void OpenApp(Context context2, String str) {
        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context2.startActivity(launchIntentForPackage);
        } else {
            setToast(context2, context2.getResources().getString(R.string.app_not_available));
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.equalsIgnoreCase(Address.OCTAL_PREFIX);
    }

    public static List<String> extractUrls(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", 2).matcher(str);
        while (matcher.find()) {
            arrayList.add(str.substring(matcher.start(0), matcher.end(0)));
        }
        return arrayList;
    }

    public static void infoDialog(Context context2, String str, String str2) {
        new AlertDialog.Builder(context2).setTitle(str).setMessage(str2).setPositiveButton(context2.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    public static String getBack(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        return matcher.find() ? matcher.group(1) : "";
    }

    public static boolean download(Context context2, String str) {
        return copyFileInSavedDir(context2, str);
    }

    public static boolean isVideoFile(Context context2, String str) {
        if (str.startsWith("content")) {
            String type = DocumentFile.fromSingleUri(context2, Uri.parse(str)).getType();
            if (type == null || !type.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
                return false;
            }
            return true;
        }
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        if (guessContentTypeFromName == null || !guessContentTypeFromName.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
            return false;
        }
        return true;
    }

    public static boolean copyFileInSavedDir(Context context2, String str) {
        String str2;
        if (isVideoFile(context2, str)) {
            str2 = getDir(context2, "Videos").getAbsolutePath();
        } else {
            str2 = getDir(context2, "Images").getAbsolutePath();
        }
        Uri fromFile = Uri.fromFile(new File(str2 + File.separator + new File(str).getName()));
        try {
            InputStream openInputStream = context2.getContentResolver().openInputStream(Uri.parse(str));
            OutputStream openOutputStream = context2.getContentResolver().openOutputStream(fromFile, "w");
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    openOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    openOutputStream.flush();
                    openOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(fromFile);
                    context2.sendBroadcast(intent);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    static File getDir(Context context2, String str) {
//        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Download" + File.separator + context2.getResources().getString(R.string.app_name) + File.separator + str);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), Application.getInstance().getString(R.string.app_name));
        file.mkdirs();
        return file;
    }

    public static void setLanguage(Context context2, String str) {
        Locale locale = new Locale(str);
        Resources resources = context2.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @SuppressLint("WrongConstant")
    public static boolean appInstalledOrNot(Context context2, String str) {
        try {
            context2.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void shareFile(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        context2.startActivity(intent);
    }

    public static void repostWhatsApp(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setPackage("com.whatsapp");
        context2.startActivity(intent);
    }

}
