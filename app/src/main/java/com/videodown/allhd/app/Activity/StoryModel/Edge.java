package com.videodown.allhd.app.Activity.StoryModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Edge implements Serializable {
    @SerializedName("node")
    private Node node;

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node2) {
        this.node = node2;
    }
}
