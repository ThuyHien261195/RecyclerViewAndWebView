package com.hasbrain.areyouandroiddev.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;

import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thuyhien on 9/20/17.
 */

public class ExpandNewGroupRVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    private int groupIndex;
    private OnGroupHeaderListener onGroupHeaderListener;

    ExpandNewGroupRVHolder(final OnGroupHeaderListener onGroupHeaderListener, View view) {
        super(view);
        ButterKnife.bind(this, view);

        this.onGroupHeaderListener = onGroupHeaderListener;
        view.setOnClickListener(this);
    }

    protected void bindGroupView(String header, int parentId) {
        this.groupIndex = parentId;
        textViewGroupHeader.setText(header);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (onGroupHeaderListener != null) {
            onGroupHeaderListener.onGroupItemClick(position, groupIndex);
        }
    }

    public interface OnGroupHeaderListener {
        void onGroupItemClick(int position, int groupIndex);
    }
}
