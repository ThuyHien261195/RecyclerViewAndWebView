package com.hasbrain.areyouandroiddev.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;

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

    PostViewHolder(View view, int viewType) {
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
