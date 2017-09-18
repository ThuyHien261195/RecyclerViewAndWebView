package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.hasbrain.areyouandroiddev.ListViewUtil;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandListViewRedditPostAdapter extends BaseExpandableListAdapter {

    private List<Integer> colorTitleList = new ArrayList<Integer>();
    private List<String> titleList = new ArrayList<String>();
    private Context context;
    private List<String> postGroupHeaderList;
    private HashMap<String, List<RedditPost>> postDataChildList;

    public ExpandListViewRedditPostAdapter(Context context, List<String> postGroupHeader,
                                           HashMap<String, List<RedditPost>> postDataChildList) {
        this.context = context;
        this.postGroupHeaderList = postGroupHeader;
        this.postDataChildList = postDataChildList;
        titleList = ListViewUtil.setTitleList(context);
        colorTitleList = ListViewUtil.setColorTitleList(context);
    }

    @Override
    public int getGroupCount() {
        return postGroupHeaderList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String postGroupHeader = postGroupHeaderList.get(groupPosition);
        return postDataChildList.get(postGroupHeader).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return postGroupHeaderList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String postGroupHeader = postGroupHeaderList.get(groupPosition);
        return postDataChildList.get(postGroupHeader).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View groupRowView = convertView;
        if (groupRowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            groupRowView = inflater.inflate(R.layout.item_group_expand_lv_post, parent, false);
            ExpandListViewGroupViewHolder groupViewHolder = new ExpandListViewGroupViewHolder(groupRowView);
            groupRowView.setTag(groupViewHolder);
        }

        bindGroupView(groupRowView, groupPosition);
        return groupRowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View childRowView = convertView;
        if (childRowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            childRowView = inflater.inflate(R.layout.item_list_view_post, parent, false);
            PostViewHolder postViewHolder = new PostViewHolder(childRowView);
            childRowView.setTag(postViewHolder);
        }

        bindChildPostView(childRowView, groupPosition, childPosition);
        return childRowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void bindGroupView(View groupRowView, int groupPosition) {
        ExpandListViewGroupViewHolder groupViewHolder = (ExpandListViewGroupViewHolder) groupRowView.getTag();
        groupViewHolder.textViewGroupHeader.setText(postGroupHeaderList.get(groupPosition));
    }

    private void bindChildPostView(View childRowView, int groupPosition, int childPosition) {
        RedditPost redditPost = (RedditPost) this.getChild(groupPosition, childPosition);
        PostViewHolder postViewHolder = (PostViewHolder) childRowView.getTag();
        postViewHolder.bindContentPostView(context, titleList, colorTitleList, redditPost);
    }
}
