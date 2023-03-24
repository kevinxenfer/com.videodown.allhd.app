package com.videodown.allhd.app.AUtils.XAWebFB;

import java.io.Serializable;

public class DownloadingItem implements Serializable {
    public double currentSize = 0.0d;
    public int downloadId = 0;
    public String icon = "";
    public int isInPause = 0;
    public String lastModification = "";
    public String localFilePath = "";
    public String name = "";
    public int percent = 0;
    public int progress = 0;
    public int speed = 0;
    public double totalSize = 0.0d;
    public String url = "";

    public DownloadingItem() {
    }

    public DownloadingItem(String name2, String icon2, String url2, int downloadId2, double totalSize2, double currentSize2, int percent2, int progress2, int speed2, int isInPause2, String localFilePath2, String lastModification2) {
        this.name = name2;
        this.icon = icon2;
        this.url = url2;
        this.downloadId = downloadId2;
        this.totalSize = totalSize2;
        this.currentSize = currentSize2;
        this.percent = percent2;
        this.progress = progress2;
        this.speed = speed2;
        this.isInPause = isInPause2;
        this.localFilePath = localFilePath2;
        this.lastModification = lastModification2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon2) {
        this.icon = icon2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public int getDownloadId() {
        return this.downloadId;
    }

    public void setDownloadId(int downloadId2) {
        this.downloadId = downloadId2;
    }

    public double getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(double totalSize2) {
        this.totalSize = totalSize2;
    }

    public void setCurrentSize(double currentSize2) {
        this.currentSize = currentSize2;
    }

    public int getPercent() {
        return this.percent;
    }

    public void setPercent(int percent2) {
        this.percent = percent2;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed2) {
        this.speed = speed2;
    }

    public int getIsInPause() {
        return this.isInPause;
    }

    public void setIsInPause(int isInPause2) {
        this.isInPause = isInPause2;
    }

    public String getLocalFilePath() {
        return this.localFilePath;
    }

    public void setLocalFilePath(String localFilePath2) {
        this.localFilePath = localFilePath2;
    }

    public double getCurrentSize() {
        return this.currentSize;
    }

    public String getLastModification() {
        return this.lastModification;
    }

    public void setLastModification(String lastModification2) {
        this.lastModification = lastModification2;
    }
}
