package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/13/17.
 */

public class RecyclerViewRedditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CONTENT_VIEW = 0;
    public static final int FOOTER_VIEW = 1;

    private final int orientation;
    private List<RedditPost> redditPostList;
    private HashMap<String, String> timeTitleList;

    public RecyclerViewRedditPostAdapter(Context context, List<RedditPost> redditPostList) {
        this.redditPostList = redditPostList;
        this.timeTitleList = FormatStringUtil.createTimeTitleList(context);
        orientation = context.getResources().getConfiguration().orientation;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        switch (viewType) {
            case CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_post, parent, false);

                return createContentViewHolder(rowView);
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

    private PostViewHolder createContentViewHolder(View rowView) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new PostViewHolder(rowView);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new LandscapePostViewHolder(rowView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case CONTENT_VIEW:
                ((PostViewHolder) holder).bindContentPostView(
                        redditPostList.get(position), timeTitleList);
                break;
            case FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        // RedditPostList + 1 for Footer View
        return redditPostList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == redditPostList.size()) {
            return FOOTER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    private void setOnClickFooterView(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(view.getContext(), PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                view.getContext().startActivity(postViewIntent);
            }
        });
    }
}
