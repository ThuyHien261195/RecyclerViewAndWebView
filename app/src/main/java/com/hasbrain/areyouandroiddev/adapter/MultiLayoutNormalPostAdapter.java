package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/25/17.
 */

public class MultiLayoutNormalPostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<RedditPost> redditPostList;
    private HashMap<String, String> timeTitleList;

    public MultiLayoutNormalPostAdapter(List<RedditPost> redditPostList, HashMap<String, String> timeTitleList) {
        this.redditPostList = redditPostList;
        this.timeTitleList = timeTitleList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view_normal_post, parent, false);
        return new PostViewHolder(rowView, timeTitleList);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        RedditPost redditPost = redditPostList.get(position);
        holder.bindContentPostView(redditPost);
    }

    @Override
    public int getItemCount() {
        return redditPostList.size();
    }
}
