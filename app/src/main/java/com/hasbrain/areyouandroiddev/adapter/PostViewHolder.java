package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/14/17.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.linear_layout_reddit_post)
    LinearLayout linearLayoutRedditPost;

    @BindView(R.id.text_score)
    TextView textViewScore;

    @BindView(R.id.text_author)
    TextView textViewAuthor;

    @BindView(R.id.text_post_title)
    TextView textViewPostTitle;

    @BindView(R.id.text_comment)
    TextView textViewComment;

    PostViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindContentPostView(final Context context, List<String> titleList,
                                    List<Integer> colorTitleList,
                                    final RedditPost redditPost){
        String postTime = FormatStringUtil.getPostTime(redditPost.getCreatedUTC(), titleList);
        textViewScore.setText(String.valueOf(redditPost.getScore()));
        String authorTitle = FormatStringUtil.formatAuthorTitle(titleList.get(0),
                redditPost.getAuthor(),
                redditPost.getSubreddit(),
                colorTitleList.get(0));
        textViewAuthor.setText(FormatStringUtil.fromHtml(authorTitle));
        textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            textViewPostTitle.setTextColor(colorTitleList.get(1));
        }
        textViewComment.setText(String.format(titleList.get(1),
                redditPost.getCommentCount(),
                redditPost.getDomain(),
                postTime));
        linearLayoutRedditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(context, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL, redditPost.getUrl());
                context.startActivity(postViewIntent);
            }
        });
    }
}
