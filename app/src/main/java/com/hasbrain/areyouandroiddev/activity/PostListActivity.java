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

public class PostListActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    public static final String EXTRA_NAME_GROUP_VIEW_TYPE = "groupViewType";
    public static final int RECYCLER_VIEW = 0;
    public static final int LIST_VIEW = 2;
    public static final int GRID_VIEW = 3;
    public static final int NUMBER_POST_COLUMN = 3;

    private FeedDataStore feedDataStore;
    protected int viewType = 0;
    private RecyclerViewRedditPostAdapter redditPostAdapter;

    @Nullable
    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView recyclerViewRedditPost;

    @Nullable
    @BindView(R.id.list_view_reddit_post)
    ListView listViewRedditPost;

    @Nullable
    @BindView(R.id.grid_view_reddit_post)
    GridView gridViewRedditPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        ButterKnife.bind(this);
        setScreenOrientation();
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
        switch (viewType) {
            case RECYCLER_VIEW:
                bindDataToRecyclerView(postList);
                break;
            case LIST_VIEW:
                bindDataToListView(postList);
                break;
            case GRID_VIEW:
                bindDataToGridView(postList);
            default:
                break;
        }
    }

    protected int getLayoutResource() {
        getViewType();
        int layoutRes = 0;
        switch (viewType) {
            case RECYCLER_VIEW:
                layoutRes = R.layout.activity_post_recycler_view;
                break;
            case LIST_VIEW:
                layoutRes = R.layout.activity_post_list_view;
                break;
            case GRID_VIEW:
                layoutRes = R.layout.activity_post_grid_view;
            default:
                break;
        }
        return layoutRes;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutPostView();
    }

    private void setScreenOrientation() {
        switch (viewType) {
            case LIST_VIEW:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case GRID_VIEW:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                break;
        }
    }

    protected void getViewType() {
        viewType = getIntent().getIntExtra(EXTRA_NAME_GROUP_VIEW_TYPE, 0);
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

    private void bindDataToListView(List<RedditPost> postList) {
        if (listViewRedditPost != null) {
            ListViewRedditPostAdapter listViewRedditPostAdapter =
                    new ListViewRedditPostAdapter(this, postList);
            View footerView = getLayoutInflater().inflate(R.layout.item_footer, null);
            listViewRedditPost.addFooterView(footerView);
            listViewRedditPost.setAdapter(listViewRedditPostAdapter);
            setOnClickFooterView(footerView);
        }
    }

    private void bindDataToGridView(List<RedditPost> postList) {
        if (gridViewRedditPost != null) {
            GridViewRedditPostAdapter gridViewRedditPostAdapter =
                    new GridViewRedditPostAdapter(this, postList);
            gridViewRedditPost.setAdapter(gridViewRedditPostAdapter);
        }
    }

    private void setOnClickFooterView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(PostListActivity.this, PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                startActivity(postViewIntent);
            }
        });
    }
}
