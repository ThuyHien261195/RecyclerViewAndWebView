package com.hasbrain.areyouandroiddev.adapter;

import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/18/17.
 */

public class ExpandableGroupViewHolder {
    @BindView(R.id.text_group_header)
    TextView textViewGroupHeader;

    public ExpandableGroupViewHolder(View view){
        ButterKnife.bind(this, view);
    }
}
