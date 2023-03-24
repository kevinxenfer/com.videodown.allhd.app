package com.videodown.allhd.app.AllStory.AllvideoEqulizer;

public class EqualizerDataList {
    String name;
    boolean selectItem;

    public EqualizerDataList(String str, boolean z) {
        this.name = str;
        this.selectItem = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean isSelectItem() {
        return this.selectItem;
    }

    public void setSelectItem(boolean z) {
        this.selectItem = z;
    }
}
