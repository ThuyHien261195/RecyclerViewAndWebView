package com.hasbrain.areyouandroiddev.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.FormatStringUtil;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyhien on 9/14/17.
 */

public class GroupViewRedditPostAdapter extends ArrayAdapter<RedditPost> {

    private List<Integer> colorTitleList = new ArrayList<Integer>();
    private List<String> titleList = new ArrayList<String>();
    private List<RedditPost> redditPostList;
    private Activity context;

    private int groupViewType;

    public GroupViewRedditPostAdapter(Activity context,
                                      List<RedditPost> redditPostList,
                                      int groupViewType) {
        super(context, R.layout.item_list_view_post, redditPostList);
        this.context = context;
        this.redditPostList = redditPostList;
        this.groupViewType = groupViewType;
        setColorTitleList();
        setTitleList();
    }

    @Override
    public int getCount() {
        return redditPostList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        int layoutRes;
        if (rowView == null) {
            switch (groupViewType) {
                case ConstantCollection.LIST_VIEW:
                    layoutRes = R.layout.item_list_view_post;
                    break;
                case ConstantCollection.GRID_VIEW:
                    layoutRes = R.layout.item_grid_view_post;
                    break;
                default:
                    layoutRes = R.layout.item_list_view_post;
                    break;
            }
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowView = inflater.inflate(layoutRes, parent, false);
            PostViewHolder postViewHolder = new PostViewHolder(rowView);
            rowView.setTag(postViewHolder);
        }

        bindContentView(rowView, position);
        return rowView;
    }

    private void bindContentView(View view, int position) {
        PostViewHolder holder = (PostViewHolder) view.getTag();
        final RedditPost redditPost = redditPostList.get(position);
        String postTime = FormatStringUtil.getPostTime(redditPost.getCreatedUTC(), titleList);
        setTextAuthorTitle(holder.textViewAuthor, redditPost);
        holder.textViewScore.setText(String.valueOf(redditPost.getScore()));
        holder.textViewPostTitle.setText(redditPost.getTitle());
        if (redditPost.isStickyPost()) {
            holder.textViewPostTitle.setTextColor(colorTitleList.get(1));
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

    private void setTextAuthorTitle(TextView textViewAuthor, RedditPost redditPost) {
        switch (groupViewType) {
            case ConstantCollection.LIST_VIEW:
                String authorTitle = FormatStringUtil.formatAuthorTitle(titleList.get(0),
                        redditPost.getAuthor(),
                        redditPost.getSubreddit(),
                        colorTitleList.get(0));
                textViewAuthor.setText(FormatStringUtil.fromHtml(authorTitle));
                break;
            case ConstantCollection.GRID_VIEW:
                textViewAuthor.setText(redditPost.getAuthor());
                break;
            default:
                break;
        }
    }

    private void setColorTitleList() {
        colorTitleList.add(ContextCompat.getColor(context, R.color.color_author_title));
        colorTitleList.add(ContextCompat.getColor(context, R.color.color_sticky_post));
    }

    private void setTitleList() {
        titleList.add(context.getResources().getString(R.string.title_author));
        titleList.add(context.getResources().getString(R.string.title_comment));
        titleList.add(context.getResources().getString(R.string.title_created_years));
        titleList.add(context.getResources().getString(R.string.title_created_months));
        titleList.add(context.getResources().getString(R.string.title_created_days));
        titleList.add(context.getResources().getString(R.string.title_created_hours));
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
