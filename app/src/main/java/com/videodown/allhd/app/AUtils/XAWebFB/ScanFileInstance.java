package com.videodown.allhd.app.AUtils.XAWebFB;

import android.media.MediaScannerConnection;
import android.net.Uri;

public final  class ScanFileInstance implements MediaScannerConnection.OnScanCompletedListener {
    public static final ScanFileInstance INSTANCE = new ScanFileInstance();

    private ScanFileInstance() {
    }

    public final void onScanCompleted(String str, Uri uri) {
        AndroidUtils.MediaScannerService(str, uri);
    }
}
