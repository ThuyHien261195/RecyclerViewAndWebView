package com.hasbrain.areyouandroiddev.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/25/17.
 */

public class MultiLayoutHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    public MultiLayoutHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindHeaderView(String title) {
        textViewGroupHeader.setText(title);
    }
}
