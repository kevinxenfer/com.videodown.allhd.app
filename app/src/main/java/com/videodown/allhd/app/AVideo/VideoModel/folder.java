package com.videodown.allhd.app.AVideo.VideoModel;

import java.io.Serializable;

public class folder implements Serializable {
    public String bid;
    public String bucket;
    public String count;
    public String data;
    public String date;
    public long folderSize;
    public String newTag;
    public String size;

    public folder() {
    }

    folder(String str, String str2, String str3, String str4) {
        this.bucket = str;
        this.data = str2;
        this.size = str3;
        this.bid = str4;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String str) {
        this.bucket = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setBid(String str) {
        this.bid = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public String getBid() {
        return this.bid;
    }

    public String getCount() {
        return this.count;
    }

    public void setCount(String str) {
        this.count = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public long getFolderSize() {
        return this.folderSize;
    }

    public void setFolderSize(long j) {
        this.folderSize = j;
    }
}
