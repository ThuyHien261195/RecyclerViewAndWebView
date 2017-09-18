package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.ListViewUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandRecyclerViewGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> colorTitleList = new ArrayList<Integer>();
    private List<String> titleList = new ArrayList<String>();
    private Context context;
    private List<String> postGroupHeaderList;
    private HashMap<String, List<RedditPost>> postDataChildList;

    public ExpandRecyclerViewGroupAdapter(Context context, List<String> postGroupHeader,
                                          HashMap<String, List<RedditPost>> postDataChildList) {
        this.context = context;
        this.postGroupHeaderList = postGroupHeader;
        this.postDataChildList = postDataChildList;
        titleList = ListViewUtil.setTitleList(context);
        colorTitleList = ListViewUtil.setColorTitleList(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == postGroupHeaderList.size()) {
            return ConstantCollection.FOOTER_VIEW;
        } else {
            return ConstantCollection.CONTENT_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = null;
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group_expand_rv_post, parent, false);
                return new ExpandRVGroupViewHolder(rowView);
            case ConstantCollection.FOOTER_VIEW:
                rowView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent, false);
                setOnClickFooterView(rowView);
                return new FooterViewHolder(rowView);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ConstantCollection.CONTENT_VIEW:
                String header = postGroupHeaderList.get(position);
                List<RedditPost> redditPostList = postDataChildList.get(header);
                ((ExpandRVGroupViewHolder) holder).bindGroupHeaderView(context, header, redditPostList);
                break;
            case ConstantCollection.FOOTER_VIEW:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return postGroupHeaderList.size() + 1;
    }

    private void setOnClickFooterView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(context, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                context.startActivity(postViewIntent);
            }
        });
    }
}
