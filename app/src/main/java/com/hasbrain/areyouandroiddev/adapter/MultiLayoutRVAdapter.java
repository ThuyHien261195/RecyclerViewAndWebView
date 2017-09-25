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
    public static final int NORMAL_VIEW = 1;
    public static final int TITLE_VIEW = 2;
    public static final int FOOTER_VIEW = 3;

    private List<ExpandRedditPost> expandRedditPostList;
    private HashMap<String, String> timeTitleList;
    private Context context;

    private String stickyHeaderTitle;
    private String normalHeaderTitle;

    public MultiLayoutRVAdapter(Context context,
                                List<ExpandRedditPost> expandRedditPostList) {
        this.expandRedditPostList = expandRedditPostList;
        this.timeTitleList = FormatStringUtil.createTimeTitleList(context);
        this.context = context;
        stickyHeaderTitle = context.getString(R.string.title_sticky_posts);
        normalHeaderTitle = context.getString(R.string.title_normal_posts);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        switch (viewType) {
            case STICKY_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_post, parent, false);
                return new PostViewHolder(rowView);
            case NORMAL_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_recycler, parent, false);
                return new MultiLayoutHorizontalViewHolder(rowView);
            case TITLE_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_group, parent, false);
                return new MultiLayoutHeaderViewHolder(rowView);
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
        int viewType = getItemViewType(position);
        ExpandRedditPost expandRedditPost;
        switch (viewType) {
            case STICKY_VIEW:
                expandRedditPost = expandRedditPostList.get(
                        getPositionOfGroupList(stickyHeaderTitle));
                RedditPost redditPost = expandRedditPost.getChildRedditPostList().get(position);
                ((PostViewHolder) holder).bindContentPostView(redditPost, timeTitleList);
                break;
            case NORMAL_VIEW:
                expandRedditPost = expandRedditPostList.get(
                        getPositionOfGroupList(normalHeaderTitle));
                List<RedditPost> redditPostList = expandRedditPost.getChildRedditPostList();
                ((MultiLayoutHorizontalViewHolder) holder).bindHorizontalPostView(context,
                        redditPostList,
                        timeTitleList);
                break;
            case TITLE_VIEW:
                expandRedditPost = expandRedditPostList.get(
                        getPositionOfGroupList(normalHeaderTitle));
                String title = expandRedditPost.getGroupHeader();
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

        ExpandRedditPost stickyPostList = expandRedditPostList.get(
                getPositionOfGroupList(stickyHeaderTitle));
        ExpandRedditPost normalPostList = expandRedditPostList.get(
                getPositionOfGroupList(normalHeaderTitle));
        if (position >= 0
                && position < stickyPostList.getChildRedditPostList().size()) {
            return STICKY_VIEW;
        } else if (position == stickyPostList.getChildRedditPostList().size()) {
            return TITLE_VIEW;
        } else if (position == itemTotal - 1) {
            return FOOTER_VIEW;
        }
        return NORMAL_VIEW;
    }

    @Override
    public int getItemCount() {
        // 1 for Title Normal Post and 1 for Footer View.
        int itemCount = 3;

        int stickyInGroupListPos = getPositionOfGroupList(stickyHeaderTitle);
        itemCount += expandRedditPostList.get(stickyInGroupListPos).getChildRedditPostList().size();
        return itemCount;
    }

    private int getPositionOfGroupList(String header) {
        for (int i = 0; i < expandRedditPostList.size(); i++) {
            if (expandRedditPostList.get(i).getGroupHeader().equals(header)) {
                return i;
            }
        }
        return -1;
    }
}