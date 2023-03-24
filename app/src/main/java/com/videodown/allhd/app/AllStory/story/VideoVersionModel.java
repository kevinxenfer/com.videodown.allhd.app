package com.videodown.allhd.app.AllStory.story;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VideoVersionModel implements Serializable {
    @SerializedName("height")
    private int height;
    @SerializedName("id")

    /* renamed from: id */
    private String f452id;
    @SerializedName("type")
    private int type;
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private int width;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getId() {
        return this.f452id;
    }

    public void setId(String str) {
        this.f452id = str;
    }
}
