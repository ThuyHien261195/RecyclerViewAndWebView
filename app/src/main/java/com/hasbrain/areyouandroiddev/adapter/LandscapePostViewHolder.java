package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.View;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class LandscapePostViewHolder extends PostViewHolder {

    public LandscapePostViewHolder(View view, HashMap<String, String> timeTitleList) {
        super(view, timeTitleList);
    }

    @Override
    protected void bindAuthorText() {
        textViewAuthor.setText(post.getAuthor());
    }
}
