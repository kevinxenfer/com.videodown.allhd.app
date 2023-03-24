package com.videodown.allhd.app.AllStory.story;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class ImageVersionModel implements Serializable {
    @SerializedName("candidates")
    private ArrayList<CandidatesModel> candidates;

    public ArrayList<CandidatesModel> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(ArrayList<CandidatesModel> arrayList) {
        this.candidates = arrayList;
    }
}
