package com.videodown.allhd.app.Activity.StoryModel.FBStoryModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class NodeModel implements Serializable {
    @SerializedName("node")
    private NodeDataModel nodeDataModel;

    public NodeDataModel getNodeDataModel() {
        return this.nodeDataModel;
    }

    public void setNodeDataModel(NodeDataModel nodeDataModel2) {
        this.nodeDataModel = nodeDataModel2;
    }
}
