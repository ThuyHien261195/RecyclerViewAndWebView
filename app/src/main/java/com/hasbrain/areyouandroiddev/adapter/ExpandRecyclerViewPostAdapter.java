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
import com.hasbrain.areyouandroiddev.model.ExpandRedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostIndex;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/20/17.
 */

public class ExpandRecyclerViewPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ExpandNewGroupGVHolder.OnGroupHeaderListener {

    public static final int GROUP_VIEW = 0;
    public static final int CHILD_CONTENT_VIEW = 1;
    public static final int FOOTER_VIEW = 2;

    private List<ExpandRedditPost> expandRedditPostList;
    private HashMap<String, String> timeTitleList;

    public ExpandRecyclerViewPostAdapter(Context context, List<ExpandRedditPost> expandRedditPostList) {
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
                return new ExpandNewGroupGVHolder(
                        (ExpandNewGroupGVHolder.OnGroupHeaderListener) this, rowView);
            case CHILD_CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_post, parent, false);
                return new PostViewHolder(rowView);
            case FOOTER_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent, false);
                setOnClickFooterView(rowView);
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
                ((ExpandNewGroupGVHolder) holder).bindGroupView(header, redditPostIndex.getGroupIndex());
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
        int expandRedditPostItemId = 0;
        if (position == count) {
            return FOOTER_VIEW;
        }

        int numItems = 0;
        for (ExpandRedditPost expandRedditPost : expandRedditPostList) {
            if (numItems == position) {
                return GROUP_VIEW;
            } else if (numItems > position) {
                return CHILD_CONTENT_VIEW;
            }
            if (expandRedditPost.isExpandGroup()) {
                numItems += expandRedditPost.getChildRedditPostList().size() + 1;
            } else {
                numItems++;
            }
        }
        return CHILD_CONTENT_VIEW;
    }

    @Override
    public int getItemCount() {
        // ExpandRedditPostList + 1 for Footer View
        int count = expandRedditPostList.size() + 1;
        for (ExpandRedditPost expandRedditPost : expandRedditPostList) {
            if (expandRedditPost.isExpandGroup()) {
                count += expandRedditPost.getChildRedditPostList().size();
            }
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

    private void setOnClickFooterView(View rowView) {
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostViewActivity.class);
                intent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                v.getContext().startActivity(intent);
            }
        });
    }

    private RedditPostIndex getRealPositionInList(int position) {
        int numItems = 0;
        int count = getItemCount() - 1;

        for (int i = 0; i < expandRedditPostList.size(); i++) {
            ExpandRedditPost expandRedditPost = expandRedditPostList.get(i);
            if (numItems == position) {
                return new RedditPostIndex(i, -1);
            } else if (numItems > position) {
                int groupIndex = numItems
                        - expandRedditPostList.get(i - 1).getChildRedditPostList().size() - 1;
                int childIndex = position - groupIndex - 1;
                return new RedditPostIndex(i - 1, childIndex);
            }
            if (expandRedditPost.isExpandGroup()) {
                numItems += expandRedditPost.getChildRedditPostList().size() + 1;
            } else {
                numItems++;
            }
        }

        if (numItems > position) {
            int lastGroupIndex = expandRedditPostList.size() - 1;
            int groupIndex = numItems
                    - expandRedditPostList.get(lastGroupIndex).getChildRedditPostList().size() - 1;
            int childIndex = position - groupIndex - 1;
            return new RedditPostIndex(lastGroupIndex, childIndex);
        }

        return null;
    }
}
