package com.videodown.allhd.app.AllStory.AllvideoMyDownload;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.videodown.allhd.app.AUtils.MyUtils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DownloadVideosMain {
    private static Thread mytiktok_get_thread;
    public static long downloadID;
    public static DownloadManager downloadManager;

    public static void Start(final Activity context, final String str, Boolean bool) {
        MyUtils.showProgressDialog(context);
        try {
            PrintStream printStream = System.out;
            printStream.println("myurliss = " + str);
            if (DownloadVideosMain.mytiktok_get_thread != null && DownloadVideosMain.mytiktok_get_thread.isAlive()) {
                DownloadVideosMain.mytiktok_get_thread.stop();
            }
            DownloadVideosMain.mytiktok_get_thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Looper.prepare();
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("q", str)
                                .addFormDataPart("vt", "home")
                                .build();

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("https://x2download.com/api/ajaxSearch")
                                .post(requestBody)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    final String myResponse = response.body().string();
                                    Log.d("response", myResponse);
                                    JSONObject jSONObject = null;
                                    try {
                                        jSONObject = new JSONObject(myResponse);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (!jSONObject.getJSONObject("links").getString("hd").equals("")) {
                                            Log.d("TAG_run", "run: 111");
                                            MyUtils.hideProgressDialog(context);
                                            startDownloading(context, jSONObject.getJSONObject("links").getString("hd"), "Facebook_" + System.currentTimeMillis(), ".mp4");
                                           // startDownload(jSONObject.getJSONObject("links").getString("hd"), RootDirectoryFacebook,context,"Facebook_" + System.currentTimeMillis()+ ".mp4");

                                        } else if (!jSONObject.getJSONObject("links").getString("sd").equals("")) {
                                            Log.d("TAG_run", "run: 222");
                                            MyUtils.hideProgressDialog(context);
                                            startDownloading(context, jSONObject.getJSONObject("links").getString("sd"), "Facebook_" + System.currentTimeMillis(), ".mp4");

                                        } else {
                                            MyUtils.hideProgressDialog(context);
                                            Toast.makeText(context, "Error 1", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        });
                    } catch (Exception e6) {
                        PrintStream printStream3 = System.out;
                        printStream3.println("myurliss err dialog = " + e6.getLocalizedMessage());
                        e6.printStackTrace();
                        Toast.makeText(context, "Please tryagin later", Toast.LENGTH_SHORT).show();
                        MyUtils.hideProgressDialog(context);
                    }
                }
            };
            DownloadVideosMain.mytiktok_get_thread.start();
        } catch (Exception e6) {
            PrintStream printStream2 = System.out;
            printStream2.println("myurliss err dialog 4= " + e6.getLocalizedMessage());
            e6.printStackTrace();
            MyUtils.hideProgressDialog(context);
            Toast.makeText(context, "Please tryagin later", Toast.LENGTH_SHORT).show();

        }
    }
    public static void startDownloading(final Context context, String str, String str2, String str3) {
        char c = 0;
        try {
            String replaceAll = ("All_Video_Downloader_" + str2).replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]", "").replaceAll("['+.^:,#\"]", "");
            String str4 = replaceAll.replace(StringUtils.SPACE, "-").replace("!", "").replace(":", "") + str3;
            if (str4.length() > 100) {
                str4 = str4.substring(0, 100) + str3;
            }
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str.replace("\"", "")));
            request.setTitle(str2);
            request.setDescription("downloading...");
            request.setNotificationVisibility(1);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
            if (!file.exists()) {
                file.mkdir();
            }
            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
            if (!file2.exists()) {
                file2.mkdir();
            }
            File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
            if (!file3.exists()) {
                file3.mkdir();
            }
            c = 65535;
            switch (str3.hashCode()) {
                case 1472726:
                    if (str3.equals(".gif")) {
                        Log.d("TAG_run", "startDownloading: gif");
                        c = 2;
                        break;
                    }
                    break;
                case 1475827:
                    if (str3.equals(".jpg")) {
                        Log.d("TAG_run", "startDownloading: jpg");
                        c = 1;
                        break;
                    }
                    break;
                case 1476844:
                    if (str3.equals(".m4a")) {
                        Log.d("TAG_run", "startDownloading: m4a");
                        c = 7;
                        break;
                    }
                    break;
                case 1478658:
                    if (str3.equals(".mp3")) {
                        Log.d("TAG_run", "startDownloading: mp3");
                        c = 6;
                        break;
                    }
                    break;
                case 1478659:
                    if (str3.equals(".mp4")) {
                        Log.d("TAG_run", "startDownloading: mp4");
                        c = 5;
                        break;
                    }
                    break;
                case 1481531:
                    if (str3.equals(".png")) {
                        Log.d("TAG_run", "startDownloading: png");
                        break;
                    }
                    break;
                case 1487870:
                    if (str3.equals(".wav")) {
                        Log.d("TAG_run", "startDownloading: wav");
                        c = '\b';
                        break;
                    }
                    break;
                case 45750678:
                    if (str3.equals(".jpeg")) {
                        Log.d("TAG_run", "startDownloading: jpeg");
                        c = 3;
                        break;
                    }
                    break;
                case 46127303:
                    if (str3.equals(".webm")) {
                        Log.d("TAG_run", "startDownloading: webm");
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                    System.out.println("dirrrrnbrjjjn " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, MyUtils.RootDirectoryFacebook + str4);
                    System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
                    break;
                case 4:
                case 5:
                    System.out.println("dirrrrnbrjjjn " + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryFacebook);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, MyUtils.RootDirectoryFacebook + str4);
                    System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
                    break;
                case 6:
                case 7:
                case '\b':
                    System.out.println("dirrrrnbrjjjn " + Environment.DIRECTORY_DOWNLOADS + MyUtils.RootDirectoryFacebook);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, MyUtils.RootDirectoryFacebook + str4);
                    System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + MyUtils.RootDirectoryFacebook);
                    break;
            }
            request.allowScanningByMediaScanner();
            downloadID = downloadManager.enqueue(request);

            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public final void run() {
                    Toast.makeText(context, "Downloading Start", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public final void run() {
                        Toast.makeText(context, "Downloading Start", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }
}
