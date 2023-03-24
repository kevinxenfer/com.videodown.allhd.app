package com.videodown.allhd.app.Activity.StoryModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseModel implements Serializable {
    @SerializedName("graphql")
    private Graphql graphql;

    public Graphql getGraphql() {
        return this.graphql;
    }

    public void setGraphql(Graphql graphql2) {
        this.graphql = graphql2;
    }
}
