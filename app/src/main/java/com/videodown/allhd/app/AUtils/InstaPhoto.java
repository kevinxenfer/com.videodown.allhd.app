package com.videodown.allhd.app.AUtils;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

public class InstaPhoto {
    private String medium = null;
    private String regular = null;
    private String small = null;

    public String getRegular() {
        Log.e("WEBVIEWJS", "getRegular ");
        Log.e("WEBVIEWJS", "getRegular " + this.regular);
        String str = this.regular;
        return str == null ? getMedium() : str;
    }

    private String getMedium() {
        String str = this.medium;
        return str == null ? getSmall() : str;
    }

    private String getSmall() {
        String str = this.small;
        if (str == null) {
            return null;
        }
        return str;
    }

    public boolean parseStr(String str) {
        String[] split = str.split(",");
        Log.e("WEBVIEWJS", "parseStr " + split);
        int i = 0;
        while (i < split.length) {
            try {
                String trim = split[i].trim();
                Log.e("WEBVIEWJS", "parseStr " + trim);
                String[] split2 = trim.split(StringUtils.SPACE);
                if (split2[1].equals(PhotoItems.SMALL.f169id)) {
                    Log.e("WEBVIEWJS", "parseStr1 url" + split2[0]);
                    Log.e("WEBVIEWJS", "parseStr1 id" + split2[0]);
                    this.small = split2[0];
                } else if (split2[1].equals(PhotoItems.MEDIUM.f169id)) {
                    Log.e("WEBVIEWJS", "parseStr2 url" + split2[0]);
                    Log.e("WEBVIEWJS", "parseStr2 id" + split2[0]);
                    this.medium = split2[0];
                } else if (split2[1].equals(PhotoItems.REGULAR.f169id)) {
                    Log.e("WEBVIEWJS", "parseStr3 url" + split2[0]);
                    Log.e("WEBVIEWJS", "parseStr3 id" + split2[0]);
                    this.regular = split2[0];
                }
                i++;
            } catch (Exception e) {
                Log.e("WEBVIEWJS", "parseStr Exception" + e.getMessage());
                return false;
            }
        }
        return true;
    }
}
