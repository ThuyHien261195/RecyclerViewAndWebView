package com.hasbrain.areyouandroiddev.model;

/**
 * Created by thuyhien on 9/20/17.
 */

public class RedditPostIndex {
    private int groupIndex;
    private int childIndex;

    public RedditPostIndex(int groupIndex, int childIndex) {
        this.groupIndex = groupIndex;
        this.childIndex = childIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }
}
