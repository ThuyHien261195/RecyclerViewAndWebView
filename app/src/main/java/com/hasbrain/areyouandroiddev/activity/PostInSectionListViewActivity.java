package com.hasbrain.areyouandroiddev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hasbrain.areyouandroiddev.ConstantCollection;
import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.adapter.ExpandListViewRedditPostAdapter;
import com.hasbrain.areyouandroiddev.adapter.ExpandRecyclerViewGroupAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
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
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/9/15.
 */
public class PostInSectionListViewActivity extends AppCompatActivity {

    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;

    @BindView(R.id.expandable_lv_reddit_post)
    ExpandableListView expandableListRedditPost;

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
        List<String> groupHeaderList = createGroupHeaderList();
        HashMap<String, List<RedditPost>> redditPostChildList =
                createRedditPostChildList(groupHeaderList, postList);

        bindDataToExpandableListView(groupHeaderList, redditPostChildList);
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_in_section;
    }

    private void bindDataToExpandableListView(
            List<String> groupHeaderList, HashMap<String, List<RedditPost>> redditPostChildList) {
        ExpandListViewRedditPostAdapter redditPostAdapter =
                new ExpandListViewRedditPostAdapter(this, groupHeaderList, redditPostChildList);
        expandableListRedditPost.setAdapter(redditPostAdapter);
        View footerView = this.getLayoutInflater().inflate(R.layout.item_footer, null);
        expandableListRedditPost.addFooterView(footerView);
        setOnClickFooterView(footerView);
    }

    private List<String> createGroupHeaderList() {
        List<String> groupHeaderList = new ArrayList<>();
        groupHeaderList.add(getResources().getString(R.string.title_sticky_posts));
        groupHeaderList.add(getResources().getString(R.string.title_normal_posts));
        return groupHeaderList;
    }

    private HashMap<String, List<RedditPost>> createRedditPostChildList(
            List<String> groupHeaderList, List<RedditPost> postList) {

        HashMap<String, List<RedditPost>> redditPostChildList = new HashMap<>();
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

        redditPostChildList.put(groupHeaderList.get(0), stickyPostList);
        redditPostChildList.put(groupHeaderList.get(1), normalPostList);
        return redditPostChildList;
    }

    private void setOnClickFooterView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postViewIntent = new Intent(PostInSectionListViewActivity.this,
                        PostViewActivity.class);
                postViewIntent.putExtra(ConstantCollection.EXTRA_NAME_URL,
                        ConstantCollection.EXTRA_VALUE_MORE_INFO_URL);
                startActivity(postViewIntent);
            }
        });
    }
}
