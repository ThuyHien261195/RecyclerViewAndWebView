package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.View;

import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.List;

/**
 * Created by thuyhien on 9/18/17.
 */

public class LandscapePostViewHolder extends PostViewHolder {

    public LandscapePostViewHolder(View view) {
        super(view);
    }

    @Override
    public void bindContentPostView(Context context,
                                    List<String> titleList,
                                    List<Integer> colorTitleList,
                                    RedditPost redditPost) {
        super.bindContentPostView(context, titleList, colorTitleList, redditPost);
        textViewAuthor.setText(redditPost.getAuthor());
        if (redditPost.isStickyPost()) {
            textViewPostTitle.setTextColor(colorTitleList.get(1));
        } else {
            textViewPostTitle.setTextColor(colorTitleList.get(3));
        }
    }
}
