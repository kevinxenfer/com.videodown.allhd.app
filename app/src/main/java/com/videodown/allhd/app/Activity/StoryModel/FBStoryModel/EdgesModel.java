package com.videodown.allhd.app.Activity.StoryModel.FBStoryModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class EdgesModel implements Serializable {
    @SerializedName("edges")
    private ArrayList<NodeModel> edgeModel;

    public ArrayList<NodeModel> getEdgeModel() {
        return this.edgeModel;
    }

    public void setEdgeModel(ArrayList<NodeModel> arrayList) {
        this.edgeModel = arrayList;
    }
}
