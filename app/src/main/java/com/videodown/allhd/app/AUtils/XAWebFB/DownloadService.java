package com.videodown.allhd.app.AUtils.XAWebFB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.videodown.allhd.app.R;
import com.videodown.allhd.app.Activity.StoryDownloader.FacebookActivity;
import com.huxq17.download.Pump;
import com.huxq17.download.config.DownloadConfig;

import java.io.File;

public class DownloadService extends Service {
    private static final String TAG = "groupA";
    public static int counter = 0;
    private Context context;
    private final IBinder downloadBind = new DownloadBinder();
    private AllDownloaderSharedpref sharedpref;

    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;
        this.sharedpref = new AllDownloaderSharedpref(this.context);
        return 1;
    }

    public class DownloadBinder extends Binder {
        public DownloadBinder() {
        }

        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.downloadBind;
    }

    public void addDownloadData(String downloadUrl, String icon, String fileName, String filePath) {
        int maximumDownload;
        String downloadPath;
        File filepath;
        String str = fileName;
        if (!new File(filePath).exists()) {
//            NewBrowserActivity.getInstance().showToast("Downloading file...");
            DownloadingItem downloadingItem = new DownloadingItem(fileName, icon, downloadUrl, 0, 0.0d, 0.0d, 0, 0, 0, 0, filePath, String.valueOf(System.currentTimeMillis()));
            if (this.sharedpref.getInt(Constants.MAX_DOWNLOAD) != 0) {
                maximumDownload = this.sharedpref.getInt(Constants.MAX_DOWNLOAD);
            } else {
                maximumDownload = 6;
            }
            DownloadConfig.newBuilder().setMaxRunningTaskNum(maximumDownload).build();
            if (!this.sharedpref.getString(Constants.SELECTED_PATH).isEmpty()) {
                this.sharedpref = new AllDownloaderSharedpref(this.context);
                downloadPath = this.sharedpref.getString(Constants.SELECTED_PATH);
            } else {
                downloadPath = getString(R.string.storagepath);
            }
            if (!downloadPath.equals("/storage/emulated/0/All Downloader App/Videos")) {
                String str2 = fileName;
                if (str2.contains("instagram")) {
                    String name = str2.replace("%20", " ");
                    String s = name.substring(0, name.length() - 40);
                    filepath = new File(downloadPath, "All Downloader App/Videos" + File.separator + ("Instagram" + s.substring(0, 8)) + ".mp4");
                } else if (str2.contains("?rc=")) {
                    String name2 = str2.replace("%20", " ");
                    filepath = new File(downloadPath, "All Downloader App/Videos" + File.separator + name2.substring(4).substring(0, name2.length() - 160) + ".mp4");
                } else {
                    filepath = new File(downloadPath, "All Downloader App/Videos" + File.separator + str2);
                }
            } else {
                String str3 = fileName;
                if (str3.contains("instagram")) {
                    String name3 = str3.replace("%20", " ");
                    String s2 = name3.substring(0, name3.length() - 40);
                    String sname = "Instagram" + s2.substring(0, 8);
                    if (s2.contains("mp4")) {
                        filepath = new File(Environment.getExternalStorageDirectory(), "All Downloader App/Videos/" + sname);
                    } else {
                        filepath = new File(Environment.getExternalStorageDirectory(), "All Downloader App/Videos/" + sname + ".mp4");
                    }
                } else if (str3.contains("?rc=")) {
                    String name4 = str3.replace("%20", " ");
                    String s3 = name4.substring(4).substring(0, name4.length() - 160);
                    filepath = new File(Environment.getExternalStorageDirectory(), "All Downloader App/Videos/" + s3 + ".mp4");
                } else {
                    filepath = new File(Environment.getExternalStorageDirectory(), "All Downloader App/Videos/" + str3);
                }
            }
            this.sharedpref = new AllDownloaderSharedpref(this.context);
            Pump.newRequest(downloadingItem.url, filepath.getAbsolutePath()).tag(TAG).submit();
            counter++;
//            MainActivity.textViewDownloadCount.setText(String.valueOf(counter));
//            MainActivity.textViewDownloadCount.setVisibility(0);
            String str4 = filePath;
            return;
        }
        FacebookActivity.showFileExistPopUpDialog(str, filePath, (Activity) context);
    }

    public String getFilePath(String fileName) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return new File(externalStorageDirectory, "All Downloader App/Videos/" + fileName).getAbsolutePath();
    }

    public String getFileName(String downloadUrl) {
        return downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1).replace("%20", " ");
    }
}
