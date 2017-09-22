package com.hasbrain.areyouandroiddev.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.activity.PostViewActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/14/17.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_footer)
    TextView textViewFooter;

    FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(v.getContext(), PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                v.getContext().startActivity(postViewIntent);
            }
        });
    }
}
