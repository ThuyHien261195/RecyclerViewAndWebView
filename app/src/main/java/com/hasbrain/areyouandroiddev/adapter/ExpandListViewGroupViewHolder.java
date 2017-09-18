package com.hasbrain.areyouandroiddev.adapter;

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

public class ExpandListViewGroupViewHolder {

    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    ExpandListViewGroupViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
