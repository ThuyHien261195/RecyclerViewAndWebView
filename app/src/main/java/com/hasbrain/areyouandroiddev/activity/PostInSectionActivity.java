package com.hasbrain.areyouandroiddev.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.ExpandableListRedditPostAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionActivity extends PostListActivity {

    @Nullable
    @BindView(R.id.expandable_lv_reddit_post)
    ExpandableListView expandableListRedditPost;

    @Override
    protected void displayPostList(List<RedditPost> postList) {
        List<String> groupHeaderList = createGroupHeaderList();
        HashMap<String, List<RedditPost>> redditPostChildList =
                createRedditPostChildList(groupHeaderList, postList);

        ExpandableListRedditPostAdapter redditPostAdapter =
                new ExpandableListRedditPostAdapter(this, groupHeaderList, redditPostChildList);
        expandableListRedditPost.setAdapter(redditPostAdapter);
        View footerView = this.getLayoutInflater().inflate(R.layout.item_footer, null);
        expandableListRedditPost.addFooterView(footerView);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }

    private List<String> createGroupHeaderList() {
        List<String> groupHeaderList = new ArrayList<>();
        groupHeaderList.add(getResources().getString(R.string.title_sticky_posts));
        groupHeaderList.add(getResources().getString(R.string.title_normal_posts));
        return groupHeaderList;
    }

    private HashMap<String, List<RedditPost>> createRedditPostChildList(
            List<String> groupHeaderList, List<RedditPost> postList) {
        HashMap<String, List<RedditPost>> redditPostChildList = new HashMap<>();
        List<RedditPost> stickyPostList = new ArrayList<>();
        List<RedditPost> normalPostList = new ArrayList<>();

        for (RedditPost postItem :
                postList) {
            if (postItem.isStickyPost()) {
                stickyPostList.add(postItem);
            } else {
                normalPostList.add(postItem);
            }
        }

        redditPostChildList.put(groupHeaderList.get(0), stickyPostList);
        redditPostChildList.put(groupHeaderList.get(1), normalPostList);
        return redditPostChildList;
    }
}
