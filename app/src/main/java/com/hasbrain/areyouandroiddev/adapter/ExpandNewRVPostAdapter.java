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
import com.hasbrain.areyouandroiddev.model.RedditPostIndex;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/20/17.
 */

public class ExpandNewRVPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ExpandNewGroupRVHolder.OnGroupHeaderListener {

    public static final int GROUP_VIEW = 0;
    public static final int CHILD_CONTENT_VIEW = 1;
    public static final int FOOTER_VIEW = 2;

    private List<ExpandRedditPost> expandRedditPostList;
    private HashMap<String, String> timeTitleList;

    public ExpandNewRVPostAdapter(Context context, List<ExpandRedditPost> expandRedditPostList) {
        this.expandRedditPostList = expandRedditPostList;
        timeTitleList = FormatStringUtil.createTimeTitleList(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        switch (viewType) {
            case GROUP_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_group, parent, false);
                return new ExpandNewGroupRVHolder(
                        (ExpandNewGroupRVHolder.OnGroupHeaderListener) this, rowView);
            case CHILD_CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_post, parent, false);
                return new PostViewHolder(rowView);
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
        RedditPostIndex redditPostIndex = getRealPositionInList(position);

        if (redditPostIndex == null) {
            return;
        }

        switch (viewType) {
            case GROUP_VIEW:
                String header = expandRedditPostList.get(
                        redditPostIndex.getGroupIndex()).getGroupHeader();
                ((ExpandNewGroupRVHolder) holder).bindGroupView(header, redditPostIndex.getGroupIndex());
                break;
            case CHILD_CONTENT_VIEW:
                ExpandRedditPost expandRedditPost = expandRedditPostList.get(redditPostIndex.getGroupIndex());
                RedditPost redditPost = expandRedditPost.getChildRedditPostList()
                        .get(redditPostIndex.getChildIndex());
                ((PostViewHolder) holder).bindContentPostView(redditPost, timeTitleList);
                break;
            case FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int count = getItemCount() - 1;
        if (position == count) {
            return FOOTER_VIEW;
        }

        int numItems = 0;
        for (int i = 0; i < expandRedditPostList.size(); i++) {
            if (numItems == position) {
                return GROUP_VIEW;
            } else if (numItems > position) {
                return CHILD_CONTENT_VIEW;
            }
            numItems += getVisibleNumberOfItemInGroup(i);
        }
        return CHILD_CONTENT_VIEW;
    }

    @Override
    public int getItemCount() {
        // ExpandRedditPostList + 1 for Footer View
        int count = 1;
        for (int i = 0; i < expandRedditPostList.size(); i++) {
            count += getVisibleNumberOfItemInGroup(i);
        }
        return count;
    }

    @Override
    public void onGroupItemClick(int position, int groupIndex) {
        ExpandRedditPost expandRedditPost = expandRedditPostList.get(groupIndex);
        if (expandRedditPost.isExpandGroup()) {
            expandRedditPost.setExpandGroup(false);
            notifyItemRangeRemoved(position + 1, expandRedditPost.getChildRedditPostList().size());
        } else {
            expandRedditPost.setExpandGroup(true);
            notifyItemRangeInserted(position + 1, expandRedditPost.getChildRedditPostList().size());
        }
    }

    private int getVisibleNumberOfItemInGroup(int i) {
        ExpandRedditPost expandRedditPost = expandRedditPostList.get(i);
        if (expandRedditPost.isExpandGroup()) {
            return expandRedditPostList.get(i).getChildRedditPostList().size() + 1;
        }
        return 1;
    }

    private RedditPostIndex getRealPositionInList(int position) {
        int visibleNumberItems = 0;

        for (int i = 0; i < expandRedditPostList.size(); i++) {
            if (visibleNumberItems == position) {
                return new RedditPostIndex(i, -1);
            }

            visibleNumberItems += getVisibleNumberOfItemInGroup(i);

            if (visibleNumberItems > position) {
                int childIndex = getChildIndex(visibleNumberItems, position, i);
                return new RedditPostIndex(i, childIndex);
            }
        }
        return null;
    }

    private int getChildIndex(int currentPos, int position, int groupIndex) {
        int expandGroupIndex = currentPos
                - expandRedditPostList.get(groupIndex).getChildRedditPostList().size() - 1;
        return position - expandGroupIndex - 1;
    }
}
