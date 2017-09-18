package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.ListViewUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandRecyclerViewChildAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Integer> colorTitleList = new ArrayList<Integer>();
    private List<String> titleList = new ArrayList<String>();
    private Context context;
    private List<RedditPost> redditPostList;

    public ExpandRecyclerViewChildAdapter(Context context, List<RedditPost> redditPostList) {
        this.context = context;
        this.redditPostList = redditPostList;
        titleList = ListViewUtil.setTitleList(context);
        colorTitleList = ListViewUtil.setColorTitleList(context);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view_post, parent, false);
        return new PostViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        RedditPost redditPost = redditPostList.get(position);
        holder.bindContentPostView(context, titleList, colorTitleList, redditPost);
    }

    @Override
    public int getItemCount() {
        return redditPostList.size();
    }
}
