package com.hasbrain.areyouandroiddev.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/14/17.
 */

public class ListViewRedditPostAdapter extends ArrayAdapter<RedditPost> {

    private List<RedditPost> redditPostList;
    private HashMap<String, String> timeTitleList;

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


    public ListViewRedditPostAdapter(
            Activity context, List<RedditPost> redditPostList) {
        super(context, R.layout.item_list_view_post, redditPostList);
        this.redditPostList = redditPostList;
        this.timeTitleList = FormatStringUtil.createTimeTitleList(context);
    }

    @Override
    public int getCount() {
        return redditPostList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowView = inflater.inflate(R.layout.item_list_view_post, parent, false);
            PostViewHolder postViewHolder = new PostViewHolder(rowView);
            rowView.setTag(postViewHolder);
        }

        ButterKnife.bind(this, rowView);
        bindContentView(rowView, position);
        return rowView;
    }

    private void bindContentView(View view, int position) {
        PostViewHolder holder = (PostViewHolder) view.getTag();

        final RedditPost redditPost = redditPostList.get(position);
        String postTime = FormatStringUtil.getPostTime(redditPost.getCreatedUTC(), timeTitleList);
        String authorTitle = FormatStringUtil.formatAuthorTitle(authorText,
                redditPost.getAuthor(),
                redditPost.getSubreddit(),
                authorColor);
        holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
        holder.textViewAuthor.setText(FormatStringUtil.fromHtml(authorTitle));
        holder.textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            holder.textViewPostTitle.setTextColor(stickyColor);
        } else {
            holder.textViewPostTitle.setTextColor(normalColor);
        }
        holder.textViewComment.setText(String.format(commentText,
                redditPost.getCommentCount(),
                redditPost.getDomain(),
                postTime));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(v.getContext(), PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL, redditPost.getUrl());
                v.getContext().startActivity(postViewIntent);
            }
        });
    }

    static class PostViewHolder {
        public TextView textViewScore;
        public TextView textViewAuthor;
        public TextView textViewPostTitle;
        public TextView textViewComment;

        PostViewHolder(View view) {
            textViewScore = (TextView) view.findViewById(R.id.text_score);
            textViewAuthor = (TextView) view.findViewById(R.id.text_author);
            textViewPostTitle = (TextView) view.findViewById(R.id.text_post_title);
            textViewComment = (TextView) view.findViewById(R.id.text_comment);
        }
    }
}
