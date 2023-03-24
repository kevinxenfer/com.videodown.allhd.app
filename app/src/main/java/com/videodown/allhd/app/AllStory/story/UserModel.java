package com.videodown.allhd.app.AllStory.story;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("id")
    private long id;

    int isFav;
    @SerializedName("is_private")
    private boolean is_private;
    @SerializedName("is_verified")
    private boolean is_verified;
    @SerializedName("pk")

    /* renamed from: pk */
    private long f451pk;
    @SerializedName("profile_pic_id")
    private String profile_pic_id;
    @SerializedName("profile_pic_url")
    private String profile_pic_url;
    @SerializedName("username")
    private String username;

    public int getIsFav() {
        return this.isFav;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setIsFav(int i) {
        this.isFav = i;
    }

    public long getPk() {
        return this.f451pk;
    }

    public void setPk(long j) {
        this.f451pk = j;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String str) {
        this.full_name = str;
    }

    public boolean isIs_private() {
        return this.is_private;
    }

    public void setIs_private(boolean z) {
        this.is_private = z;
    }

    public String getProfile_pic_url() {
        return this.profile_pic_url;
    }

    public void setProfile_pic_url(String str) {
        this.profile_pic_url = str;
    }

    public String getProfile_pic_id() {
        return this.profile_pic_id;
    }

    public void setProfile_pic_id(String str) {
        this.profile_pic_id = str;
    }

    public boolean isIs_verified() {
        return this.is_verified;
    }

    public void setIs_verified(boolean z) {
        this.is_verified = z;
    }
}
