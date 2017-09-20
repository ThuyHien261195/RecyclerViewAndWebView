package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/14/17.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_score)
    TextView textViewScore;

    @BindView(R.id.text_author)
    TextView textViewAuthor;

    @BindView(R.id.text_post_title)
    TextView textViewPostTitle;

    @BindView(R.id.text_comment)
    TextView textViewComment;

    @BindString(R.string.title_author)
    String authorText;

    @BindString(R.string.title_comment)
    String commentText;

    @BindColor(R.color.color_author_title)
    int authorColor;

    @BindColor(R.color.color_sticky_post)
    int stickyColor;

    @BindColor(R.color.color_normal_post)
    int normalColor;

    protected RedditPost post;

    PostViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(v.getContext(), PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL, post.getUrl());
                v.getContext().startActivity(postViewIntent);
            }
        });
    }

    protected void bindContentPostView(final RedditPost redditPost, HashMap<String, String> timeTitleList) {
        this.post = redditPost;
        String postTime = FormatStringUtil.getPostTime(redditPost.getCreatedUTC(), timeTitleList);
        textViewScore.setText(String.valueOf(redditPost.getScore()));
        textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            textViewPostTitle.setTextColor(stickyColor);
        } else {
            textViewPostTitle.setTextColor(normalColor);
        }
        textViewComment.setText(String.format(commentText,
                redditPost.getCommentCount(),
                redditPost.getDomain(),
                postTime));

        bindAuthorText();
    }

    protected void bindAuthorText() {
        String authorTitle = FormatStringUtil.formatAuthorTitle(authorText,
                post.getAuthor(),
                post.getSubreddit(),
                authorColor);
        textViewAuthor.setText(FormatStringUtil.fromHtml(authorTitle));
    }
}
