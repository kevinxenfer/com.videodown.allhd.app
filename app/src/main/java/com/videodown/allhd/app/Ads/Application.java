package com.videodown.allhd.app.Ads;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.downloader.PRDownloader;
import com.videodown.allhd.app.AUtils.XAWebFB.DownloadService;
import com.google.gms.ads.CustomApplication;


public class Application extends CustomApplication {
    public static final String TAG = Application.class.getSimpleName();
    public static Application mInstance;
    public static Context mActivity;
    public static boolean activity = false;
    private RequestQueue mRequestQueue;
    private Intent olddownloadService;

    public DownloadService downloadService;
    public Intent downloadIntent = null;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mActivity = this;

        new PrefShar(this);
        PRDownloader.initialize(getApplicationContext());
//        downloadService = new Intent(getApplicationContext(), DownloadManager.class);
        olddownloadService = new Intent(getApplicationContext(), DownloadManager.class);


    }


    public ServiceConnection downloadConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {

            downloadService = ((DownloadService.DownloadBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            downloadService.stopForeground(false);
        }
    };


    public static synchronized Application getInstance() {
        return mInstance;
    }

    public static synchronized Context getContext() {
        return getInstance().getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
