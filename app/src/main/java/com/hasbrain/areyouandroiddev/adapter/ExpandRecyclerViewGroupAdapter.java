package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandRecyclerViewGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CONTENT_VIEW = 0;
    public static final int FOOTER_VIEW = 1;

    private List<String> postGroupHeaderList;
    private HashMap<String, List<RedditPost>> postDataChildList;
    private HashMap<String, String> timeTitleList;

    public ExpandRecyclerViewGroupAdapter(Context context,
                                          List<String> postGroupHeader,
                                          HashMap<String, List<RedditPost>> postDataChildList) {
        this.postGroupHeaderList = postGroupHeader;
        this.postDataChildList = postDataChildList;
        this.timeTitleList = FormatStringUtil.createTimeTitleList(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == postGroupHeaderList.size()) {
            return FOOTER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        switch (viewType) {
            case CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group_expand_rv_post, parent, false);
                return new ExpandRVGroupViewHolder(rowView, timeTitleList);
            case FOOTER_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent, false);
                return new FooterViewHolder(rowView);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case CONTENT_VIEW:
                String header = postGroupHeaderList.get(position);
                List<RedditPost> redditPostList = postDataChildList.get(header);
                ((ExpandRVGroupViewHolder) holder).bindGroupHeaderView(header, redditPostList);
                break;
            case FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return postGroupHeaderList.size() + 1;
    }
}
