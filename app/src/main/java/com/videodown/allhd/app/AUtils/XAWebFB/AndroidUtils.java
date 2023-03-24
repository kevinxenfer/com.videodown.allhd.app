package com.videodown.allhd.app.AUtils.XAWebFB;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AndroidUtils {

    public static final String BASE_URL = "https://getvideo.at/";
    public static final String BASE_URL1 = "https://ajith-facebook-video-downloader-v1.p.rapidapi.com/";
    public static ProgressDialog progressDialog;

    public static API vimeoVodeoCallAPI(String url) throws NoSuchAlgorithmException, KeyManagementException {
        return (API) new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).build().create(API.class);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {

            TrustManager[] trustManagers = {new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init((KeyManager[]) null, trustManagers, new SecureRandom());
            return new OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES).sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]).hostnameVerifier(HostNameVerifier.INSTANCE).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES).build();
        }
    }

    static  boolean OkHttpclient(String s, SSLSession sslSession) {
        return true;
    }

    public static void showProgress(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(0);
        progressDialog.show();
    }

    public static void showProgress(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(0);
        progressDialog.show();
    }

    public static void dismissProgress() {
        ProgressDialog progressDialog2 = progressDialog;
        if (progressDialog2 != null && progressDialog2.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        if (activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(AppCompatActivity activity, View view) {
        view.requestFocus();
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    public static File getDefaultFilePath(String filePath) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filePath);
        if (file.exists()) {
            return file;
        }
        if (file.mkdirs()) {
            return file;
        }
        return null;
    }

    @SuppressLint("WrongConstant")
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
        intent.addFlags(268435456);
        return intent;
    }

    public static void playFile(Context context, String mLocalUri) {
        try {
            context.startActivity(Intent.createChooser(playFileIntent(context, mLocalUri), "Play With"));
        } catch (Exception e) {
            Toast.makeText(context, "No App Found", Toast.LENGTH_LONG).show();
        }
    }

    public static void notifyMediaScannerService(Context context, String path) {
        MediaScannerConnection.scanFile(context, new String[]{path}, (String[]) null, ScanFileInstance.INSTANCE);
    }

    static  void MediaScannerService(String path1, Uri uri) {
        Log.i("ExternalStorage", "Scanned " + path1 + ":");
        StringBuilder sb = new StringBuilder();
        sb.append("-> uri=");
        sb.append(uri);
        Log.i("ExternalStorage", sb.toString());
    }

    public static void clearCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                if (!deleteDir(new File(dir, file))) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir == null || !dir.isFile()) {
            return false;
        } else {
            return dir.delete();
        }
    }

}
