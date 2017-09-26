package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandRVGroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    @BindView(R.id.recycler_view_child_post)
    RecyclerView recyclerViewPostChild;

    private boolean isExpandChildPostList = false;
    private HashMap<String, String> timeTitleList;

    ExpandRVGroupViewHolder(View view, HashMap<String, String> timeTitleList) {
        super(view);
        ButterKnife.bind(this, view);

        this.timeTitleList = timeTitleList;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewPostChild.setLayoutManager(linearLayoutManager);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandChildPostList();
            }
        });
    }

    protected void bindGroupHeaderView(String groupHeader, List<RedditPost> redditPostList) {
        textViewGroupHeader.setText(groupHeader);
        ExpandRecyclerViewChildAdapter expandRecyclerViewChildAdapter =
                new ExpandRecyclerViewChildAdapter(redditPostList, timeTitleList);
        recyclerViewPostChild.setAdapter(expandRecyclerViewChildAdapter);
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
