package com.hasbrain.areyouandroiddev.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.GridViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.adapter.ListViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.adapter.RecyclerViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostRecyclerViewActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    public static final int NUMBER_POST_COLUMN = 3;

    private FeedDataStore feedDataStore;
    private RecyclerViewRedditPostAdapter redditPostAdapter;

    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView recyclerViewRedditPost;

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
        bindDataToRecyclerView(postList);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_recycler_view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutPostView();
    }

    private void setLayoutPostView() {
        int orientation = getResources().getConfiguration().orientation;
        View layout = findViewById(R.id.layout_landscape_reddit_post);

        if (recyclerViewRedditPost != null) {
            switch (orientation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    recyclerViewRedditPost.setLayoutManager(linearLayoutManager);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(
                            this, NUMBER_POST_COLUMN);
                    recyclerViewRedditPost.setLayoutManager(gridLayoutManager);
                    break;
                default:
                    break;
            }
        }
        recyclerViewRedditPost.setAdapter(redditPostAdapter);
    }

    private void bindDataToRecyclerView(List<RedditPost> postList) {
        if (recyclerViewRedditPost != null) {
            redditPostAdapter = new RecyclerViewRedditPostAdapter(this, postList);
            setLayoutPostView();
        }
    }
}
