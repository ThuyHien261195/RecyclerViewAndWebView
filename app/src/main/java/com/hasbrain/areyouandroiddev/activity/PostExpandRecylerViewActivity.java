package com.hasbrain.areyouandroiddev.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.ExpandRecyclerViewGroupAdapter;
import com.hasbrain.areyouandroiddev.adapter.ExpandRecyclerViewPostAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.ExpandRedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuyhien on 9/20/17.
 */

public class PostExpandRecylerViewActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;

    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView expandableRecyclerViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        ButterKnife.bind(this);
        initViews();
    }

    public void initViews() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
        Gson gson = gsonBuilder.create();
        InputStream is = null;
        try {
            is = getAssets().open(DATA_JSON_FILE_NAME);
            feedDataStore = new FileBasedFeedDataStore(gson, is);
            feedDataStore.getPostList(new FeedDataStore.OnRedditPostsRetrievedListener() {
                @Override
                public void onRedditPostsRetrieved(List<RedditPost> postList, Exception ex) {
                    displayPostList(postList);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void displayPostList(List<RedditPost> postList) {
        List<ExpandRedditPost> expandRedditPostList = createExpandRedditPostList(postList);
        bindDataToExpandableRecyclerView(expandRedditPostList);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_recycler_view;
    }

    private void bindDataToExpandableRecyclerView(List<ExpandRedditPost> expandRedditPostList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        expandableRecyclerViewPost.setLayoutManager(linearLayoutManager);
        ExpandRecyclerViewPostAdapter redditPostAdapter =
                new ExpandRecyclerViewPostAdapter(this, expandRedditPostList);
        expandableRecyclerViewPost.setAdapter(redditPostAdapter);
    }

    private List<String> createGroupHeaderList() {
        List<String> groupHeaderList = new ArrayList<>();
        groupHeaderList.add(getResources().getString(R.string.title_sticky_posts));
        groupHeaderList.add(getResources().getString(R.string.title_normal_posts));
        return groupHeaderList;
    }

    private List<ExpandRedditPost> createExpandRedditPostList(List<RedditPost> postList) {

        List<ExpandRedditPost> expandRedditPostList = new ArrayList<>();

        List<String> groupHeaderList = createGroupHeaderList();
        List<RedditPost> stickyPostList = new ArrayList<>();
        List<RedditPost> normalPostList = new ArrayList<>();

        for (RedditPost postItem :
                postList) {
            if (postItem.isStickyPost()) {
                stickyPostList.add(postItem);
            } else {
                normalPostList.add(postItem);
            }
        }

        expandRedditPostList.add(new ExpandRedditPost(groupHeaderList.get(0), stickyPostList));
        expandRedditPostList.add(new ExpandRedditPost(groupHeaderList.get(1), normalPostList));
        return expandRedditPostList;
    }
}
