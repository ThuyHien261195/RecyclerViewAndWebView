package com.hasbrain.areyouandroiddev.model;

import java.util.List;

/**
 * Created by thuyhien on 9/20/17.
 */

public class ExpandRedditPost {
    private String groupHeader;
    private List<RedditPost> childRedditPostList;
    private boolean isExpandGroup;

    public ExpandRedditPost(String groupHeader, List<RedditPost> childRedditPostList) {
        this.groupHeader = groupHeader;
        this.childRedditPostList = childRedditPostList;
        this.isExpandGroup = false;
    }

    public String getGroupHeader() {
        return groupHeader;
    }

    public void setGroupHeader(String groupHeader) {
        this.groupHeader = groupHeader;
    }

    public List<RedditPost> getChildRedditPostList() {
        return childRedditPostList;
    }

    public void setChildRedditPostList(List<RedditPost> childRedditPostList) {
        this.childRedditPostList = childRedditPostList;
    }

    public boolean isExpandGroup() {
        return isExpandGroup;
    }

    public void setExpandGroup(boolean expandGroup) {
        isExpandGroup = expandGroup;
    }

}
