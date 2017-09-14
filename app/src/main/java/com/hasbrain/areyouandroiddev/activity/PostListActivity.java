package com.hasbrain.areyouandroiddev.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.ListViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.adapter.RecyclerViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostListActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;
    private int viewType = 0;

    @Nullable
    @BindView(R.id.recycler_view_reddit_post)
    RecyclerView recyclerViewRedditPost;

    @Nullable
    @BindView(R.id.list_view_reddit_post)
    ListView listViewRedditPost;

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
        switch (viewType) {
            case ConstantCollection.RECYCLER_VIEW:
                bindDataToRecyclerView(postList);
                break;
            case ConstantCollection.LIST_VIEW:
                bindDataToListView(postList);
                break;
            default:
                break;
        }
    }

    protected int getLayoutResource() {
        getViewType();
        int layoutRes = 0;
        switch (viewType) {
            case ConstantCollection.RECYCLER_VIEW:
                layoutRes = R.layout.activity_post_recycler_view;
                break;
            case ConstantCollection.LIST_VIEW:
                layoutRes = R.layout.activity_post_list_view;
                break;
            default:
                break;
        }
        return layoutRes;
    }

    private void getViewType() {
        viewType = getIntent().getIntExtra(ConstantCollection.EXTRA_NAME_LIST_VIEW_TYPE, 0);
    }

    private void bindDataToRecyclerView(List<RedditPost> postList) {
        // Add item null is a footer item
        RedditPost footerPostItem = new RedditPost();
        footerPostItem.setViewType(ConstantCollection.FOOTER_VIEW);
        postList.add(footerPostItem);

        if (recyclerViewRedditPost != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerViewRedditPost.setLayoutManager(linearLayoutManager);
            RecyclerViewRedditPostAdapter redditPostAdapter =
                    new RecyclerViewRedditPostAdapter(this, postList);
            recyclerViewRedditPost.setAdapter(redditPostAdapter);
        }
    }

    private void bindDataToListView(List<RedditPost> postList) {
        if (listViewRedditPost != null) {
            ListViewRedditPostAdapter lvRedditPostAdapter =
                    new ListViewRedditPostAdapter(this, postList);
            View footerView = getLayoutInflater().inflate(R.layout.item_footer, null);
            listViewRedditPost.addFooterView(footerView);
            listViewRedditPost.setAdapter(lvRedditPostAdapter);
            setOnClickFooterView(footerView);
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
