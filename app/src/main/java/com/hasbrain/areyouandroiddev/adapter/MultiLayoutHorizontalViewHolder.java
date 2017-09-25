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

    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView horizontalRecyclerViewRedditPost;

    public MultiLayoutHorizontalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindHorizontalPostView(Context context,
                                       List<RedditPost> redditPostList,
                                       HashMap<String, String> timeTitleList) {
        MultiLayoutNormalPostAdapter childAdapter = new MultiLayoutNormalPostAdapter(redditPostList, timeTitleList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerViewRedditPost.setLayoutManager(linearLayoutManager);
        horizontalRecyclerViewRedditPost.setAdapter(childAdapter);
    }
}
