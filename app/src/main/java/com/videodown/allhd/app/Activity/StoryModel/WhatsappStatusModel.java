package com.videodown.allhd.app.Activity.StoryModel;

import android.net.Uri;

public class WhatsappStatusModel {
    private String filename;
    private String name;
    private String path;
    private Uri uri;

    public WhatsappStatusModel(String str, Uri uri2, String str2, String str3) {
        this.name = str;
        this.uri = uri2;
        this.path = str2;
        this.filename = str3;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri2) {
        this.uri = uri2;
    }
}
