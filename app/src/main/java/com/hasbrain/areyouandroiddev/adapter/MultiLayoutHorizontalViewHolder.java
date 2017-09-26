package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/25/17.
 */

public class MultiLayoutHorizontalViewHolder extends RecyclerView.ViewHolder {

    private final HashMap<String, String> timeTitleList;
    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView horizontalRecyclerViewRedditPost;

    public MultiLayoutHorizontalViewHolder(View itemView, HashMap<String, String> timeTitleList) {
        super(itemView);
        this.timeTitleList = timeTitleList;
        ButterKnife.bind(this, itemView);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerViewRedditPost.setLayoutManager(linearLayoutManager);
    }

    public void bindHorizontalPostView(List<RedditPost> redditPostList) {
        MultiLayoutNormalPostAdapter childAdapter =
                new MultiLayoutNormalPostAdapter(redditPostList, timeTitleList);
        horizontalRecyclerViewRedditPost.setAdapter(childAdapter);
    }
}
