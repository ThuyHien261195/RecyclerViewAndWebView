package com.hasbrain.areyouandroiddev;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/13/17.
 */

public class RedditPostAdapter extends RecyclerView.Adapter<RedditPostAdapter.ViewHolder> {
    private Context context;
    private List<RedditPost> redditPostList;

    public RedditPostAdapter(Context context, List<RedditPost> redditPostList) {
        this.context = context;
        this.redditPostList = redditPostList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = 0;
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                layoutRes = R.layout.partial_post_list_1;
                break;
            case ConstantCollection.FOOTER_VIEW:
                layoutRes = R.layout.partial_post_list_2;
                break;
            default:
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    private void bindContentView(ViewHolder holder, int position) {
        RedditPost redditPost = redditPostList.get(position);
        String authorTitle = formatAuthorTitle(redditPost.getAuthor(), redditPost.getSubreddit());
        holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
        holder.textViewAuthor.setText(Html.fromHtml(authorTitle));
        holder.textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            holder.textViewPostTitle.setTextColor(ContextCompat.getColor(context, R.color.color_sticky_post));
        }
        holder.textViewComment.setText(context.getResources()
                .getString(R.string.title_comment,
                        redditPost.getCommentCount(),
                        redditPost.getDomain()));
    }

    private String getColoredSpanned(String text, int color) {
        return "<font color=" + context.getResources().getString(color) + ">" + text + "</font>";
    }

    private String formatAuthorTitle(String author, String subReddit) {
        author = getColoredSpanned(author, R.color.color_author_title);
        subReddit = getColoredSpanned(subReddit, R.color.color_author_title);
        String authorTitle = context.getResources().getString(
                R.string.title_author, author, subReddit);
        return authorTitle;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_score)
        TextView textViewScore;

        @BindView(R.id.text_author)
        TextView textViewAuthor;

        @BindView(R.id.text_post_title)
        TextView textViewPostTitle;

        @BindView(R.id.text_comment)
        TextView textViewComment;

        ViewHolder(View view, int viewType) {
            super(view);
            switch (viewType) {
                case ConstantCollection.CONTENT_VIEW:
                    ButterKnife.bind(this, view);
                    break;
                case ConstantCollection.FOOTER_VIEW:
                    break;
                default:
                    break;
            }
        }
    }
}
