package com.videodown.allhd.app.AVideo.VideoModel;

public class PrivateItem {
    public String name;
    public String oldPath;

    public PrivateItem(String str, String str2) {
        this.oldPath = str;
        this.name = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getOldPath() {
        return this.oldPath;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOldPath(String str) {
        this.oldPath = str;
    }
}
