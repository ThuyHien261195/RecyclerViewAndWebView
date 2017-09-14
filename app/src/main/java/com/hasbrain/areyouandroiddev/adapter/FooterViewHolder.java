package com.hasbrain.areyouandroiddev.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/14/17.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_footer)
    TextView textViewFooter;

    FooterViewHolder(View view){
        super(view);
        ButterKnife.bind(this, view);
    }
}
