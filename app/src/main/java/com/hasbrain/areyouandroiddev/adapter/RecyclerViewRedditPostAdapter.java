package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by thuyhien on 9/13/17.
 */

public class RecyclerViewRedditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<RedditPost> redditPostList;

    public RecyclerViewRedditPostAdapter(Context context, List<RedditPost> redditPostList) {
        this.context = context;
        this.redditPostList = redditPostList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = null;
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_view_post, parent, false);
                return new PostViewHolder(rowView);
            case ConstantCollection.FOOTER_VIEW:
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
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                bindContentView((PostViewHolder) holder, position);
                break;
            case ConstantCollection.FOOTER_VIEW:
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
            return ConstantCollection.FOOTER_VIEW;
        } else {
            return ConstantCollection.CONTENT_VIEW;
        }
    }

    private void setOnClickFooterView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(context, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                context.startActivity(postViewIntent);
            }
        });
    }

    private void bindContentView(PostViewHolder holder, int position) {
        final RedditPost redditPost = redditPostList.get(position);
        String authorTitle = FormatStringUtil.formatAuthorTitle(context,
                redditPost.getAuthor(),
                redditPost.getSubreddit());
        holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
        holder.textViewAuthor.setText(FormatStringUtil.fromHtml(authorTitle));
        holder.textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            holder.textViewPostTitle.setTextColor(
                    ContextCompat.getColor(context, R.color.color_sticky_post));
        }
        holder.textViewComment.setText(context.getResources()
                .getString(R.string.title_comment,
                        redditPost.getCommentCount(),
                        redditPost.getDomain()));
        holder.linearLayoutRedditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(context, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL, redditPost.getUrl());
                context.startActivity(postViewIntent);
            }
        });
    }
}
