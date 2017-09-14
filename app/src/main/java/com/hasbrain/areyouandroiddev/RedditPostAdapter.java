package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by thuyhien on 9/13/17.
 */

public class RedditPostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private Context context;
    private List<RedditPost> redditPostList;

    public RedditPostAdapter(Context context, List<RedditPost> redditPostList) {
        this.context = context;
        this.redditPostList = redditPostList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                view = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.partial_post_list_1, parent, false);
                break;
            case ConstantCollection.FOOTER_VIEW:
                view = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.partial_post_list_2, parent, false);
                setOnClickFooterView(view);
            default:
                break;
        }
        return new PostViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                bindContentView(holder, position);
                break;
            case ConstantCollection.FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return redditPostList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return redditPostList.get(position).getViewType();
    }

    private void setOnClickFooterView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(context, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_URL_MORE_INFO);
                context.startActivity(postViewIntent);
            }
        });
    }

    private void bindContentView(PostViewHolder holder, int position) {
        final RedditPost redditPost = redditPostList.get(position);
        String authorTitle = formatAuthorTitle(redditPost.getAuthor(), redditPost.getSubreddit());
        holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
        holder.textViewAuthor.setText(fromHtml(authorTitle));
        holder.textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            holder.textViewPostTitle.setTextColor(ContextCompat.getColor(context, R.color.color_sticky_post));
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

    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String author) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(author, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(author);
        }
        return result;
    }

    private String getColoredSpanned(String text, int color) {
        color = ContextCompat.getColor(context, color);
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        return "<font color='" + hexColor + "'>" + text + "</font>";
    }

    private String formatAuthorTitle(String author, String subReddit) {
        author = getColoredSpanned(author, R.color.color_author_title);
        subReddit = getColoredSpanned(subReddit, R.color.color_author_title);
        String authorTitle = context.getResources().getString(
                R.string.title_author, author, subReddit);
        return authorTitle;
    }
}
