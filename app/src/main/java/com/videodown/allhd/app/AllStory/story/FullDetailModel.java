package com.videodown.allhd.app.AllStory.story;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class FullDetailModel implements Serializable {
    @SerializedName("reel_feed")
    private ReelFeedModel reel_feed;
    @SerializedName("user_detail")
    private UserDetailModel user_detail;

    public UserDetailModel getUser_detail() {
        return this.user_detail;
    }

    public void setUser_detail(UserDetailModel userDetailModel) {
        this.user_detail = userDetailModel;
    }

    public ReelFeedModel getReel_feed() {
        return this.reel_feed;
    }

    public void setReel_feed(ReelFeedModel reelFeedModel) {
        this.reel_feed = reelFeedModel;
    }
}
