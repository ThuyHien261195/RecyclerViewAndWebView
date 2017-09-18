package com.hasbrain.areyouandroiddev.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.ExpandableListRedditPostAdapter;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

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

        switch (viewType) {
            case ConstantCollection.EXPANDABLE_LIST_VIEW:
                bindDataToExpandableListView(groupHeaderList, redditPostChildList);
                break;
            case ConstantCollection.EXPANDABLE_RECYCLER_VIEW:
                bindDataToExpandableRecyclerView(groupHeaderList, redditPostChildList);
                break;
            default:
                break;
        }

    }

    @Override
    protected int getLayoutResource() {
        int layoutRes = 0;
        switch (viewType) {
            case ConstantCollection.EXPANDABLE_LIST_VIEW:
                layoutRes = R.layout.activity_post_in_section;
                break;
            case ConstantCollection.EXPANDABLE_RECYCLER_VIEW:
                layoutRes = R.layout.activity_post_recycler_view;
                break;
            default:
                break;
        }
        return layoutRes;
    }

    private void bindDataToExpandableListView(
            List<String> groupHeaderList, HashMap<String, List<RedditPost>> redditPostChildList) {
        ExpandableListRedditPostAdapter redditPostAdapter =
                new ExpandableListRedditPostAdapter(this, groupHeaderList, redditPostChildList);
        expandableListRedditPost.setAdapter(redditPostAdapter);
        View footerView = this.getLayoutInflater().inflate(R.layout.item_footer, null);
        expandableListRedditPost.addFooterView(footerView);
    }

    private void bindDataToExpandableRecyclerView(
            List<String> groupHeaderList, HashMap<String, List<RedditPost>> redditPostChildList) {

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
