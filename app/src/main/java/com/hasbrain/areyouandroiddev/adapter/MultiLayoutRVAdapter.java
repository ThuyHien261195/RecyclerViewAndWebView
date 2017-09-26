package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.ExpandRedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/25/17.
 */

public class MultiLayoutRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int STICKY_VIEW = 0;
    public static final int NORMAL_HORIZONTAL_LIST = 1;
    public static final int TITLE_VIEW = 2;
    public static final int FOOTER_VIEW = 3;

    private ExpandRedditPost stickyPostList;
    private ExpandRedditPost normalPostList;
    private HashMap<String, String> timeTitleList;

    public MultiLayoutRVAdapter(Context context,
                                ExpandRedditPost stickyPostList,
                                ExpandRedditPost normalPostList) {
        this.stickyPostList = stickyPostList;
        this.normalPostList = normalPostList;
        this.timeTitleList = FormatStringUtil.createTimeTitleList(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case STICKY_VIEW:
                rowView = inflater.inflate(R.layout.item_card_view_post, parent, false);
                return new PostViewHolder(rowView, timeTitleList);
            case NORMAL_HORIZONTAL_LIST:
                rowView = inflater.inflate(R.layout.item_card_view_recycler, parent, false);
                return new MultiLayoutHorizontalViewHolder(rowView, timeTitleList);
            case TITLE_VIEW:
                rowView = inflater.inflate(R.layout.item_card_view_group, parent, false);
                return new MultiLayoutHeaderViewHolder(rowView);
            case FOOTER_VIEW:
                rowView = inflater.inflate(R.layout.item_footer, parent, false);
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
            case STICKY_VIEW:
                RedditPost redditPost = stickyPostList.getChildRedditPostList().get(position);
                ((PostViewHolder) holder).bindContentPostView(redditPost);
                break;
            case NORMAL_HORIZONTAL_LIST:
                List<RedditPost> redditPostList = normalPostList.getChildRedditPostList();
                ((MultiLayoutHorizontalViewHolder) holder).bindHorizontalPostView(redditPostList);
                break;
            case TITLE_VIEW:
                String title = normalPostList.getGroupHeader();
                ((MultiLayoutHeaderViewHolder) holder).bindHeaderView(title);
                break;
            case FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemTotal = getItemCount();
        if (position >= 0
                && position < stickyPostList.getChildRedditPostList().size()) {
            return STICKY_VIEW;
        } else if (position == stickyPostList.getChildRedditPostList().size()) {
            return TITLE_VIEW;
        } else if (position == itemTotal - 1) {
            return FOOTER_VIEW;
        }
        return NORMAL_HORIZONTAL_LIST;
    }

    @Override
    public int getItemCount() {
        // Title Normal Post, Reycler View of normal post list and Footer View.
        int itemCount = 3;
        itemCount += stickyPostList.getChildRedditPostList().size();
        return itemCount;
    }
}