package com.hasbrain.areyouandroiddev.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.ListViewUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyhien on 9/15/17.
 */

public class GridViewRedditPostAdapter extends ArrayAdapter<RedditPost> {

    private List<Integer> colorTitleList = new ArrayList<Integer>();
    private List<String> titleList = new ArrayList<String>();
    private List<RedditPost> redditPostList;
    private Activity context;

    public GridViewRedditPostAdapter(Activity context,
                                     List<RedditPost> redditPostList) {
        super(context, R.layout.item_list_view_post, redditPostList);
        this.context = context;
        this.redditPostList = redditPostList;
        titleList = ListViewUtil.setTitleList(context);
        colorTitleList = ListViewUtil.setColorTitleList(context);
    }

    @Override
    public int getCount() {
        return redditPostList.size() + 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;

        int itemViewType = getItemLayoutType(position);
        if (rowView == null || position == redditPostList.size()) {
            switch (itemViewType) {
                case ConstantCollection.CONTENT_VIEW:
                    rowView = getContentView(parent, rowView);
                    break;
                case ConstantCollection.FOOTER_VIEW:
                    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                    rowView = inflater.inflate(R.layout.item_footer, parent, false);
                    break;
                default:
                    break;
            }
        }

        PostViewHolder holder = (PostViewHolder) rowView.getTag();
        if (holder == null && itemViewType == ConstantCollection.CONTENT_VIEW) {
            rowView = getContentView(parent, rowView);
        }

        bindContentView(rowView, position);
        return rowView;
    }

    private View getContentView(@NonNull ViewGroup parent, View rowView) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        rowView = inflater.inflate(R.layout.item_grid_view_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(rowView);
        rowView.setTag(postViewHolder);
        return rowView;
    }

    private int getItemLayoutType(int position) {
        if (position == redditPostList.size()) {
            return ConstantCollection.FOOTER_VIEW;
        } else {
            return ConstantCollection.CONTENT_VIEW;
        }
    }

    private void bindContentView(View view, int position) {
        if (position < redditPostList.size()) {
            PostViewHolder holder = (PostViewHolder) view.getTag();
            final RedditPost redditPost = redditPostList.get(position);
            String postTime = FormatStringUtil.getPostTime(redditPost.getCreatedUTC(), titleList);
            holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
            holder.textViewAuthor.setText(redditPost.getAuthor());
            holder.textViewPostTitle.setText(redditPost.getTitle());
            if (redditPost.isStickyPost()) {
                holder.textViewPostTitle.setTextColor(colorTitleList.get(1));
            } else {
                holder.textViewPostTitle.setTextColor(colorTitleList.get(3));
            }
            holder.textViewComment.setText(String.format(titleList.get(1),
                    redditPost.getCommentCount(),
                    redditPost.getDomain(),
                    postTime));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postViewIntent = new Intent(context.getBaseContext(), PostViewActivity.class);
                    postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL, redditPost.getUrl());
                    context.startActivity(postViewIntent);
                }
            });
        }
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
