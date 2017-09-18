package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandRVGroupViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.linear_layout_child_post)
    LinearLayout linearLayoutChildPost;

    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    @BindView(R.id.recycler_view_child_post)
    RecyclerView recyclerViewPostChild;

    private boolean isExpandChildPostList = false;

    ExpandRVGroupViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindGroupHeaderView(
            Context context, String groupHeader, List<RedditPost> redditPostList) {
        textViewGroupHeader.setText(groupHeader);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewPostChild.setLayoutManager(linearLayoutManager);
        ExpandRecyclerViewChildAdapter expandRecyclerViewChildAdapter =
                new ExpandRecyclerViewChildAdapter(context, redditPostList);
        recyclerViewPostChild.setAdapter(expandRecyclerViewChildAdapter);

        linearLayoutChildPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandChildPostList();
            }
        });
    }

    private void expandChildPostList() {
        if (isExpandChildPostList) {
            recyclerViewPostChild.setVisibility(View.GONE);
            isExpandChildPostList = false;
        } else {
            recyclerViewPostChild.setVisibility(View.VISIBLE);
            isExpandChildPostList = true;
        }
    }
}
