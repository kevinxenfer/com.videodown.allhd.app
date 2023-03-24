package com.videodown.allhd.app.Activity.StoryModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {
    @SerializedName("display_resources")
    private List<DisplayResource> display_resources;
    @SerializedName("display_url")
    private String display_url;
    @SerializedName("is_video")
    private boolean is_video;
    @SerializedName("video_url")
    private String video_url;

    public String getDisplay_url() {
        return this.display_url;
    }

    public void setDisplay_url(String str) {
        this.display_url = str;
    }

    public List<DisplayResource> getDisplay_resources() {
        return this.display_resources;
    }

    public void setDisplay_resources(List<DisplayResource> list) {
        this.display_resources = list;
    }

    public boolean isIs_video() {
        return this.is_video;
    }

    public void setIs_video(boolean z) {
        this.is_video = z;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public void setVideo_url(String str) {
        this.video_url = str;
    }
}
