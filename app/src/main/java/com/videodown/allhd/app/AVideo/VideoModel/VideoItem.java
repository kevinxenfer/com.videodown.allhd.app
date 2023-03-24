package com.videodown.allhd.app.AVideo.VideoModel;

import java.io.Serializable;

public class VideoItem implements Serializable {
    private String Bucket;
    public String DATA;
    private String DATE;
    public String DISPLAY_NAME;
    private String DURATION;
    private String SIZE;
    private String _ID;
    public String newTag;
    private long videoSize;

    public void setVideoSize(long j) {
        this.videoSize = j;
    }

    public String getBucket() {
        return this.Bucket;
    }

    public void setBucket(String str) {
        this.Bucket = str;
    }

    public VideoItem(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this._ID = str;
        this.SIZE = str2;
        this.DATE = str3;
        this.DATA = str4;
        this.DISPLAY_NAME = str5;
        this.DURATION = str6;
        this.Bucket = str7;
    }

    public VideoItem() {
    }

    public String get_ID() {
        return this._ID;
    }

    public void set_ID(String str) {
        this._ID = str;
    }

    public String getSIZE() {
        return this.SIZE;
    }

    public void setSIZE(String str) {
        this.SIZE = str;
    }

    public String getDATE() {
        return this.DATE;
    }

    public void setDATE(String str) {
        this.DATE = str;
    }

    public String getDATA() {
        return this.DATA;
    }

    public void setDATA(String str) {
        this.DATA = str;
    }

    public String getDISPLAY_NAME() {
        return this.DISPLAY_NAME;
    }

    public void setDISPLAY_NAME(String str) {
        this.DISPLAY_NAME = str;
    }

    public String getDURATION() {
        return this.DURATION;
    }

    public void setDURATION(String str) {
        this.DURATION = str;
    }

    public long getVideoSize() {
        return this.videoSize;
    }
}
